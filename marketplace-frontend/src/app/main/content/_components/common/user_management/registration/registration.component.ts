import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators
} from "@angular/forms";
import { FuseConfigService } from "@fuse/services/config.service";
import { Router } from "@angular/router";
import { RegistrationService } from "../../../../_service/registration.service";
import { HttpResponse } from "@angular/common/http";
import { fuseAnimations } from "@fuse/animations";

@Component({
  selector: "registration",
  templateUrl: "registration.component.html",
  styleUrls: ["./registration.component.scss"],
  animations: fuseAnimations
})
export class FuseRegistrationComponent implements OnInit {
  registrationForm: FormGroup;
  registrationFormErrors: any;

  constructor(
    private fuseConfig: FuseConfigService,
    private formBuilder: FormBuilder,
    private router: Router,
    private registrationService: RegistrationService
  ) {
    const layout = {
      navigation: "none",
      toolbar: "none",
      footer: "none"
    };
    this.fuseConfig.setConfig({ layout: layout });

    this.registrationFormErrors = {
      username: {},
      password: {}
    };
  }

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      username: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required),
      firstName: new FormControl("", Validators.required),
      lastName: new FormControl("", Validators.required)
    });

    this.registrationForm.valueChanges.subscribe(() => {
      this.onRegistrationFormValuesChanged();
    });
  }

  onRegistrationFormValuesChanged() {
    for (const field in this.registrationFormErrors) {
      if (!this.registrationFormErrors.hasOwnProperty(field)) {
        continue;
      }

      // Clear previous errors
      this.registrationFormErrors[field] = {};

      // Get the control
      const control = this.registrationForm.get(field);
      if (control && control.dirty && !control.valid) {
        this.registrationFormErrors[field] = control.errors;
      }
    }
  }

  register() {
    if (!this.registrationForm.valid) {
      return;
    }

    this.registrationService
      .register(
        this.registrationForm.value.username,
        this.registrationForm.value.password
      )
      .toPromise()
      .then((response: HttpResponse<any>) => {
        this.router.navigate(["/login"]);
      });
  }
}
