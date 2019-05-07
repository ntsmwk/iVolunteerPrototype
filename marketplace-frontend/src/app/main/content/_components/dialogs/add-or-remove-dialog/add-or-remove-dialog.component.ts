import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from "@angular/material";
import { QuestionBase } from '../../../_model/dynamic-forms/questions';
import { Property, PropertyListItem } from '../../../_model/properties/Property';
import { UserDefinedTaskTemplate } from 'app/main/content/_model/user-defined-task-template';
import { isNullOrUndefined } from 'util';


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
