import { Component, OnInit, Inject } from '@angular/core';
import { Marketplace } from '../../../../../_model/marketplace';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { PropertyDefinition } from 'app/main/content/_model/meta/property';
import { isNullOrUndefined } from 'util';
import { EnumDefinition } from 'app/main/content/_model/meta/enum';

export interface PropertyOrEnumCreationDialogData {
  marketplace: Marketplace;
  helpseeker: Helpseeker;
  allPropertyDefinitions: PropertyDefinition<any>[];

  propertyDefinition: PropertyDefinition<any>;
  enumDefinition: EnumDefinition;
}

@Component({
  selector: 'property-enum-creation-dialog',
  templateUrl: './property-enum-creation-dialog.component.html',
  styleUrls: ['./property-enum-creation-dialog.component.scss'],
})
export class PropertyOrEnumCreationDialogComponent implements OnInit {

  loaded = false;


  constructor(
    public dialogRef: MatDialogRef<PropertyOrEnumCreationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PropertyOrEnumCreationDialogData,
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