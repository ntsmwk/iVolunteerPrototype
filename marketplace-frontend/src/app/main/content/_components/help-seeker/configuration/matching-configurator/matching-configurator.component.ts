import { Component, OnInit } from '@angular/core';
import { DialogFactoryDirective } from '../../../_shared/dialogs/_dialog-factory/dialog-factory.component';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: "app-matching-configurator",
  templateUrl: './matching-configurator.component.html',
  styleUrls: ['./matching-configurator.component.scss'],
  providers: [DialogFactoryDirective]
})
export class MatchingConfiguratorComponent implements OnInit {
  constructor(
    private loginService: LoginService,
    private sanitizer: DomSanitizer,
  ) { }

  loaded: boolean;
  sanitizedUrl: SafeResourceUrl;
  globalInfo: GlobalInfo;


  async ngOnInit() {
    this.loaded = false;

    this.globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise();
    this.createSanitizedUrl();
    this.loaded = true;

  }

  createSanitizedUrl() {
    this.sanitizedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`
    http://localhost:4201/main/matching-configurator?tenantId=${this.globalInfo.tenants[0].id}&redirect=http:%2F%2Flocalhost:8080%2Fresponse%2Fmatching-configurator     
    `);


  }

}
