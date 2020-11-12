import { mxgraph } from 'mxgraph';
import { Component, OnInit, AfterContentInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { DialogFactoryDirective } from '../../../_shared/dialogs/_dialog-factory/dialog-factory.component';
import { MatchingEntityDataService } from 'app/main/content/_service/configuration/matching-collector-configuration.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';
import { MatchingOperatorRelationshipService } from 'app/main/content/_service/configuration/matching-operator-relationship.service';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { MatchingConfiguration, MatchingEntityMappingConfiguration } from 'app/main/content/_model/meta/configurations';
import { CConstants } from '../class-configurator/utils-and-constants';
import { MatchingOperatorRelationship, MatchingEntityType, MatchingEntityMappings, MatchingEntity, MatchingDataRequestDTO } from 'app/main/content/_model/matching';
import { Tenant } from 'app/main/content/_model/tenant';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { MyMxCell, MyMxCellType } from '../myMxCell';
import { MatchingConfiguratorPopupMenu } from './popup-menu';
import { PropertyType } from 'app/main/content/_model/meta/property/property';
import { isNullOrUndefined } from 'util';
import { AddClassDefinitionDialogData } from './_dialogs/add-class-definition-dialog/add-class-definition-dialog.component';
import { NewMatchingDialogData } from './_dialogs/new-dialog/new-dialog.component';
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
