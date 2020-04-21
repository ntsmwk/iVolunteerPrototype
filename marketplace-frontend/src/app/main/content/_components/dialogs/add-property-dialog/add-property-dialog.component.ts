import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PropertyItem, ClassProperty, PropertyInstance, PropertyDefinition } from '../../../_model/meta/Property';
import { Router } from '@angular/router';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { MatTableDataSource } from '@angular/material';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { SelectionModel } from '@angular/cdk/collections';

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
  ) {
  }

  datasource = new MatTableDataSource<PropertyDefinition<any>>();
  displayedColumns = ['checkbox', 'label', 'type'];
  loaded: boolean;
  selection = new SelectionModel<PropertyDefinition<any>>(true, []);



  ngOnInit() {
    this.propertyDefinitionService.getAllPropertyDefinitons(this.data.marketplace).toPromise().then((ret: PropertyDefinition<any>[]) => {
      this.datasource.data = ret;
      this.loaded = true;
    });

  }

  onSubmit() {

  }

  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.datasource.data.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.datasource.data.forEach(row => this.selection.select(row));
  }


}
