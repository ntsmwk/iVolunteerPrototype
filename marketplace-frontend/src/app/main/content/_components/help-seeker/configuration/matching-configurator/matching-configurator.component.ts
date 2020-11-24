import { Component, OnInit } from '@angular/core';
import { DialogFactoryDirective } from '../../../_shared/dialogs/_dialog-factory/dialog-factory.component';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpUrlEncodingCodec } from '@angular/common/http';
import { environment } from 'environments/environment';

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
      `${environment.CONFIGURATOR_URL}/main/matching-configurator?tenantId=${this.globalInfo.tenants[0].id}&redirect=${this.encodedMPUrl}response%2Fmatching-configurator`
      // `${environment.CONFIGURATOR_URL}/main/matching-configurator?tenantId=${this.globalInfo.tenants[0].id}`

    );
  }

}
