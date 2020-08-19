import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import {
  MatDialogRef, MAT_DIALOG_DATA,
} from '@angular/material/dialog';

import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { isNullOrUndefined } from 'util';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { LoginService } from 'app/main/content/_service/login.service';
import { Tenant } from 'app/main/content/_model/tenant';
import { MatchingEntityMappingConfiguration } from 'app/main/content/_model/meta/configurations';
import { MatchingEntity } from 'app/main/content/_model/matching';

export interface AddClassDefinitionDialogData {
  matchingEntityConfiguration: MatchingEntityMappingConfiguration;
  existingEntityPaths: string[];
  addedEntities: MatchingEntity[];

}

@Component({
  selector: "add-class-definition-dialog",
  templateUrl: './add-class-definition-dialog.component.html',
  styleUrls: ['./add-class-definition-dialog.component.scss'],
})
export class AddClassDefinitionDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<AddClassDefinitionDialogData>,
    @Inject(MAT_DIALOG_DATA)
    public data: AddClassDefinitionDialogData,
    private loginService: LoginService,
  ) { }

  dataSource = new MatTableDataSource<MatchingEntity>();
  displayedColumns = ['checkbox', 'label', 'type'];

  selection: SelectionModel<MatchingEntity>;

  initialClassDefinitions: ClassDefinition[];
  disabledClassDefinitions: ClassDefinition[];

  loaded: boolean;
  tabIndex: number;

  globalInfo: GlobalInfo;
  tenant: Tenant;

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  async ngOnInit() {
    this.tabIndex = 0;

    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );

    this.tenant = this.globalInfo.tenants[0];

    this.dataSource.data = this.data.matchingEntityConfiguration.mappings.entities;
    this.initialClassDefinitions = [];
    this.disabledClassDefinitions = [];
    this.selection = new SelectionModel<MatchingEntity>(true, []);


    console.log(this.dataSource.data);
    this.loaded = true;
    // Promise.all([
    //   this.flatPropertyDefinitionService.getAllPropertyDefinitons(this.globalInfo.marketplace, this.tenant.id)
    //     .toPromise()
    //     .then((ret: FlatPropertyDefinition<any>[]) => {
    //       this.flatPropertyDataSource.data = ret;
    //       this.allFlatPropertyDefinitions = ret;

    //       this.initialFlatPropertyDefinitions = ret.filter((p) =>
    //         this.data.classDefinition.properties.find((q) => q.id === p.id)
    //       );

    //       const parentClassProperties = this.findParentProperties();
    //       const parentProperties = ret.filter((p) =>
    //         parentClassProperties.find((q) => q.id === p.id)
    //       );

    //       this.disabledFlatPropertyDefinitions = [];
    //       this.disabledFlatPropertyDefinitions.push(...this.initialFlatPropertyDefinitions, ...parentProperties);

    //       this.flatPropertySelection.select(...this.initialFlatPropertyDefinitions);
    //     }),
    //   this.treePropertyDefinitionService
    //     .getAllPropertyDefinitionsForTenant(this.globalInfo.marketplace, this.tenant.id)
    //     .toPromise()
    //     .then((ret: TreePropertyDefinition[]) => {
    //       this.treePropertyDataSource.data = ret;
    //       this.allTreePropertyDefinitions = ret;

    //       this.initialTreePropertyDefinitions = ret.filter((e) =>
    //         this.data.classDefinition.properties.find((f) => f.id === e.id)
    //       );
    //       this.disabledTreePropertyDefinitions = [];
    //       this.disabledTreePropertyDefinitions.push(...this.initialTreePropertyDefinitions);
    //       this.treePropertySelection
    //         .select(...this.initialTreePropertyDefinitions);
    //     }),
    // ]).then(() => {
    //   this.loaded = true;
    // });
  }

  isRowDisabled(row: MatchingEntity) {
    return !isNullOrUndefined(
      this.disabledClassDefinitions.find((p) => p.id === row.classDefinition.id)
    );
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }


  onRowClick(row: MatchingEntity) {
    if (this.isRowDisabled(row)) {
      return;
    }

    this.selection.isSelected(row) ? this.selection.deselect(row) : this.selection.select(row);
  }

  onSubmit() {
    // Add selected, but filter out existing ones

    let added: MatchingEntity[];

    if (isNullOrUndefined(this.data.existingEntityPaths)) {
      added = this.selection.selected;
    } else {
      added = this.selection.selected.filter(
        (p) =>
          this.data.existingEntityPaths.findIndex((q) => p.path === q) === -1
      );
    }

    this.data.addedEntities = added;
    this.dialogRef.close(this.data);
  }

}
