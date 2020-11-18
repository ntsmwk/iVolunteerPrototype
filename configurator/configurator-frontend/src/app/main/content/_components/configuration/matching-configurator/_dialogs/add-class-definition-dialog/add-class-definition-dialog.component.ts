import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';

import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { isNullOrUndefined } from 'util';
import { MatchingEntityMappingConfiguration } from 'app/main/content/_model/configurator/configurations';
import { MatchingEntity } from 'app/main/content/_model/matching';
import { AddClassDefinitionGraphDialogComponent, AddClassDefinitionGraphDialogData } from '../add-class-definition-graph-dialog/add-class-definition-graph-dialog.component';

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
    @Inject(MAT_DIALOG_DATA) public data: AddClassDefinitionDialogData,
    public dialog: MatDialog,
  ) { }

  dataSource = new MatTableDataSource<MatchingEntity>();
  displayedColumns = ['checkbox', 'label', 'type'];

  selection: SelectionModel<MatchingEntity>;
  disabledClassDefinitions: ClassDefinition[];

  loaded: boolean;
  tabIndex: number;

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  async ngOnInit() {
    this.tabIndex = 0;

    this.dataSource.data = this.data.matchingEntityConfiguration.mappings.entities;
    this.selection = new SelectionModel<MatchingEntity>(true, []);
    const entities = this.dataSource.data.filter(e => this.data.existingEntityPaths.find(path => path === e.path));

    if (!isNullOrUndefined(entities)) {
      this.selection.select(...entities);
    }
    this.loaded = true;
  }

  isRowDisabled(row: MatchingEntity) {
    return !isNullOrUndefined(
      this.data.existingEntityPaths.find(path => path === row.path)
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

  openGraphDialog() {
    this.openAddClassDefinitionDialog(this.data, this.selection.selected).then((ret: AddClassDefinitionGraphDialogData) => {
      if (!isNullOrUndefined(ret)) {
        this.selection.clear();
        this.selection.select(...ret.addedEntities);
      }
    });
  }

  onSubmit(submitAll: boolean) {
    // Add selected, but filter out existing ones

    if (submitAll) {
      this.selection.select(...this.dataSource.data);
    }

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

  openAddClassDefinitionDialog(data: AddClassDefinitionGraphDialogData, selected: MatchingEntity[]) {
    const dialogRef = this.dialog.open(AddClassDefinitionGraphDialogComponent, {
      width: '90vw',
      height: '90vh',
      data: {
        matchingEntityConfiguration: data.matchingEntityConfiguration,
        existingEntityPaths: data.existingEntityPaths,
        addedEntities: selected,
      },
    });

    let returnValue: AddClassDefinitionGraphDialogData;

    dialogRef.beforeClosed().toPromise()
      .then((result: AddClassDefinitionGraphDialogData) => {
        returnValue = result;
      });

    return dialogRef.afterClosed().toPromise()
      .then(() => {
        return returnValue;
      });
  }

}
