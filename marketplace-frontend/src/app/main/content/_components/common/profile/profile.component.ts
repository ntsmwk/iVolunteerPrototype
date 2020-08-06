import { Component, OnInit } from "@angular/core";
import { User } from "app/main/content/_model/user";
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

@Component({
  selector: "profile",
  templateUrl: "profile.component.html",
  styleUrls: ["profile.component.scss"],
  animations: fuseAnimations,
})
export class ProfileComponent implements OnInit {
  user: User;

  profileForm: FormGroup;
  profileFormErrors: any;

  today = new Date();

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
  }

  async reload() {
    this.user = <User>await this.loginService.getLoggedIn().toPromise();
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
    this.reload();
  }

  getProfileImage() {
    if (this.user.image) {
      return this.imageService.getImgSourceFromBytes(this.user.image);
    } else {
      return "/assets/images/avatars/profile.jpg";
    }
  }
}
