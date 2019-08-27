import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { Property } from '../../../_model/meta/Property';

export interface AddOrRemoveDialogData {
  checkboxStates: {property: Property<any>, disabled: boolean, checked: boolean, dirty: boolean}[];
  label: string;
}



@Component({
  selector: 'add-or-remove-dialog',
  templateUrl: './add-or-remove-dialog.component.html',
  styleUrls:['./add-or-remove-dialog.component.scss']
})
export class AddOrRemoveDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<AddOrRemoveDialogComponent>, @Inject(MAT_DIALOG_DATA)
    public data: AddOrRemoveDialogData,
    ) {
  }

  displayedColumns: string[] = ['label', 'options'];
  
  onNoClick(): void {
    this.dialogRef.close();
  } 
}
