import { Component, Input, OnDestroy, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

import { Subscription } from 'rxjs';

import { FusePerfectScrollbarDirective } from '@fuse/directives/fuse-perfect-scrollbar/fuse-perfect-scrollbar.directive';
import { FuseSidebarService } from '@fuse/components/sidebar/sidebar.service';

import { navigation_volunteer } from 'app/navigation/navigation_volunteer';
import { FuseNavigationService } from '@fuse/components/navigation/navigation.service';
import { FuseSidebarComponent } from '@fuse/components/sidebar/sidebar.component';
import { navigation_helpseeker } from '../../navigation/navigation_helpseeker';
import { navigation_flexprod } from '../../navigation/navigation_flexprod';
import { LoginService } from '../content/_service/login.service';
import { ParticipantRole } from '../content/_model/participant';
import { MessageService } from '../content/_service/message.service';

@Component({
  selector: 'fuse-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  providers: [LoginService],
  encapsulation: ViewEncapsulation.None
})
export class FuseNavbarComponent implements OnInit, OnDestroy {
  private fusePerfectScrollbar: FusePerfectScrollbarDirective;

  @ViewChild(FusePerfectScrollbarDirective) set directive(theDirective: FusePerfectScrollbarDirective) {
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
    this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
      switch (role) {
        case 'HELP_SEEKER':
          this.navigation = navigation_helpseeker;
          break;
        case 'VOLUNTEER':
          this.navigation = navigation_volunteer;
          break;
        case 'FLEXPROD':
          this.navigation = navigation_flexprod;
      }
    })
    // Default layout
    this.layout = 'vertical';
  }

  ngOnInit() {
    this.navigationServiceWatcher =
      this.navigationService.onItemCollapseToggled.subscribe(() => {
        this.fusePerfectScrollbarUpdateTimeout = setTimeout(() => {
          this.fusePerfectScrollbar.update();
        }, 310);
      });
    this.router.events.subscribe(
      (event) => {
        if (event instanceof NavigationEnd) {
          if (this.sidebarService.getSidebar('navbar')) {
            this.sidebarService.getSidebar('navbar').close();
          }
        }
      }
    );
  }



  ngOnDestroy() {
    if (this.fusePerfectScrollbarUpdateTimeout) {
      clearTimeout(this.fusePerfectScrollbarUpdateTimeout);
    }

    if (this.navigationServiceWatcher) {
      this.navigationServiceWatcher.unsubscribe();
    }
  }


}
