import {
  Component,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
  ViewEncapsulation,
} from "@angular/core";
import { NavigationEnd, Router } from "@angular/router";

import { Subscription } from "rxjs";

import { FusePerfectScrollbarDirective } from "@fuse/directives/fuse-perfect-scrollbar/fuse-perfect-scrollbar.directive";
import { FuseSidebarService } from "@fuse/components/sidebar/sidebar.service";

import { navigation_volunteer } from "app/navigation/navigation_volunteer";
import { FuseNavigationService } from "@fuse/components/navigation/navigation.service";
import { FuseSidebarComponent } from "@fuse/components/sidebar/sidebar.component";
import { navigation_helpseeker } from "../../navigation/navigation_helpseeker";
import { navigation_flexprod } from "../../navigation/navigation_flexprod";
import { LoginService } from "../content/_service/login.service";
import { ParticipantRole, User } from "../content/_model/user";
import { MessageService } from "../content/_service/message.service";
import { navigation_recruiter } from "app/navigation/navigation_recruiter";
import { navigation_admin } from "app/navigation/navigation_admin";

@Component({
  selector: "fuse-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.scss"],
  providers: [LoginService],
  encapsulation: ViewEncapsulation.None,
})
export class FuseNavbarComponent implements OnInit, OnDestroy {
  private fusePerfectScrollbar: FusePerfectScrollbarDirective;

  @ViewChild(FusePerfectScrollbarDirective, { static: true }) set directive(
    theDirective: FusePerfectScrollbarDirective
  ) {
    if (!theDirective) {
      return;
    }
  }

  @Input() layout;
  navigation: any;
  navigationServiceWatcher: Subscription;
  fusePerfectScrollbarUpdateTimeout;

  constructor(
    private messageService: MessageService,
    private sidebarService: FuseSidebarService,
    private loginService: LoginService,
    private navigationService: FuseNavigationService,
    private router: Router
  ) {
    // Navigation data
    this.loginService
      .getLoggedInParticipantRole()
      .toPromise()
      .then((role: ParticipantRole) => {
        switch (role) {
          case "HELP_SEEKER":
            this.navigation = navigation_helpseeker;
            break;
          case "VOLUNTEER":
            this.navigation = navigation_volunteer;
            break;
          case "FLEXPROD":
            this.navigation = navigation_flexprod;
            break;
          case "RECRUITER":
            this.navigation = navigation_recruiter;
            break;
          case "ADMIN":
            this.navigation = navigation_admin;
            break;
        }
      })
      .catch((e) => {
        console.warn(`NAVBAR COMPONENT ERROR: ${JSON.stringify(e)}`);
      });
    // Default layout
    this.layout = "vertical";
  }

  ngOnInit() {
    this.navigationServiceWatcher = this.navigationService.onItemCollapseToggled.subscribe(
      () => {
        this.fusePerfectScrollbarUpdateTimeout = setTimeout(() => {
          this.fusePerfectScrollbar.update();
        }, 310);
      }
    );
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        if (this.sidebarService.getSidebar("navbar")) {
          this.sidebarService.getSidebar("navbar").close();
        }
      }
    });
  }

  ngOnDestroy() {
    if (this.fusePerfectScrollbarUpdateTimeout) {
      clearTimeout(this.fusePerfectScrollbarUpdateTimeout);
    }

    if (this.navigationServiceWatcher) {
      this.navigationServiceWatcher.unsubscribe();
    }
  }

  toggleSidebarOpened() {
    this.sidebarService.getSidebar("navbar").toggleOpen();
  }
  toggleSidebarFolded() {
    this.sidebarService.getSidebar("navbar").toggleFold();
  }
}
