import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { FlatPropertyDefinition } from 'app/main/content/_model/meta/property/property';
import { isNullOrUndefined } from 'util';
import { TreePropertyDefinition } from 'app/main/content/_model/meta/property/tree-property';

export interface PropertyCreationDialogData {
  // allFlatPropertyDefinitions: FlatPropertyDefinition<any>[];

  flatPropertyDefinition: FlatPropertyDefinition<any>;
  treePropertyDefinition: TreePropertyDefinition;
  builderType: 'flat' | 'tree';
}

@Component({
  selector: "property-creation-dialog",
  templateUrl: './property-creation-dialog.component.html',
  styleUrls: ['./property-creation-dialog.component.scss'],
})
export class PropertyCreationDialogComponent implements OnInit {
  loaded = false;

  constructor(
    public dialogRef: MatDialogRef<PropertyCreationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PropertyCreationDialogData
  ) { }

  ngOnInit() {
    this.loaded = true;
  }

  handleResultEvent(event: any) {
    if (isNullOrUndefined(event)) {
      this.handleCloseClick();
    } else {
      if (event.builderType === 'tree') {
        this.data.treePropertyDefinition = event.value;
      } else if (event.builderType === 'flat') {
        this.data.flatPropertyDefinition = event.value;
      }
      this.dialogRef.close(this.data);
    }
  }

  handleCloseClick() {
    this.dialogRef.close();
  }
}
