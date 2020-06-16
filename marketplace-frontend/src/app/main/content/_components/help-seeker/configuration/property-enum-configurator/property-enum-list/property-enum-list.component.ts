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
import { EnumDefinition, EnumEntry } from 'app/main/content/_model/meta/enum';
import { DialogFactoryModule } from 'app/main/content/_shared_components/dialogs/_dialog-factory/dialog-factory.module';
import { DialogFactoryDirective } from 'app/main/content/_shared_components/dialogs/_dialog-factory/dialog-factory.component';

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
  animations: fuseAnimations,
  providers: [DialogFactoryDirective]
})
export class PropertyEnumListComponent implements OnInit {

  dataSource = new MatTableDataSource<PropertyEnumEntry>();
  displayedColumns = ['type', 'name', 'filler', 'actions'];

  marketplace: Marketplace;
  helpseeker: Helpseeker;

  propertyDefinitions: PropertyDefinition<any>[];
  enumDefinitions: EnumDefinition[];

  propertyEnumEntries: PropertyEnumEntry[];

  dropdownFilterValue: string;
  isLoaded: boolean;

  constructor(private router: Router,
    private propertyDefinitionService: PropertyDefinitionService,
    private enumDefinitionService: EnumDefinitionService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private flexProdService: CoreFlexProdService,
    private dialogFactory: DialogFactoryDirective) {
  }

  ngOnInit() {
    this.isLoaded = false;
    this.dropdownFilterValue = 'all';
    this.dataSource.filterPredicate = function (data, filter: string): boolean {
      return data.name.toLowerCase().includes(filter);
    };
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


  applyTypeFilter() {
    switch (this.dropdownFilterValue) {
      case 'all': this.dataSource.data = this.propertyEnumEntries; break;
      case 'properties': this.dataSource.data = this.propertyEnumEntries.filter((entry: PropertyEnumEntry) => entry.type !== PropertyType.ENUM); break;
      case 'enums': this.dataSource.data = this.propertyEnumEntries.filter((entry: PropertyEnumEntry) => entry.type === PropertyType.ENUM); break;
      default: console.error('undefined type');
    }

  }


  applyTextFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    if (isNullOrUndefined(filterValue)) {
      this.dataSource.filter = undefined;
    } else {
      this.dataSource.filter = filterValue.trim().toLowerCase();
    }
  }

  viewPropertyAction(property: PropertyDefinition<any>) {
    this.router.navigate(['main/property/detail/view/' + this.marketplace.id + '/' + property.id], { queryParams: { ref: 'list' } });
  }

  newAction(key: string) {
    this.router.navigate(['main/property-builder/' + this.marketplace.id], { queryParams: { type: key } });
  }

  editAction(entry: PropertyEnumEntry) {
    const builderType = entry.type === PropertyType.ENUM ? 'enum' : 'property';
    this.router.navigate(['main/property-builder/' + this.marketplace.id + '/' + entry.id], { queryParams: { type: builderType } });
  }

  deleteAction(entry: PropertyEnumEntry) {
    this.dialogFactory.confirmationDialog('Löschen', 'Dieser Vorgang kann nicht rückgeängig gemacht werden').then((ret) => {

      if (ret && entry.type !== PropertyType.ENUM) {
        this.propertyDefinitionService.deletePropertyDefinition(this.marketplace, entry.id).toPromise().then((innerret) => {
          this.deleteFromLists('property', entry.id);
        });
      } else if (ret && entry.type === PropertyType.ENUM) {
        this.enumDefinitionService.deleteEnumDefinition(this.marketplace, entry.id).toPromise().then((innerret) => {
          this.deleteFromLists('enum', entry.id);
        });
      }
    });
  }

  deleteFromLists(key: 'enum' | 'property', id: string) {
    key === 'enum' ? this.enumDefinitions.filter(e => e.id !== id) : this.propertyDefinitions.filter(e => e.id !== id);
    this.propertyEnumEntries = this.propertyEnumEntries.filter(e => e.id !== id);
    this.dataSource.data = this.dataSource.data.filter(e => e.id !== id);
  }

  getPropertyTypeLabel(propertyType: PropertyType) {
    return PropertyType.getLabelForPropertyType(propertyType);
  }

  // getImagePathPropertyType(propertyType: PropertyType) {
  //   return this.propertyTypePalettes.find(p => p.id === propertyType).imgPath;
  // }
}
