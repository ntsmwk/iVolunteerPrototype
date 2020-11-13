import { Component, OnInit } from '@angular/core';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: "app-configurator",
  templateUrl: './configurator.component.html',
  styleUrls: ['./configurator.component.scss']
})
export class ConfiguratorComponent implements OnInit {
  globalInfo: GlobalInfo;
  loaded = false;
  sanitizedUrl: SafeResourceUrl;


  constructor(
    private loginService: LoginService,
    private sanitizer: DomSanitizer
  ) { }

  async ngOnInit() {
    this.loaded = false;
    this.sanitizedUrl = undefined;
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.createSanatizedUrl();


    this.loaded = true;
  }

  createSanatizedUrl() {
    this.sanitizedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`
    http://localhost:4201/main/class-configurator?tenantId=${this.globalInfo.tenants[0].id}&redirect=http:%2F%2Flocalhost:8080%2Fresponse%2Fclass-configurator
     `)


  }

  navigateBack() {
    window.history.back();
  }
}
