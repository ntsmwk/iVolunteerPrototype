import { Component, Input, OnDestroy, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { FusePerfectScrollbarDirective } from '@fuse/directives/fuse-perfect-scrollbar/fuse-perfect-scrollbar.directive';
import { FuseSidebarService } from '@fuse/components/sidebar/sidebar.service';
import { FuseNavigationService } from '@fuse/components/navigation/navigation.service';
import { navigation_configurator } from 'app/navigation/navigation_configurator';

@Component({
  selector: 'fuse-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  providers: [],
  encapsulation: ViewEncapsulation.None,
})
export class FuseNavbarComponent implements OnInit, OnDestroy {
  private fusePerfectScrollbar: FusePerfectScrollbarDirective;

  onRoleChanged: Subscription;

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
    private sidebarService: FuseSidebarService,
    private navigationService: FuseNavigationService,
    private router: Router,
  ) {
    // TODO
    // Navigation data
    this.navigation = navigation_configurator;
    // this.navigation = navigation_configurator_debug;
    // Default layout
    this.layout = 'vertical';
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
        if (this.sidebarService.getSidebar('navbar')) {
          this.sidebarService.getSidebar('navbar').close();
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

    this.onRoleChanged.unsubscribe();
  }

  toggleSidebarOpened() {
    this.sidebarService.getSidebar('navbar').toggleOpen();
  }
  toggleSidebarFolded() {
    this.sidebarService.getSidebar('navbar').toggleFold();
  }
}
