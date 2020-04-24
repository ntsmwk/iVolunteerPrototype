import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PropertyItem, ClassProperty, PropertyInstance, PropertyDefinition } from '../../../_model/meta/Property';
import { Router } from '@angular/router';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { SelectionModel } from '@angular/cdk/collections';
import { isNullOrUndefined } from 'util';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';

export interface AddPropertyDialogData {
  marketplace: Marketplace;
  classDefinition: ClassDefinition;
}

@Component({
  selector: 'add-property-dialog',
  templateUrl: './add-property-dialog.component.html',
  styleUrls: ['./add-property-dialog.component.scss']
})
export class AddPropertyDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<AddPropertyDialogData>, @Inject(MAT_DIALOG_DATA)
    public data: AddPropertyDialogData,
    private router: Router,
    private propertyDefinitionService: PropertyDefinitionService,
    private classDefinitionService: ClassDefinitionService
  ) {
  }

  datasource = new MatTableDataSource<PropertyItem>();
  displayedColumns = ['checkbox', 'label', 'type'];
  loaded: boolean;
  selection = new SelectionModel<PropertyItem>(true, []);
  initialProperties: PropertyDefinition<any>[];


  @ViewChild(MatSort, { static: true }) sort: MatSort;


  ngOnInit() {
    this.propertyDefinitionService.getAllPropertyDefinitons(this.data.marketplace).toPromise().then((ret: PropertyDefinition<any>[]) => {
      this.datasource.data = ret;
      this.initialProperties = ret.filter(p => this.data.classDefinition.properties.find(q => q.id === p.id));
      this.selection.select(...this.initialProperties);
      this.loaded = true;
    });

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
    return !isNullOrUndefined(this.initialProperties.find(p => p.id === propertyDefinition.id));
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
    const addedProperties = this.selection.selected.filter(p => this.data.classDefinition.properties.findIndex(q => p.id === q.id) === -1);
    this.classDefinitionService.getClassPropertyFromPropertyDefinitionById(this.data.marketplace, addedProperties.map(p => p.id)).toPromise().then((ret: ClassProperty<any>[]) => {
      this.data.classDefinition.properties.push(...ret);
      this.dialogRef.close(this.data);
    });
  }

  createNewPropertyClicked() {
    console.log('implement');
  }
}
