import { Component, OnInit } from "@angular/core";
import { User, UserRole } from "app/main/content/_model/user";
import { LoginService } from "app/main/content/_service/login.service";
import { fuseAnimations } from "@fuse/animations";
import { ImageService } from "app/main/content/_service/image.service";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
} from "@angular/forms";
import { CoreUserService } from "app/main/content/_service/core-user.serivce";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "profile",
  templateUrl: "profile.component.html",
  styleUrls: ["profile.component.scss"],
  animations: fuseAnimations,
})
export class ProfileComponent implements OnInit {
  globalInfo: GlobalInfo;
  user: User;
  currentRoles: UserRole[] = [];
  profileForm: FormGroup;
  profileFormErrors: any;

  today = new Date();
  isLoaded: boolean = false;

  constructor(
    private loginService: LoginService,
    private imageService: ImageService,
    private formBuilder: FormBuilder,
    private coreUserService: CoreUserService
  ) {
    this.profileFormErrors = {
      firstName: {},
      lastName: {},
      birthday: {},
    };
  }

  async ngOnInit() {
    this.profileForm = this.formBuilder.group({
      firstName: new FormControl("", Validators.required),
      lastName: new FormControl("", Validators.required),
      birthday: new FormControl("", Validators.required),
    });

    this.profileForm.valueChanges.subscribe(() => {
      this.onProfileFormValuesChanged();
    });
    this.reload();

    this.isLoaded = true;
  }

  async reload() {
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    this.user = this.globalInfo.user;
    this.currentRoles = this.user.subscribedTenants.map((s) => s.role);

    this.profileForm.setValue({
      firstName: this.user.firstname,
      lastName: this.user.lastname,
      birthday: new Date(this.user.birthday),
    });
  }

  onProfileFormValuesChanged() {
    for (const field in this.profileFormErrors) {
      if (!this.profileFormErrors.hasOwnProperty(field)) {
        continue;
      }

      // Clear previous errors
      this.profileFormErrors[field] = {};

      // Get the control
      const control = this.profileForm.get(field);
      if (control && control.dirty && !control.valid) {
        this.profileFormErrors[field] = control.errors;
      }
    }
  }

  async changeProfile() {
    this.user.firstname = this.profileForm.value.firstName;
    this.user.lastname = this.profileForm.value.lastName;
    this.user.birthday = this.profileForm.value.birthday;
    await this.coreUserService.updateUser(this.user, true).toPromise();

    this.loginService.generateGlobalInfo(
      this.globalInfo.userRole,
      this.globalInfo.tenants.map((t) => t.id)
    );

    this.reload();
  }

  getProfileImage() {
    if (this.user.image) {
      return this.imageService.getImgSourceFromBytes(this.user.image);
    } else {
      return "/assets/images/avatars/profile.jpg";
    }
  }

  hasVolunteerRole() {
    return this.currentRoles.indexOf(UserRole.VOLUNTEER) != -1;
  }
}
