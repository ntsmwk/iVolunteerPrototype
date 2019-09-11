import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";



export interface OpenDialogData {
  ret: string;
}


@Component({
  selector: 'open-dialog',
  templateUrl: './open-dialog.component.html',
  styleUrls:['./open-dialog.component.scss']
})
export class OpenDialogComponent {
  
  constructor(
    public dialogRef: MatDialogRef<OpenDialogComponent>, 
    @Inject(MAT_DIALOG_DATA) public data: OpenDialogData
    ) {
  }

  selected: string;

  
  itemSelected(event: any, s: string) {
    console.log("aa");
    console.log(event);
    this.selected = s;
    this.data.ret = s;
    this.dialogRef.close(this.data)

  }

  onNoClick(): void {
    this.dialogRef.close();
  }






  
}


