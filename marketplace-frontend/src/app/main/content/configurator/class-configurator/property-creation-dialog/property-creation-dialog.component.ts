import { Component, OnInit, Inject } from '@angular/core';
import { Marketplace } from '../../../_model/marketplace';
import { FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

export interface PropertyCreationDialogData {
  marketplace: Marketplace;

}

@Component({
  selector: 'property-creation-dialog',
  templateUrl: './property-creation-dialog.component.html',
  styleUrls: ['./property-creation-dialog.component.scss'],
})
export class PropertyCreationDialogComponent implements OnInit {

  isLoaded = false;


  constructor(
    public dialogRef: MatDialogRef<PropertyCreationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PropertyCreationDialogData,
  ) {
  }

  ngOnInit() {

  }

  handleResultEvent(event: FormEntryReturnEventData) {

  }

  handleCloseClick() {
    this.dialogRef.close();
  }

  printAnything(anything: any) {
    console.log(anything);
  }


}