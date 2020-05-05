import { Component, OnInit, Inject } from '@angular/core';
import { Marketplace } from '../../../../../_model/marketplace';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { PropertyDefinition } from 'app/main/content/_model/meta/property';
import { isNullOrUndefined } from 'util';

export interface PropertyCreationDialogData {
  marketplace: Marketplace;
  helpseeker: Helpseeker;
  allPropertyDefinitions: PropertyDefinition<any>[];

  propertyDefinition: PropertyDefinition<any>;
}

@Component({
  selector: 'property-creation-dialog',
  templateUrl: './property-creation-dialog.component.html',
  styleUrls: ['./property-creation-dialog.component.scss'],
})
export class PropertyCreationDialogComponent implements OnInit {

  loaded = false;


  constructor(
    public dialogRef: MatDialogRef<PropertyCreationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PropertyCreationDialogData,
  ) {
  }

  ngOnInit() {
    this.loaded = true;
  }

  handleResultEvent(event: PropertyDefinition<any>) {
    if (isNullOrUndefined(event)) {
      this.handleCloseClick();
    } else {
      this.data.propertyDefinition = event;
      this.dialogRef.close(this.data);
    }
  }

  handleCloseClick() {
    this.dialogRef.close();
  }



}