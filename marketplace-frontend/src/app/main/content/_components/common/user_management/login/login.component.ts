import { Component, OnInit } from "@angular/core";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { Router } from "@angular/router";
import { HttpResponse } from "@angular/common/http";

import { FuseConfigService } from "@fuse/services/config.service";
import { fuseAnimations } from "@fuse/animations";
import { LoginService } from "../../../../_service/login.service";
import { isNullOrUndefined } from "util";
@Component({
  selector: "fuse-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
  providers: [LoginService],
  animations: fuseAnimations,
})
export class FuseLoginComponent implements OnInit {
  loginForm: FormGroup;
  loginFormErrors: any;

  error: boolean;
  displayLoginForm: boolean;
  resendActivationFlow: boolean;
  layout: { navigation: string; toolbar: string; footer: string };

  constructor(
    private fuseConfig: FuseConfigService,
    private formBuilder: FormBuilder,
    private router: Router,
    private loginService: LoginService
  ) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: new FormControl("", Validators.required),
      password: new FormControl("", Validators.required),
    });

    this.loginForm.valueChanges.subscribe(() => {
      this.onLoginFormValuesChanged();
    });

    this.error = false;
    this.displayLoginForm = true;
    this.resendActivationFlow = false;

    this.layout = {
      navigation: "none",
      toolbar: "none",
      footer: "none",
    };
    this.fuseConfig.setConfig({ layout: this.layout });
    this.loginFormErrors = {
      username: {},
      password: {},
    };
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
        .getActivationStatus(this.loginForm.value.username)
        .toPromise()
        .then((cont: boolean) => {
          if (cont) {
            this.loginService
              .login(
                this.loginForm.value.username,
                this.loginForm.value.password
              )
              .toPromise()
              .then((response: HttpResponse<any>) => {
                const accessToken: string = response.body.accessToken;
                const refreshToken: string = response.body.refreshToken;

                if (accessToken == null || refreshToken == null) {
                  this.error = true;
                  this.loginService.logout();
                } else {
                  localStorage.setItem("accessToken", accessToken);
                  localStorage.setItem("refreshToken", refreshToken);
                  this.router.navigate(["/role"]);
                }
              })
              .catch((e) => {
                this.loginService.logout();
                this.error = true;
                this.loginService.logout();
              });
          } else {
            this.displayLoginForm = false;
          }
        });
    }
  }
}
