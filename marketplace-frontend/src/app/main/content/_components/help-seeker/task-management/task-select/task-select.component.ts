import { Component, OnInit } from "@angular/core";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatTableDataSource } from "@angular/material";
import { ClassArchetype } from "app/main/content/_model/meta/class";
import { Tenant } from "app/main/content/_model/tenant";
import { FormBuilder } from "@angular/forms";
import { Router } from "@angular/router";
import { LoginService } from "app/main/content/_service/login.service";
import { ClassDefinitionService } from "app/main/content/_service/meta/core/class/class-definition.service";
import { isNullOrUndefined } from "util";
import { ClassDefinition } from "app/main/content/_model/meta/class";
import { User, UserRole } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { SafeResourceUrl, DomSanitizer } from '@angular/platform-browser';

@Component({
  templateUrl: "./task-select.component.html",
  styleUrls: ["./task-select.component.scss"]
})
export class FuseTaskSelectComponent implements OnInit {
  globalInfo: GlobalInfo;
  loaded = false;
  sanitizedUrl: SafeResourceUrl;


  constructor(
    private loginService: LoginService,
    private classDefService: ClassDefinitionService,
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
    http://localhost:4201/main/task-select?tenantId=${this.globalInfo.tenants[0].id}&redirect=http:%2F%2Flocalhost:8080%2Fresponse%2Fclass-instance-configurator
    `);


  }

  navigateBack() {
    window.history.back();
  }
}
