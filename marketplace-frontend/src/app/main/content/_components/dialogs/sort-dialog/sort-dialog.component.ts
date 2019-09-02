import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";

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
    console.log(this.dialogRef);
    console.log(this.data);

  }
  
  onNoClick(): void {
    this.dialogRef.close();
  }

  printProperties() {
    console.log(this.data);
  }

  



  
}


