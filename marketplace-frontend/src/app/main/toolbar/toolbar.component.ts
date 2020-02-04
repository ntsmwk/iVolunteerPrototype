import { Component, ViewChild, ElementRef, ChangeDetectorRef } from '@angular/core';
import { NavigationEnd, NavigationStart, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { FuseConfigService } from '@fuse/services/config.service';
import { FuseSidebarService } from '@fuse/components/sidebar/sidebar.service';

import { navigation_volunteer } from 'app/navigation/navigation_volunteer';
import { navigation_helpseeker } from '../../navigation/navigation_helpseeker';
import { LoginService } from '../content/_service/login.service';
import { Participant, ParticipantRole } from '../content/_model/participant';

@Component({
  selector: 'fuse-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})

export class FuseToolbarComponent{
  userStatusOptions: any[];
  languages: any;
  selectedLanguage: any;
  showLoadingBar: boolean;
  horizontalNav: boolean;
  noNav: boolean;
  navigation: any;
  icons: string;

  @ViewChild('inboxIcon', { static: true }) inboxIcon: ElementRef;
  displayInboxOverlay: boolean;

  showEditor: boolean = false;



  constructor(private router: Router,
    private fuseConfig: FuseConfigService,
    private loginService: LoginService,
    private sidebarService: FuseSidebarService,
    private translate: TranslateService,
    private changeDetector: ChangeDetectorRef) {
    this.userStatusOptions = [
      {
        'title': 'Online',
        'icon': 'icon-checkbox-marked-circle',
        'color': '#4CAF50'
      },
      {
        'title': 'Away',
        'icon': 'icon-clock',
        'color': '#FFC107'
      },
      {
        'title': 'Do not Disturb',
        'icon': 'icon-minus-circle',
        'color': '#F44336'
      },
      {
        'title': 'Invisible',
        'icon': 'icon-checkbox-blank-circle-outline',
        'color': '#BDBDBD'
      },
      {
        'title': 'Offline',
        'icon': 'icon-checkbox-blank-circle-outline',
        'color': '#616161'
      }
    ];

    this.languages = [
      {
        'id': 'en',
        'title': 'English',
        'flag': 'us'
      },
      {
        'id': 'tr',
        'title': 'Turkish',
        'flag': 'tr'
      }
    ];

    this.selectedLanguage = this.languages[0];

    router.events.subscribe(
      (event) => {
        if (event instanceof NavigationStart) {
          this.showLoadingBar = true;
        }
        if (event instanceof NavigationEnd) {
          this.showLoadingBar = false;
        }
      });

    this.fuseConfig.onConfigChanged.subscribe((settings) => {
      this.horizontalNav = settings.layout.navigation === 'top';
      this.noNav = settings.layout.navigation === 'none';
    });

    this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
      switch (role) {
        case 'HELP_SEEKER':
          this.navigation = navigation_helpseeker;
          this.icons = 'HELP_SEEKER';
          break;
        case 'VOLUNTEER':
          this.navigation = navigation_volunteer;
          this.icons = 'VOLUNTEER';
          break;
      }
    }).catch(e => {
      console.warn(e);
    });

    setInterval(() => { this.setHeading(this); } , 100);
  }

  private setHeading(self ){

    if(self.router && self.router.url && self.router.url.indexOf('instance-editor') === -1){
      self.showEditor = false;
    }
    else{
      self.showEditor = true;
    }

    setInterval(() => { self.setHeading(self); } , 100);
  }



  toggleSidebarOpened(key) {
    this.sidebarService.getSidebar(key).toggleOpen();
  }

  search(value) {
    // Do your search here...
    console.log(value);
  }

  @ViewChild('overlayDiv', { static: false }) overlayDiv: ElementRef;
  @ViewChild('overlayArrow', { static: false }) overlayArrowDiv: ElementRef;


  toggleInboxOverlay(event: any, inboxIcon: any) {
    this.displayInboxOverlay = !this.displayInboxOverlay;
    this.changeDetector.detectChanges();

    if (this.displayInboxOverlay) {
      const { x, y } = inboxIcon._elementRef.nativeElement.getBoundingClientRect();

      console.log("x: " + x + " y: " + y);
      console.log(this.overlayDiv);
      this.overlayDiv.nativeElement.style.top = (y + 35) + 'px';
      this.overlayDiv.nativeElement.style.left = (x - 150) + 'px';
      this.overlayDiv.nativeElement.style.position = 'fixed';
      this.overlayDiv.nativeElement.style.width = '300px';
      this.overlayDiv.nativeElement.style.height = '240px';

      this.overlayArrowDiv.nativeElement.style.top = (y+20)+'px';
      this.overlayArrowDiv.nativeElement.style.left = (x-8)+'px';
      this.overlayArrowDiv.nativeElement.style.position = 'fixed';


      console.log(this.overlayDiv.nativeElement.style);
    }



  }

  closeOverlay($event) {
    this.displayInboxOverlay = false;
  }




  setLanguage(lang) {
    // Set the selected language for toolbar
    this.selectedLanguage = lang;

    // Use the selected language for translations
    this.translate.use(lang.id);
  }
}
