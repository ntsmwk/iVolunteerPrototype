import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { QuestionBase } from '../../../_model/dynamic-forms/questions';
import { Property, PropertyListItem } from '../../../_model/properties/Property';


export interface TextFieldDialogData {
  label: string;
  fields: [{description: string, hintText: string, value: string}];
}



@Component({
  selector: 'text-field-dialog',
  templateUrl: './text-field-dialog.component.html',
  styleUrls:['./text-field-dialog.component.scss']
})
export class TextFieldDialogComponent implements OnInit{
  newValues: string[]=[];
  
  constructor(
    public dialogRef: MatDialogRef<TextFieldDialogComponent>, @Inject(MAT_DIALOG_DATA)
    public data: TextFieldDialogData,

    ) {
  }

  ngOnInit() {
    for (let i = 0; i < this.data.fields.length; i++) {
      this.newValues[i] = this.data.fields[i].value;
    }
    
  }
  
  onNoClick(): void {
    this.dialogRef.close();
  }

  getDataFromInput() {
    for (let i = 0; i < this.data.fields.length; i++) {
      this.data.fields[i].value = this.newValues[i];
    }
  }


  
}


