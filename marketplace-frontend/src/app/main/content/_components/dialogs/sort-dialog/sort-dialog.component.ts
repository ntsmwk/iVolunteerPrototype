import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatTableDataSource } from "@angular/material";
import { QuestionBase } from '../../../_model/dynamic-forms/questions';
import { Property, PropertyListItem } from '../../../_model/properties/Property';


export interface SortDialogData {
  list: any[];
  label: string;
}



@Component({
  selector: 'sort-dialog',
  templateUrl: './sort-dialog.component.html',
  styleUrls:['./sort-dialog.component.scss']
})
export class SortDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<SortDialogComponent>, @Inject(MAT_DIALOG_DATA)
    public data: SortDialogData,
    ) {


  }

//  displayedColumns: string[] = ['label', 'options'];

  ngOnInit() {

  }
  
  onNoClick(): void {
    this.dialogRef.close();
  }


  



  
}

