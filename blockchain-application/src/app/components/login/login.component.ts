import {Component, OnInit} from '@angular/core';
import {VolunteerService} from '../../providers/volunteer.service';
import {OrganisationService} from '../../providers/organisation.service';
import {Organisation, Person, Volunteer} from '../../model/at.jku.cis';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Observable} from 'rxjs/Observable';
import {startWith} from 'rxjs/operators/startWith';
import {map} from 'rxjs/operators/map';
import {AuthenticationService, LoginData} from '../../providers/authentication.service';


@Component({
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})

export class LoginComponent {

  loginForm: FormGroup;

  constructor(formBuilder: FormBuilder) {
    this.loginForm = formBuilder.group({
      'username': new FormControl('', Validators.required),
      'password': new FormControl('', Validators.required)
    });
  }

  onSubmit() {
    if (!this.loginForm.valid) {
      return;
    }

    const loginData = this.loginForm.value as LoginData;
    window.location.replace('http://localhost:3000/auth/github');

    /*this.authenticationService.login(loginData)
      .toPromise()
      .then((success: any) => console.log(success))
      .catch((error: any) => console.log(error));*/
  }
}
