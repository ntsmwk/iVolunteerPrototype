import { Component, OnInit } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: "app-property-list",
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss'],
  animations: fuseAnimations,
  providers: [],
})
export class PropertyListComponent implements OnInit {
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
    http://localhost:4201/main/properties/all?tenantId=${this.globalInfo.tenants[0].id}
    `);


  }
}
