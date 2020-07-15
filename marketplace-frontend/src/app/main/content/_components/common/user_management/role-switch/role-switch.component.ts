import { Component, OnInit } from "@angular/core";
import { LoginService } from "app/main/content/_service/login.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import {
  User,
  TenantUserSubscription,
  UserRole,
} from "app/main/content/_model/user";
import { ImageService } from "app/main/content/_service/image.service";
import { fuseAnimations } from "@fuse/animations";
import { Tenant } from "app/main/content/_model/tenant";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Router } from "@angular/router";
import { navigation_helpseeker } from "app/navigation/navigation_helpseeker";
import { navigation_volunteer } from "app/navigation/navigation_volunteer";
import { navigation_flexprod } from "app/navigation/navigation_flexprod";
import { navigation_recruiter } from "app/navigation/navigation_recruiter";
import { navigation_admin } from "app/navigation/navigation_admin";

@Component({
  selector: "app-role-switch",
  templateUrl: "./role-switch.component.html",
  styleUrls: ["./role-switch.component.scss"],
  animations: fuseAnimations,
})
export class RoleSwitchComponent implements OnInit {
  user: User;
  subscriptions: TenantUserSubscription[] = [];
  allTenants: Tenant[] = [];
  navigation: any;
  isLoaded: boolean = false;

  constructor(
    private router: Router,

    private loginService: LoginService,
    private imageService: ImageService,
    private tenantService: TenantService
  ) {}

  async ngOnInit() {
    this.user = <User>await this.loginService.getLoggedIn().toPromise();
    this.subscriptions = this.user.subscribedTenants;

    this.allTenants = <Tenant[]>await this.tenantService.findAll().toPromise();

    this.subscriptions.filter((s) => {
      return s.role != UserRole.VOLUNTEER;
    });

    this.isLoaded = true;
  }

  onRoleSelected(role: UserRole) {
    this.loginService.generateGlobalInfo(role).then(() => {
      this.router.navigate(["/main/dashboard"]).then(() => {
        // TODO Philipp: find better solution
        window.location.reload();
      });
    });
  }

  getProfileImage() {
    return this.imageService.getImgSourceFromBytes(this.user.image);
  }

  getTenant(tenantId: string) {
    return this.allTenants.filter((t) => t.id === tenantId);
  }

  getTenantName(tenantId: string) {
    let tenant = this.allTenants.find((t) => t.id === tenantId);
    return tenant.name;
  }

  getRoleName(role: UserRole) {
    switch (role) {
      case UserRole.VOLUNTEER:
        return "Freiwilliger";
      case UserRole.HELP_SEEKER:
        return "Hilfesuchender";
      case UserRole.RECRUITER:
        return "Recruiter";
      case UserRole.FLEXPROD:
        return "Flexprod";
      case UserRole.ADMIN:
        return "Admin";
    }
  }
}
