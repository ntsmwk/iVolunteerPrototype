import {
  Component,
  ElementRef,
  HostBinding,
  Inject,
  OnDestroy,
  Renderer2,
  ViewEncapsulation,
} from "@angular/core";
import { DOCUMENT } from "@angular/common";
import { Platform } from "@angular/cdk/platform";
import { Subscription } from "rxjs";

import { FuseConfigService } from "@fuse/services/config.service";

import { navigation_volunteer } from "app/navigation/navigation_volunteer";
import { navigation_helpseeker } from "app/navigation/navigation_helpseeker";
import { LoginService } from "./content/_service/login.service";
import { UserRole, User } from "./content/_model/user";
import { navigation_flexprod } from "app/navigation/navigation_flexprod";
import { navigation_recruiter } from "app/navigation/navigation_recruiter";
import { Router } from "@angular/router";
import { navigation_admin } from "app/navigation/navigation_admin";

@Component({
  selector: "fuse-main",
  templateUrl: "./main.component.html",
  styleUrls: ["./main.component.scss"],
  providers: [LoginService],
  encapsulation: ViewEncapsulation.None,
})
export class FuseMainComponent implements OnDestroy {
  onConfigChanged: Subscription;
  fuseSettings: any;
  navigation: any;

  @HostBinding("attr.fuse-layout-mode") layoutMode;

  constructor(
    private _renderer: Renderer2,
    private _elementRef: ElementRef,
    private fuseConfig: FuseConfigService,
    private loginService: LoginService,
    private platform: Platform,
    private router: Router,
    @Inject(DOCUMENT) private document: any
  ) {
    this.onConfigChanged = this.fuseConfig.onConfigChanged.subscribe(
      (newSettings) => {
        this.fuseSettings = newSettings;
        this.layoutMode = this.fuseSettings.layout.mode;
      }
    );

    if (this.platform.ANDROID || this.platform.IOS) {
      this.document.body.className += " is-mobile";
    }

    this.loginService
      .getLoggedInUserRole()
      .toPromise()
      .then((role: UserRole) => {
        switch (role) {
          case UserRole.HELP_SEEKER:
            this.navigation = navigation_helpseeker;
            break;
          case UserRole.VOLUNTEER:
            this.navigation = navigation_volunteer;
            break;
          case UserRole.FLEXPROD:
            this.navigation = navigation_flexprod;
            break;
          case UserRole.RECRUITER:
            this.navigation = navigation_recruiter;
            break;
          case UserRole.ADMIN:
            this.navigation = navigation_admin;
            break;
        }
      })
      .catch((e) => {
        console.warn("MAIN COMPONENT ERROR: " + JSON.stringify(e));
      });
  }

  ngOnDestroy() {
    this.onConfigChanged.unsubscribe();
  }

  addClass(className: string) {
    this._renderer.addClass(this._elementRef.nativeElement, className);
  }

  removeClass(className: string) {
    this._renderer.removeClass(this._elementRef.nativeElement, className);
  }
}
