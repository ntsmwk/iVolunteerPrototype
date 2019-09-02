import {Component, Input, OnDestroy, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';

import {Subscription} from 'rxjs';

import {FusePerfectScrollbarDirective} from '@fuse/directives/fuse-perfect-scrollbar/fuse-perfect-scrollbar.directive';
import {FuseSidebarService} from '@fuse/components/sidebar/sidebar.service';
import {navigation_admin} from '../../navigation/navigation_admin';
import {navigation_volunteer} from 'app/navigation/navigation_volunteer';
import {navigation_helpseeker} from '../../navigation/navigation_helpseeker';
import {FuseNavigationService} from '@fuse/components/navigation/navigation.service';
import {LoginService} from '../content/_service/login.service';
import {CoreDashboardService} from '../content/_service/core-dashboard.service';
import {Dashboard} from '../content/_model/dashboard';
import {MessageService} from '../content/_service/message.service';

@Component({
  selector: 'fuse-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  providers: [LoginService],
  encapsulation: ViewEncapsulation.None
})
export class FuseNavbarComponent implements OnInit, OnDestroy {
  private dashboardChangedSubscription: Subscription;
  private fusePerfectScrollbar: FusePerfectScrollbarDirective;

  @ViewChild(FusePerfectScrollbarDirective) set directive(theDirective: FusePerfectScrollbarDirective) {
    if (!theDirective) {
      return;
    }

    this.fusePerfectScrollbar = theDirective;

    this.navigationServiceWatcher =
      this.navigationService.onItemCollapseToggled.subscribe(() => {
        this.fusePerfectScrollbarUpdateTimeout = setTimeout(() => {
          this.fusePerfectScrollbar.update();
        }, 310);
      });
  }

  @Input()
  layout;
  navigation: Array<any>;
  navigationServiceWatcher: Subscription;
  fusePerfectScrollbarUpdateTimeout;

  constructor(private loginService: LoginService,
              private messageService: MessageService,
              private dashboardService: CoreDashboardService,
              private router: Router,
              private sidebarService: FuseSidebarService,
              private navigationService: FuseNavigationService) {
    // Default layout
    this.layout = 'vertical';

    // Navigation data
    this.loadNavigationData();
  }

  ngOnInit() {
    this.dashboardChangedSubscription = this.messageService.subscribe('dashboardChanged', () => this.loadNavigationData());
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
    this.dashboardChangedSubscription.unsubscribe();
  }

  private loadNavigationData() {
    Promise.all([
      this.loginService.getLoggedIn().toPromise(),
      this.loginService.getLoggedInParticipantRole().toPromise()
    ]).then((values: any[]) => {
      if ('ADMIN' === values[1]) {
        this.navigation = navigation_admin;
      } else if ('HELP_SEEKER' === values[1]) {
        this.navigation = navigation_helpseeker;
      } else { // VOLUNTEER
        this.navigation = navigation_volunteer;
        this.dashboardService.findByParticipant(values[0].id).toPromise().then((dashboards: Dashboard[]) => {
          if (dashboards.length === 0) {
            return;
          }
          const dashboardNavigation = this.navigation.find((item) => 'dashboard' === item.id);
          dashboardNavigation.type = 'collapse';
          dashboardNavigation.children = [];
          dashboards.forEach((dashboard: Dashboard) => {
            const item = {
              'id': `dashboard-${dashboard.id}`,
              'title': `${dashboard.name}`,
              'type': 'item',
              'icon': `${dashboardNavigation.icon}`,
              'url': `${dashboardNavigation.url}/${dashboard.id}`
            };
            dashboardNavigation.children.push(item);
          });
        });
      }
    });
  }

  toggleSidebarOpened() {
    this.sidebarService.getSidebar('navbar').toggleOpen();
  }

  toggleSidebarFolded() {
    this.sidebarService.getSidebar('navbar').toggleFold();
  }
}
