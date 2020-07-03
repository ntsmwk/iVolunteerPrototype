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
import { CoreVolunteerService } from "app/main/content/_service/core-volunteer.service";

@Component({
  selector: "volunteer-profile",
  templateUrl: "volunteer-profile.component.html",
  styleUrls: ["volunteer-profile.component.scss"],
  animations: fuseAnimations,
})
export class VolunteerProfileComponent implements OnInit {
  volunteer: User;

  profileForm: FormGroup;
  profileFormErrors: any;

  today = new Date();

  constructor(
    private loginService: LoginService,
    private imageService: ImageService,
    private formBuilder: FormBuilder,
    private volunteerService: CoreVolunteerService
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
    this.volunteer = <User>await this.loginService.getLoggedIn().toPromise();
    console.error(this.volunteer);
    this.profileForm.setValue({
      firstName: this.volunteer.firstname,
      lastName: this.volunteer.lastname,
      birthday: new Date(this.volunteer.birthday),
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
    this.volunteer.firstname = this.profileForm.value.firstName;
    this.volunteer.lastname = this.profileForm.value.lastName;
    this.volunteer.birthday = this.profileForm.value.birthday;
    await this.volunteerService.updateVolunteer(this.volunteer).toPromise();
    this.reload();
  }

  getProfileImage() {
    return this.imageService.getImgSourceFromBytes(this.volunteer.image);
  }
}
