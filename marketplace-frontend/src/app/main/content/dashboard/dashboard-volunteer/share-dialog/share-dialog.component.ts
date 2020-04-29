import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogData } from '../dashboard-volunteer.component';

@Component({
  selector: 'share-dialog',
  templateUrl: 'share-dialog.component.html',
  styleUrls: ['../dashboard-volunteer.component.scss']
})
export class ShareDialog {

  shareDone: boolean;
  duringShare: boolean;
  percentageValue: number;

  constructor(
    public dialogRef: MatDialogRef<ShareDialog>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData) {
    this.shareDone = false;
    this.duringShare = false;
    this.percentageValue = 0;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  triggeredShare() {
    this.duringShare = true;
    setTimeout(() => {
      this.duringShare = false;
      this.shareDone = true;

    }, 2000);
  }

  // onClickedUpload(): void {
  //   this.duringUpload = true;


  //   this.advanceTime();



  //   console.log("clicked upload");

  // }

  // advanceTime(): void {
  //   setTimeout( () => {
  //     if (this.percentageValue == 100) {
  //       this.duringUpload = false;
  //       this.uploadFinished = true;
  //       this.dialogRef.close(true);
  //     } else {
  //       //bla
  //       this.percentageValue+=1;
  //       this.advanceTime();
  //     }

  //   }, 20)
  // }



}
