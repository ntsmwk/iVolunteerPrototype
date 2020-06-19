import { PropertyType, PropertyDefinition } from 'app/main/content/_model/meta/property';
import { Component, OnInit } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';
import { DialogFactoryDirective } from 'app/main/content/_components/shared/dialogs/_dialog-factory/dialog-factory.component';
import { MatTableDataSource } from '@angular/material';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { EnumDefinition } from 'app/main/content/_model/meta/enum';
import { Router, ActivatedRoute } from '@angular/router';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { EnumDefinitionService } from 'app/main/content/_service/meta/core/enum/enum-configuration.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { isNullOrUndefined } from 'util';

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
  textSearchValue: string;
  isLoaded: boolean;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private propertyDefinitionService: PropertyDefinitionService,
    private enumDefinitionService: EnumDefinitionService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
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

  applyFiltersFromParams() {
    this.route.queryParams.subscribe((params) => {
      this.applyFilters(params);
    });
  }

  onRowSelect(p: PropertyDefinition<any>) {
    this.router.navigate(['/main/properties/' + this.marketplace.id + '/' + p.id]);
  }

  loadAllProperties() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.helpseeker = helpseeker;

      this.helpSeekerService.findRegisteredMarketplaces(helpseeker.id).toPromise().then((marketplace: Marketplace) => {
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
            this.applyFiltersFromParams();
            this.isLoaded = true;
          });
        }
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
    this.patchFilterParam('filter', this.dropdownFilterValue);
  }

  handleTextFilterEvent(event: Event) {
    this.applyTextFilter(this.textSearchValue);
  }

  applyTextFilter(filterValue: string) {
    if (isNullOrUndefined(filterValue) || filterValue.length <= 0) {
      this.dataSource.filter = null;
      this.patchFilterParam('searchString', null);
    } else {
      this.dataSource.filter = filterValue.trim().toLowerCase();
      this.patchFilterParam('searchString', filterValue);
    }
  }

  applyFilters(params) {
    if (!isNullOrUndefined(params['filter'])) {
      this.dropdownFilterValue = params['filter'];
      this.applyTypeFilter();
    }
    if (!isNullOrUndefined(params['searchString'])) {
      this.applyTextFilter(params['searchString']);
      this.textSearchValue = params['searchString'];
    }
  }

  patchFilterParam(key: string, value: string) {
    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: { [key]: value },
        queryParamsHandling: 'merge',
        skipLocationChange: true,

      });
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

}
