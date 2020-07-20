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
import { RoleChangeService } from "./content/_service/role-change.service";

@Component({
  selector: "fuse-main",
  templateUrl: "./main.component.html",
  styleUrls: ["./main.component.scss"],
  providers: [LoginService],
  encapsulation: ViewEncapsulation.None,
})
export class FuseMainComponent implements OnDestroy {
  onConfigChanged: Subscription;
  onRoleChanged: Subscription;

  fuseSettings: any;
  navigation: any;

  role: UserRole = UserRole.NONE;

  @HostBinding("attr.fuse-layout-mode") layoutMode;

  constructor(
    private _renderer: Renderer2,
    private _elementRef: ElementRef,
    private fuseConfig: FuseConfigService,
    private loginService: LoginService,
    private platform: Platform,
    private router: Router,
    private roleChangeService: RoleChangeService,
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

    this.onRoleChanged = this.roleChangeService.onRoleChanged.subscribe(
      (newRole) => {
        this.role = newRole;
      }
    );
  }

  ngOnDestroy() {
    this.onConfigChanged.unsubscribe();
    this.onRoleChanged.unsubscribe();
  }

  addClass(className: string) {
    this._renderer.addClass(this._elementRef.nativeElement, className);
  }

  removeClass(className: string) {
    this._renderer.removeClass(this._elementRef.nativeElement, className);
  }
}
