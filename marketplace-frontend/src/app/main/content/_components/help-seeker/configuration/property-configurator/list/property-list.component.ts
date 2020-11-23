import { Component, OnInit } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpUrlEncodingCodec } from '@angular/common/http';
import { environment } from 'environments/environment';

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
  encodedMPUrl: string;


  async ngOnInit() {
    this.loaded = false;
    const codec = new HttpUrlEncodingCodec();

    this.globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise();
    this.encodedMPUrl = codec.encodeValue(this.globalInfo.marketplace.url + '/');

    this.createSanitizedUrl();
    this.loaded = true;

  }

  createSanitizedUrl() {
    this.sanitizedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(
      `${environment.CONFIGURATOR_URL}/main/properties/all?tenantId=${this.globalInfo.tenants[0].id}&redirect=${this.encodedMPUrl}response%2Fproperty-configurator`
      // `${environment.CONFIGURATOR_URL}/main/properties/all?tenantId=${this.globalInfo.tenants[0].id}`

    );


  }
}
