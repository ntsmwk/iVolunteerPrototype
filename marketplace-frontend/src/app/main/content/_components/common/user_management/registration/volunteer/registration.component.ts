import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import {
  FormGroup, FormBuilder, FormControl, Validators,
} from '@angular/forms';
import { FuseConfigService } from '@fuse/services/config.service';
import { Router } from '@angular/router';
import { RegistrationService } from 'app/main/content/_service/registration.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { fuseAnimations } from '@fuse/animations';
import { User, AccountType } from 'app/main/content/_model/user';
import { equals } from 'app/main/content/_validator/equals.validator';
import { isNullOrUndefined } from 'util';
import { stringUniqueValidator } from 'app/main/content/_validator/string-unique.validator';


@Component({
  selector: "volunteer-registration",
  templateUrl: 'registration.component.html',
  styleUrls: ['../registration.component.scss'],
  animations: fuseAnimations,
})
export class VolunteerRegistrationComponent implements OnInit {
  registrationForm: FormGroup;
  registrationFormErrors: any;
  today = new Date();
  displaySuccess: boolean;

  constructor(
    private fuseConfig: FuseConfigService,
    private formBuilder: FormBuilder,
    private router: Router,
    private registrationService: RegistrationService,

  ) {
    const layout = {
      navigation: 'none',
      toolbar: 'none',
      footer: 'none',
    };
    this.fuseConfig.setConfig({ layout: layout });

    this.registrationFormErrors = {
      username: {},
      password: {},
      confirmPassword: {},
      email: {},
      firstName: {},
      lastName: {},
      birthday: {},
    };

  }

  @ViewChild('loginFormWrapper', { static: false }) loginFormWrapper: ElementRef;


  ngOnInit() {


    this.registrationForm = this.formBuilder.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      confirmPassword: new FormControl(''),
      email: new FormControl('', [Validators.required, Validators.pattern(
        /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/
      )]),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      birthday: new FormControl('', Validators.required),
    });

    this.registrationForm.valueChanges.subscribe(() => {
      this.onRegistrationFormValuesChanged();
    });

    this.registrationForm.controls['confirmPassword'].setValidators([Validators.required, equals(this.registrationForm.controls['password'], this.registrationForm.controls['confirmPassword'])]);
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

      if (field === 'confirmPassword' || field === 'password' || field === 'email' || field === 'username') {
        control.updateValueAndValidity({ onlySelf: true });
      }

      if (control && control.dirty && !control.valid) {
        this.registrationFormErrors[field] = control.errors;
      }
    }
  }

  register() {
    if (!this.registrationForm.valid) {
      return;
    }

    const volunteer = new User();
    volunteer.username = this.registrationForm.value.username;
    volunteer.password = this.registrationForm.value.password;
    volunteer.loginEmail = this.registrationForm.value.email;
    volunteer.emails = [];
    volunteer.emails.push(this.registrationForm.value.email);
    volunteer.firstname = this.registrationForm.value.firstName;
    volunteer.lastname = this.registrationForm.value.lastName;
    volunteer.birthday = this.registrationForm.value.birthday;

    this.registrationService.registerUser(volunteer, AccountType.PERSON)
      .toPromise().then((response: HttpResponse<any>) => {

        this.loginFormWrapper.nativeElement.scrollTo(0, 0);
        this.displaySuccess = true;
      }).catch((response: HttpErrorResponse) => {

        if (response.error.response === 'USERNAME') {
          this.registrationForm.controls['username'].setValidators([Validators.required, stringUniqueValidator([volunteer.username])]);
        }

        if (response.error.response === 'EMAIL') {
          this.registrationForm.controls['email'].setValidators([Validators.required, stringUniqueValidator([volunteer.loginEmail])]);
        }

        this.onRegistrationFormValuesChanged();
      });
  }

  handleBackClick() {
    window.history.back();
  }

  handleBackToLoginClick() {
    this.router.navigate(['/login']);
  }
}
