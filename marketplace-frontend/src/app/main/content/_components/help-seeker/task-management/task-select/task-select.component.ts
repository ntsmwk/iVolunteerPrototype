import { Component, OnInit } from '@angular/core';
import { LoginService } from 'app/main/content/_service/login.service';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { SafeResourceUrl, DomSanitizer } from '@angular/platform-browser';
import { HttpUrlEncodingCodec } from '@angular/common/http';
import { environment } from 'environments/environment';

@Component({
  templateUrl: './task-select.component.html',
  styleUrls: ['./task-select.component.scss']
})
export class FuseTaskSelectComponent implements OnInit {
  globalInfo: GlobalInfo;
  loaded = false;
  sanitizedUrl: SafeResourceUrl;
  encodedMPUrl: string;


  constructor(
    private loginService: LoginService,
    private sanitizer: DomSanitizer
  ) { }

  async ngOnInit() {
    this.loaded = false;
    this.sanitizedUrl = undefined;
    const codec = new HttpUrlEncodingCodec();

    this.globalInfo = <GlobalInfo>await this.loginService.getGlobalInfo().toPromise();
    this.encodedMPUrl = codec.encodeValue(this.globalInfo.marketplace.url + '/');

    this.createSanatizedUrl();

    this.loaded = true;
  }

  createSanatizedUrl() {
    this.sanitizedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(`
    ${environment.CONFIGURATOR_URL}/main/task-select?tenantId=${this.globalInfo.tenants[0].id}&redirect=${this.encodedMPUrl}response%2Fclass-instance-configurator
    `);


  }

  navigateBack() {
    window.history.back();
  }
}
