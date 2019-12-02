import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { PropertyItem, PropertyDefinition } from '../../../_model/meta/Property';

export interface AddOrRemoveDialogData {
  checkboxStates: {propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean}[];
  label: string;
  key: string;
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
    this.data.key = undefined;
    this.dialogRef.close();
  } 

  applyClicked() {
    this.data.key = undefined;
    this.dialogRef.close(this.data);
  }

  createNewPropertyClicked() {
    console.log("create new property clicked");
    this.data.key = "new_property"
    this.dialogRef.close(this.data);
  }

}
