import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PropertyItem, PropertyDefinition } from '../../../_model/meta/Property';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';

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
