import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PropertyItem, ClassProperty, PropertyDefinition } from '../../../_model/meta/Property';
import { Router } from '@angular/router';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { SelectionModel } from '@angular/cdk/collections';
import { isNullOrUndefined } from 'util';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';

export interface RemoveDialogData {
  marketplace: Marketplace;
  classDefinition: ClassDefinition;
}

@Component({
  selector: 'remove-dialog',
  templateUrl: './remove-dialog.component.html',
  styleUrls: ['./remove-dialog.component.scss']
})
export class RemoveDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<RemoveDialogData>, @Inject(MAT_DIALOG_DATA)
    public data: RemoveDialogData,
    private router: Router,
    private classDefinitionService: ClassDefinitionService
  ) {
  }

  datasource = new MatTableDataSource<PropertyItem>();
  displayedColumns = ['checkbox', 'label', 'type'];
  loaded: boolean;
  selection = new SelectionModel<PropertyItem>(true, []);


  @ViewChild(MatSort, { static: true }) sort: MatSort;


  ngOnInit() {
    this.datasource.data = this.data.classDefinition.properties;
    this.loaded = true;
  }



  /** Whether the number of selected elements matches the total number of rows. */
  // isAllSelected() {
  //   const numSelected = this.selection.selected.length;
  //   const numRows = this.datasource.data.length;
  //   return numSelected === numRows;
  // }

  // /** Selects all rows if they are not all selected; otherwise clear selection. */
  // masterToggle() {
  //   if (this.isAllSelected()) {
  //     this.selection.clear();
  //     this.selection.select(...this.initialProperties);
  //   } else {
  //     this.datasource.data.forEach(row => this.selection.select(row));
  //   }
  // }

  isDisabled(propertyDefinition: PropertyDefinition<any>) {
    return false;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }

  onRowClick(row: PropertyDefinition<any>) {
    if (this.selection.isSelected(row)) {
      this.selection.deselect(row);
    } else {
      this.selection.select(row);
    }
  }

  onSubmit() {
    this.data.classDefinition.properties = this.data.classDefinition.properties.filter(p => this.selection.selected.findIndex(s => s.id === p.id) === -1);
    this.dialogRef.close(this.data);
  }
}
