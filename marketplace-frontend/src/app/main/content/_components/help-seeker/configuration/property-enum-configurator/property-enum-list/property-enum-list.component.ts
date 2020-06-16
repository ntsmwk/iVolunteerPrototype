import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { fuseAnimations } from '@fuse/animations';
import { LoginService } from '../../../../../_service/login.service';
import { CoreHelpSeekerService } from '../../../../../_service/core-helpseeker.service';
import { ParticipantRole } from '../../../../../_model/participant';
import { Marketplace } from '../../../../../_model/marketplace';
import { isNullOrUndefined } from 'util';
import { PropertyDefinitionService } from '../../../../../_service/meta/core/property/property-definition.service';
import { CoreFlexProdService } from '../../../../../_service/core-flexprod.service';
import { Helpseeker } from '../../../../../_model/helpseeker';
import { PropertyDefinition, PropertyType } from 'app/main/content/_model/meta/property';
import { EnumDefinitionService } from 'app/main/content/_service/meta/core/enum/enum-configuration.service';
import { EnumDefinition } from 'app/main/content/_model/meta/enum';

export interface PropertyEnumEntry {
  id: string;
  name: string;
  type: PropertyType;
  timestamp: Date;
}

@Component({
  selector: 'app-property-enum-list',
  templateUrl: './property-enum-list.component.html',
  styleUrls: ['./property-enum-list.component.scss'],
  animations: fuseAnimations
})
export class PropertyEnumListComponent implements OnInit {

  dataSource = new MatTableDataSource<PropertyEnumEntry>();
  displayedColumns = ['id', 'name', 'defaultValue', 'kind', 'actions'];

  marketplace: Marketplace;
  helpseeker: Helpseeker;

  propertyDefinitions: PropertyDefinition<any>[];
  enumDefinitions: EnumDefinition[];

  propertyEnumEntries: PropertyEnumEntry[];

  customOnly: boolean;
  isLoaded: boolean;

  constructor(private router: Router,
    private propertyDefinitionService: PropertyDefinitionService,
    private enumDefinitionService: EnumDefinitionService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private flexProdService: CoreFlexProdService) {
  }

  ngOnInit() {
    this.isLoaded = false;
    this.customOnly = false;
    this.loadAllProperties();

  }

  onRowSelect(p: PropertyDefinition<any>) {
    this.router.navigate(['/main/properties/' + this.marketplace.id + '/' + p.id]);
  }



  loadAllProperties() {

    let service: CoreHelpSeekerService | CoreFlexProdService;

    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.helpseeker = helpseeker;
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
        if (role === 'FLEXPROD') {
          service = this.flexProdService;
        } else if (role === 'HELP_SEEKER') {
          service = this.helpSeekerService;
        } else {
          return;
        }
      }).then(() => {
        service.findRegisteredMarketplaces(helpseeker.id).toPromise().then((marketplace: Marketplace) => {
          if (!isNullOrUndefined(marketplace)) {
            this.marketplace = marketplace;
            Promise.all([
              this.propertyDefinitionService.getAllPropertyDefinitons(marketplace, this.helpseeker.tenantId).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
                this.propertyDefinitions = propertyDefinitions;

              }),
              this.enumDefinitionService.getAllEnumDefinitionsForTenant(marketplace, this.helpseeker.tenantId).toPromise().then((enumDefinitions: EnumDefinition[]) => {
                this.enumDefinitions = enumDefinitions;
              })

            ]).then(() => {
              this.updatePropertyAndEnumEntryList();
              this.isLoaded = true;

            });
          }
        });
      });
    });


  }

  private updatePropertyAndEnumEntryList() {
    this.propertyEnumEntries = [];
    this.propertyEnumEntries.push(...this.propertyDefinitions);
    this.propertyEnumEntries.push(...this.enumDefinitions.map(e => ({ id: e.id, name: e.name, type: PropertyType.ENUM, timestamp: e.timestamp })));
    this.dataSource.data = this.propertyEnumEntries;
  }


  viewPropertyAction(property: PropertyDefinition<any>) {
    this.router.navigate(['main/property/detail/view/' + this.marketplace.id + '/' + property.id], { queryParams: { ref: 'list' } });
  }

  // newPropertyAction() {
  //   this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/']);
  // }

  newPropertyAction() {
    this.router.navigate(['main/property-builder/' + this.marketplace.id + '/']);
  }

  editPropertyAction(property: PropertyDefinition<any>) {
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/' + property.id]);
  }

  deletePropertyAction(property: PropertyDefinition<any>) {
    this.propertyDefinitionService.deletePropertyDefinition(this.marketplace, property.id).toPromise().then(() => {
      this.ngOnInit();
    });
  }
}
