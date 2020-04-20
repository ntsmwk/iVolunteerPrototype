import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators
} from '@angular/forms';
import { Router } from '@angular/router';
import { HttpResponse } from '@angular/common/http';

import { FuseConfigService } from '@fuse/services/config.service';
import { fuseAnimations } from '@fuse/animations';
import { LoginService } from '../../../../_service/login.service';


@Component({
  selector: 'fuse-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  providers: [LoginService],
  animations: fuseAnimations
})
export class FuseLoginComponent implements OnInit {
  loginForm: FormGroup;
  loginFormErrors: any;

  constructor(
    private fuseConfig: FuseConfigService,
    private formBuilder: FormBuilder,
    private router: Router,
    private loginService: LoginService,
  ) {
    const layout = {
      navigation: 'none',
      toolbar: 'none',
      footer: 'none'
    };
    this.fuseConfig.setConfig({ layout: layout });

    this.loginFormErrors = {
      username: {},
      password: {}
    };
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });

    this.loginForm.valueChanges.subscribe(() => {
      this.onLoginFormValuesChanged();
    });
  }

  onLoginFormValuesChanged() {
    for (const field in this.loginFormErrors) {
      if (!this.loginFormErrors.hasOwnProperty(field)) {
        continue;
      }

      // Clear previous errors
      this.loginFormErrors[field] = {};

      // Get the control
      const control = this.loginForm.get(field);
      if (control && control.dirty && !control.valid) {
        this.loginFormErrors[field] = control.errors;
      }
    }
  }

  login() {
    if (this.loginForm.valid) {
      this.loginService
        .login(this.loginForm.value.username, this.loginForm.value.password)
        .toPromise()
        .then((response: HttpResponse<any>) => {
          localStorage.setItem('token', response.headers.get('Authorization'));
          this.router.navigate(['/main/dashboard']);
        });
    }
  }
}
