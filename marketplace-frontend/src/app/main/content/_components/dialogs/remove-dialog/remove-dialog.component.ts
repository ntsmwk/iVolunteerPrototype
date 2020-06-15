import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PropertyItem, PropertyDefinition, PropertyType } from '../../../_model/meta/property';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';

export interface RemoveDialogData {
  marketplace: Marketplace;
  classDefinition: ClassDefinition;
}

interface PropertyOrEnumEntry {
  id: string;
  name: string;
  type: PropertyType;
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

  datasource = new MatTableDataSource<PropertyOrEnumEntry>();
  displayedColumns = ['checkbox', 'label', 'type'];
  entryList: PropertyOrEnumEntry[];
  selection = new SelectionModel<PropertyOrEnumEntry>(true, []);

  loaded: boolean;

  @ViewChild(MatSort, { static: true }) sort: MatSort;


  ngOnInit() {

    this.entryList = [];
    this.entryList.push(...this.data.classDefinition.properties);
    this.entryList.push(...this.data.classDefinition.enums.map(e => ({ id: e.id, name: e.name, type: PropertyType.ENUM })));


    this.datasource.data = this.entryList;
    this.loaded = true;
  }

  isDisabled(propertyDefinition: PropertyDefinition<any>) {
    return false;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }

  onRowClick(row: PropertyOrEnumEntry) {
    if (this.selection.isSelected(row)) {
      this.selection.deselect(row);
    } else {
      this.selection.select(row);
    }
  }

  onSubmit() {
    this.data.classDefinition.properties = this.data.classDefinition.properties.filter(p => this.selection.selected.findIndex(s => s.id === p.id) === -1);
    this.data.classDefinition.enums = this.data.classDefinition.enums.filter(p => this.selection.selected.findIndex(s => s.id === p.id) === -1);

    this.dialogRef.close(this.data);
  }
}
