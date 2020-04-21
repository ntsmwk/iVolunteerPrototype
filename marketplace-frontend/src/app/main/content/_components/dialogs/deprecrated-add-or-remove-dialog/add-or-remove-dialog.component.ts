import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { PropertyItem, PropertyDefinition } from '../../../_model/meta/Property';
import { Router } from '@angular/router';

export interface AddOrRemoveDialogData {
  checkboxStates: {propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean}[];
  label: string;
  key: string;
}

@Component({
  selector: 'add-or-remove-dialog',
  templateUrl: './add-or-remove-dialog.component.html',
  styleUrls: ['./add-or-remove-dialog.component.scss']
})
export class AddOrRemoveDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<AddOrRemoveDialogComponent>, @Inject(MAT_DIALOG_DATA)
    public data: AddOrRemoveDialogData,
    private router: Router,
  ) {
  }

  displayedColumns: string[] = ['label', 'options'];

  createNewClicked() {
    this.dialogRef.close();
    this.router.navigate(['/main/property/detail/edit/0eaf3a6281df11e8adc0fa7ae01bbebc']);

  }

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
