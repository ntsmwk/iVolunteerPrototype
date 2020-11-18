import { Component, OnInit } from '@angular/core';
import { fuseAnimations } from '@fuse/animations';
import { DialogFactoryDirective } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.component';
import { MatTableDataSource } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';
import { FlatPropertyDefinitionService } from 'app/main/content/_service/meta/core/property/flat-property-definition.service';
import { TreePropertyDefinitionService } from 'app/main/content/_service/meta/core/property/tree-property-definition.service';
import { isNullOrUndefined } from 'util';
import { TreePropertyDefinition } from 'app/main/content/_model/configurator/property/tree-property';
import { PropertyType, FlatPropertyDefinition } from 'app/main/content/_model/configurator/property/property';
import { ResponseService } from 'app/main/content/_service/response.service';

export interface PropertyEntry {
  id: string;
  name: string;
  type: PropertyType;
  timestamp: Date;
  custom: boolean;
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

  propertyDefinitions: FlatPropertyDefinition<any>[];
  treePropertyDefinitions: TreePropertyDefinition[];

  propertyEntries: PropertyEntry[];

  dropdownFilterValue: string;
  textSearchValue: string;
  customOnly: boolean;

  isLoaded: boolean;


  tenantId: string;
  redirectUrl: string;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private flatPropertyDefinitionService: FlatPropertyDefinitionService,
    private treePropertyDefinitionService: TreePropertyDefinitionService,
    private dialogFactory: DialogFactoryDirective,
    private responseService: ResponseService,
  ) { }

  ngOnInit() {
    this.isLoaded = false;
    this.customOnly = true;
    this.dropdownFilterValue = 'all';
    this.dataSource.filterPredicate = function (data, filter: string): boolean {
      return data.name.toLowerCase().includes(filter);
    };

    this.route.queryParams.subscribe(params => {
      if (isNullOrUndefined(params['tenantId']) || isNullOrUndefined(params['redirect'])) {
        this.router.navigate(['main/invalid-parameters']);
      } else {
        this.tenantId = params['tenantId'];
        this.redirectUrl = params['redirect'];
      }
    });

    this.loadAllProperties();
  }

  applyFiltersFromParams() {
    this.route.queryParams.subscribe((params) => {
      this.applyFilters(params);
    });
  }

  onRowSelect(p: FlatPropertyDefinition<any>) {
    // this.router.navigate([
    //   '/main/properties/' + this.marketplace.id + '/' + p.id,
    // ]);
  }

  async loadAllProperties() {

    Promise.all([
      this.flatPropertyDefinitionService
        .getAllPropertyDefinitonsForTenant(this.tenantId)
        .toPromise().then((propertyDefinitions: FlatPropertyDefinition<any>[]) => {
          this.propertyDefinitions = propertyDefinitions;
        }),
      this.treePropertyDefinitionService
        .getAllPropertyDefinitionsForTenant(this.tenantId)
        .toPromise().then((treePropertyDefinitions: TreePropertyDefinition[]) => {
          this.treePropertyDefinitions = treePropertyDefinitions;
        }),
    ]).then(() => {
      this.updatePropertyEntryList();
      this.applyFiltersFromParams();
      this.isLoaded = true;
    });
  }

  private updatePropertyEntryList() {
    this.propertyEntries = [];
    if (!isNullOrUndefined(this.propertyEntries)) {
      this.propertyEntries.push(...this.propertyDefinitions);
    }
    if (!isNullOrUndefined(this.treePropertyDefinitions)) {
      this.propertyEntries.push(
        ...this.treePropertyDefinitions.map((e) => ({
          id: e.id,
          name: e.name,
          type: PropertyType.TREE,
          timestamp: e.timestamp,
          custom: e.custom,
        }))
      );
    }
    this.dataSource.data = this.propertyEntries;
  }

  applyTypeFilter() {
    switch (this.dropdownFilterValue) {
      case 'all':
        if (this.customOnly) {
          this.dataSource.data = this.propertyEntries.filter((entry: PropertyEntry) => entry.custom);
        } else {
          this.dataSource.data = this.propertyEntries;
        }
        break;
      case 'flat':
        this.dataSource.data = this.propertyEntries.filter(
          (entry: PropertyEntry) => entry.type !== PropertyType.TREE
        );

        if (this.customOnly) {
          this.dataSource.data = this.dataSource.data.filter(entry => entry.custom);
        }
        break;
      case 'tree':
        this.dataSource.data = this.propertyEntries.filter(
          (entry: PropertyEntry) => entry.type === PropertyType.TREE
        );
        if (this.customOnly) {
          this.dataSource.data = this.dataSource.data.filter(entry => entry.custom);
        }
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
    } else {
      this.dropdownFilterValue = 'all';
    }
    this.applyTypeFilter();


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

  handleCustomOnlyToggle() {
    this.applyTypeFilter();
  }

  viewPropertyAction(property: FlatPropertyDefinition<any>) {
    // this.router.navigate(
    //   ['main/property/detail/view/' + this.marketplace.id + '/' + property.id],
    //   { queryParams: { ref: 'list' } }
    // );
  }

  newAction(key: string) {
    this.router.navigate(['main/property-builder'], {
      queryParams: { type: key, tenantId: this.tenantId, redirect: this.redirectUrl },
    });
  }

  editAction(entry: PropertyEntry) {
    const builderType = entry.type === PropertyType.TREE ? 'tree' : 'flat';
    this.router.navigate(
      ['main/property-builder/' + entry.id],
      { queryParams: { type: builderType, tenantId: this.tenantId, redirect: this.redirectUrl } }
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
          this.responseService.sendPropertyConfiguratorResponse(this.redirectUrl, [entry.id], undefined, "delete").toPromise().then(() => {
            this.flatPropertyDefinitionService.deletePropertyDefinition(entry.id).toPromise().then(() => {
              this.deleteFromLists('flat', entry.id);
            });
          }).catch(error => {
            console.error("error - rollback");
            console.log(error);
          });
        } else if (ret && entry.type === PropertyType.TREE) {


          this.responseService.sendPropertyConfiguratorResponse(this.redirectUrl, undefined, [entry.id], "delete").toPromise().then(() => {
            this.treePropertyDefinitionService.deletePropertyDefinition(entry.id).toPromise().then(() => {
              this.deleteFromLists('tree', entry.id);
            });
          }).catch(error => {
            console.error("error - rollback");
            console.log(error);
          });
        }
      });
  }

  deleteFromLists(key: 'tree' | 'flat', id: string) {
    key === 'tree'
      ? this.treePropertyDefinitions.filter((e) => e.id !== id)
      : this.propertyDefinitions.filter((e) => e.id !== id);
    this.propertyEntries = this.propertyEntries.filter(
      (e) => e.id !== id
    );
    this.dataSource.data = this.dataSource.data.filter((e) => e.id !== id);
  }

  getPropertyTypeLabel(propertyType: PropertyType) {
    return PropertyType.getLabelForPropertyType(propertyType);
  }
}
