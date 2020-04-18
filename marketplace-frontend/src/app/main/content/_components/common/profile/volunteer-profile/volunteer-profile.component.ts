import { Component, OnInit } from "@angular/core";
import { Participant } from "app/main/content/_model/participant";
import { LoginService } from "app/main/content/_service/login.service";
import { fuseAnimations } from "@fuse/animations";
import { ImageService } from "app/main/content/_service/image.service";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators,
} from "@angular/forms";

@Component({
  selector: "volunteer-profile",
  templateUrl: "volunteer-profile.component.html",
  styleUrls: ["volunteer-profile.component.scss"],
  animations: fuseAnimations,
})
export class VolunteerProfileComponent implements OnInit {
  volunteer: Participant;

  profileForm: FormGroup;
  profileFormErrors: any;

  constructor(
    private loginService: LoginService,
    private imageService: ImageService,
    private formBuilder: FormBuilder
  ) {
    this.profileFormErrors = {
      firstName: {},
      lastName: {},
      birthday: {},
      about: {},
    };
  }

  async ngOnInit() {
    this.profileForm = this.formBuilder.group({
      firstName: new FormControl("", Validators.required),
      lastName: new FormControl("", Validators.required),
      birthday: new FormControl("", Validators.required),
      about: new FormControl("", Validators.required),
    });

    this.profileForm.valueChanges.subscribe(() => {
      this.onProfileFormValuesChanged();
    });

    this.volunteer = <Participant>(
      await this.loginService.getLoggedIn().toPromise()
    );
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

  changeProfile() {
    console.error("change");
  }

  getProfileImage() {
    return this.imageService.getImgSourceFromBytes(this.volunteer.image);
  }
}
