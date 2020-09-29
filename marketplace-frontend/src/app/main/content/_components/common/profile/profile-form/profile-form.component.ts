import { Component, OnInit, Input } from '@angular/core';
import { User, UserRole } from 'app/main/content/_model/user';
import { LoginService } from 'app/main/content/_service/login.service';
import { fuseAnimations } from '@fuse/animations';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { CoreUserService } from 'app/main/content/_service/core-user.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { UserService } from 'app/main/content/_service/user.service';
import { CoreUserImageService } from 'app/main/content/_service/core-user-image.service';
import { UserImage } from 'app/main/content/_model/image';

@Component({
  selector: "profile-form",
  templateUrl: 'profile-form.component.html',
  styleUrls: ['profile-form.component.scss'],
  animations: fuseAnimations,
})
export class ProfileFormComponent implements OnInit {
  globalInfo: GlobalInfo;
  @Input() user: User;
  @Input() userImage: UserImage;
  currentRoles: UserRole[] = [];
  profileForm: FormGroup;
  profileFormErrors: any;

  today = new Date();
  loaded: boolean;

  constructor(
    private loginService: LoginService,
    private formBuilder: FormBuilder,
    private coreUserService: CoreUserService,
    private userService: UserService,
    private userImageService: CoreUserImageService,
  ) {
    this.profileFormErrors = {

      formOfAddress: {},
      firstName: {},
      lastName: {},
      birthday: {},
    };
  }

  async ngOnInit() {
    this.loaded = false;

    this.globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise();

    this.profileForm = this.formBuilder.group({
      formOfAddress: new FormControl('', Validators.required),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      birthday: new FormControl('', Validators.required),
    });

    this.profileForm.valueChanges.subscribe(() => {
      this.onProfileFormValuesChanged();
    });
    this.reload();

    this.loaded = true;
  }

  async reload() {

    this.profileForm.setValue({
      formOfAddress: this.user.formOfAddress,
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

  async save() {
    this.user.formOfAddress = this.profileForm.value.formOfAddress;
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

    return this.userImageService.getUserProfileImage(this.userImage);

  }

  hasVolunteerRole() {
    return this.currentRoles.indexOf(UserRole.VOLUNTEER) !== -1;
  }
}