import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
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
  selector: "organization-registration",
  templateUrl: 'registration.component.html',
  styleUrls: ['../registration.component.scss'],
  animations: fuseAnimations,
})
export class OrganizationRegistrationComponent implements OnInit {
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

      organizationName: {},
      organizationPosition: {},

      formOfAddress: {},
      titleBefore: {},
      firstName: {},
      lastName: {},
      titleAfter: {},

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

      organizationName: new FormControl('', Validators.required),
      organizationPosition: new FormControl('', Validators.required),

      formOfAddress: new FormControl('', Validators.required),
      titleBefore: new FormControl(''),
      firstName: new FormControl('', Validators.required),
      lastName: new FormControl('', Validators.required),
      titleAfter: new FormControl(''),

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

    const tenantAdmin = new User();
    tenantAdmin.username = this.registrationForm.value.username;
    tenantAdmin.password = this.registrationForm.value.password;
    tenantAdmin.loginEmail = this.registrationForm.value.email;
    tenantAdmin.emails = [];
    tenantAdmin.emails.push(this.registrationForm.value.email);

    tenantAdmin.organizationName = this.registrationForm.value.organizationName;
    tenantAdmin.organizationPosition = this.registrationForm.value.organizationPosition;

    tenantAdmin.titleBefore = this.registrationForm.value.titleBefore;
    tenantAdmin.firstname = this.registrationForm.value.firstName;
    tenantAdmin.lastname = this.registrationForm.value.lastName;
    tenantAdmin.titleAfter = this.registrationForm.value.titleAfter;

    this.registrationService.registerUser(tenantAdmin, AccountType.ORGANIZATION)
      .toPromise().then((response: HttpResponse<any>) => {
        this.loginFormWrapper.nativeElement.scrollTo(0, 0);
        this.displaySuccess = true;

      }).catch((response: HttpErrorResponse) => {

        if (response.error.response === 'USERNAME') {
          this.registrationForm.controls['username'].setValidators([Validators.required, stringUniqueValidator([tenantAdmin.username])]);
        }
        if (response.error.response === 'EMAIL') {
          this.registrationForm.controls['email'].setValidators([Validators.required, stringUniqueValidator([tenantAdmin.loginEmail])]);
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
