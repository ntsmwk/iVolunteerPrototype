import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FlatPropertyDefinition, PropertyType } from '../../../../_model/configurator/property/property';
import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';

export interface RemovePropertyDialogData {
  classDefinition: ClassDefinition;
}

interface PropertyEntry {
  id: string;
  name: string;
  type: PropertyType;
}

@Component({
  selector: 'remove-property-dialog',
  templateUrl: './remove-dialog.component.html',
  styleUrls: ['./remove-dialog.component.scss']
})
export class RemovePropertyDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<RemovePropertyDialogData>, @Inject(MAT_DIALOG_DATA)
    public data: RemovePropertyDialogData,
  ) {
  }

  datasource = new MatTableDataSource<PropertyEntry>();
  displayedColumns = ['checkbox', 'label', 'type'];
  entryList: PropertyEntry[];
  selection = new SelectionModel<PropertyEntry>(true, []);

  loaded: boolean;

  @ViewChild(MatSort, { static: true }) sort: MatSort;


  async ngOnInit() {
    this.entryList = [];
    this.entryList.push(...this.data.classDefinition.properties);


    this.datasource.data = this.entryList;
    this.loaded = true;
  }

  isDisabled(propertyDefinition: FlatPropertyDefinition<any>) {
    return false;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }

  onRowClick(row: PropertyEntry) {
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
