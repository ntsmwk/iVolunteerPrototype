import {
  PropertyType,
  FlatPropertyDefinition,
} from 'app/main/content/_model/meta/property/property';
import { Component, OnInit } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';
import { DialogFactoryDirective } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.component';
import { MatTableDataSource } from '@angular/material';
import { Marketplace } from 'app/main/content/_model/marketplace';

import { TreePropertyDefinition } from 'app/main/content/_model/meta/property/tree-property';
import { Router, ActivatedRoute } from '@angular/router';
import { FlatPropertyDefinitionService } from 'app/main/content/_service/meta/core/property/flat-property-definition.service';
import { TreePropertyDefinitionService } from 'app/main/content/_service/meta/core/property/tree-property-definition.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { isNullOrUndefined } from 'util';
import { User } from 'app/main/content/_model/user';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { Tenant } from 'app/main/content/_model/tenant';

export interface PropertyEntry {
  id: string;
  name: string;
  type: PropertyType;
  timestamp: Date;
}

@Component({
  selector: "app-property-list",
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss'],
  animations: fuseAnimations,
  providers: [DialogFactoryDirective],
})
export class PropertyListComponent implements OnInit {
  dataSource = new MatTableDataSource<PropertyEntry>();
  displayedColumns = ['type', 'name', 'filler', 'actions'];

  marketplace: Marketplace;
  tenantAdmin: User;
  tenants: Tenant[];

  propertyDefinitions: FlatPropertyDefinition<any>[];
  enumDefinitions: TreePropertyDefinition[];

  propertyEnumEntries: PropertyEntry[];

  dropdownFilterValue: string;
  textSearchValue: string;
  isLoaded: boolean;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private propertyDefinitionService: FlatPropertyDefinitionService,
    private enumDefinitionService: TreePropertyDefinitionService,
    private loginService: LoginService,
    private dialogFactory: DialogFactoryDirective
  ) { }

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

  onRowSelect(p: FlatPropertyDefinition<any>) {
    this.router.navigate([
      '/main/properties/' + this.marketplace.id + '/' + p.id,
    ]);
  }

  async loadAllProperties() {
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
    this.tenantAdmin = globalInfo.user;
    this.tenants = globalInfo.tenants;

    Promise.all([
      this.propertyDefinitionService
        .getAllPropertyDefinitons(this.marketplace, this.tenants[0].id)
        .toPromise()
        .then((propertyDefinitions: FlatPropertyDefinition<any>[]) => {
          this.propertyDefinitions = propertyDefinitions;
        }),
      this.enumDefinitionService
        .getAllPropertyDefinitionsForTenant(this.marketplace, this.tenants[0].id)
        .toPromise()
        .then((enumDefinitions: TreePropertyDefinition[]) => {
          this.enumDefinitions = enumDefinitions;
        }),
    ]).then(() => {
      this.updatePropertyAndEnumEntryList();
      this.applyFiltersFromParams();
      this.isLoaded = true;
    });
  }

  private updatePropertyAndEnumEntryList() {
    this.propertyEnumEntries = [];
    this.propertyEnumEntries.push(...this.propertyDefinitions);
    this.propertyEnumEntries.push(
      ...this.enumDefinitions.map((e) => ({
        id: e.id,
        name: e.name,
        type: PropertyType.TREE,
        timestamp: e.timestamp,
      }))
    );
    this.dataSource.data = this.propertyEnumEntries;
  }

  applyTypeFilter() {
    switch (this.dropdownFilterValue) {
      case 'all':
        this.dataSource.data = this.propertyEnumEntries;
        break;
      case 'properties':
        this.dataSource.data = this.propertyEnumEntries.filter(
          (entry: PropertyEntry) => entry.type !== PropertyType.TREE
        );
        break;
      case 'enums':
        this.dataSource.data = this.propertyEnumEntries.filter(
          (entry: PropertyEntry) => entry.type === PropertyType.TREE
        );
        break;
      default:
        console.error('undefined type');
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
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: { [key]: value },
      queryParamsHandling: 'merge',
      skipLocationChange: true,
    });
  }

  viewPropertyAction(property: FlatPropertyDefinition<any>) {
    this.router.navigate(
      ['main/property/detail/view/' + this.marketplace.id + '/' + property.id],
      { queryParams: { ref: 'list' } }
    );
  }

  newAction(key: string) {
    this.router.navigate(['main/property-builder/' + this.marketplace.id], {
      queryParams: { type: key },
    });
  }

  editAction(entry: PropertyEntry) {
    const builderType = entry.type === PropertyType.TREE ? 'tree' : 'flat';
    this.router.navigate(
      ['main/property-builder/' + this.marketplace.id + '/' + entry.id],
      { queryParams: { type: builderType } }
    );
  }

  deleteAction(entry: PropertyEntry) {
    this.dialogFactory
      .confirmationDialog(
        'Löschen',
        'Dieser Vorgang kann nicht rückgeängig gemacht werden'
      )
      .then((ret) => {
        if (ret && entry.type !== PropertyType.TREE) {
          this.propertyDefinitionService
            .deletePropertyDefinition(this.marketplace, entry.id)
            .toPromise()
            .then((innerret) => {
              this.deleteFromLists('flat', entry.id);
            });
        } else if (ret && entry.type === PropertyType.TREE) {
          this.enumDefinitionService
            .deletePropertyDefinition(this.marketplace, entry.id)
            .toPromise()
            .then((innerret) => {
              this.deleteFromLists('tree', entry.id);
            });
        }
      });
  }

  deleteFromLists(key: 'tree' | 'flat', id: string) {
    key === 'tree'
      ? this.enumDefinitions.filter((e) => e.id !== id)
      : this.propertyDefinitions.filter((e) => e.id !== id);
    this.propertyEnumEntries = this.propertyEnumEntries.filter(
      (e) => e.id !== id
    );
    this.dataSource.data = this.dataSource.data.filter((e) => e.id !== id);
  }

  getPropertyTypeLabel(propertyType: PropertyType) {
    return PropertyType.getLabelForPropertyType(propertyType);
  }
}
