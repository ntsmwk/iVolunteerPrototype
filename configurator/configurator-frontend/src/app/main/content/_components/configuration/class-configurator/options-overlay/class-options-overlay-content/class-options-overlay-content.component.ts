import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { RelationshipType } from 'app/main/content/_model/configurator/relationship';
import { CConstants } from '../../utils-and-constants';
import { PropertyType } from 'app/main/content/_model/configurator/property/property';
import { DomSanitizer } from '@angular/platform-browser';
import { AddPropertyDialogData } from 'app/main/content/_components/_shared/dialogs/add-property-dialog/add-property-dialog.component';
import { isNullOrUndefined } from 'util';
import { RemovePropertyDialogData } from 'app/main/content/_components/_shared/dialogs/remove-dialog/remove-dialog.component';
import { DialogFactoryDirective } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.component';
import { OptionsOverlayContentData } from '../options-overlay-control/options-overlay-control.component';

export interface PropertyEntry {
  name: string;
  type: PropertyType;
}

@Component({
  selector: "class-options-overlay-content",
  templateUrl: './class-options-overlay-content.component.html',
  styleUrls: ['./class-options-overlay-content.component.scss'],
})
export class ClassOptionsOverlayContentComponent implements OnInit {
  @Input() inputData: OptionsOverlayContentData;
  @Output() resultData = new EventEmitter<OptionsOverlayContentData>();

  relationshipPalettes = CConstants.relationshipPalettes;
  propertyTypePalettes = CConstants.propertyTypePalettes;

  entryList: PropertyEntry[];

  constructor(
    private dialogFactory: DialogFactoryDirective,
    private _sanitizer: DomSanitizer
  ) { }

  ngOnInit() {
    this.updatePropertiesList();
  }

  onSubmit() {
    this.resultData.emit(this.inputData);
  }

  onCancel() {
    this.resultData.emit(undefined);
  }

  changeIconClicked() {
    this.dialogFactory
      .openChangeIconDialog(
        this.inputData.classDefinition.imagePath
      )
      .then((result: any) => {
        this.inputData.classDefinition.imagePath = result;
      });
  }

  getImagePathForRelationship(relationshipType: RelationshipType) {
    return this.relationshipPalettes.rows.find((r) => r.id === relationshipType)
      .imgPath;
  }

  getLabelForRelationship(relationshipType: RelationshipType) {
    return RelationshipType.getLabelFromRelationshipType(relationshipType);
  }

  getImagePathPropertyType(propertyType: PropertyType) {
    return this.propertyTypePalettes.find((p) => p.id === propertyType).imgPath;
  }

  getEntryStyle(index: number) {
    if (index < this.entryList.length - 1) {
      return this._sanitizer.bypassSecurityTrustStyle(
        'height: 20px; border-bottom: solid 1px rgb(80, 80, 80)'
      );
    } else {
      return this._sanitizer.bypassSecurityTrustStyle(
        'height: 20px; border-bottom: none'
      );
    }
  }

  addPropertyClicked() {
    this.dialogFactory
      .openAddPropertyDialog(
        this.inputData.classDefinition,
        this.inputData.allClassDefinitions,
        this.inputData.allRelationships,
        this.inputData.tenantId
      )
      .then((ret: AddPropertyDialogData) => {
        if (!isNullOrUndefined(ret)) {
          this.inputData.classDefinition.properties =
            ret.classDefinition.properties;
          this.updatePropertiesList();
        }
      });
  }

  removeClicked() {
    this.dialogFactory
      .openRemovePropertyDialog(
        this.inputData.classDefinition
      )
      .then((ret: RemovePropertyDialogData) => {
        if (!isNullOrUndefined(ret)) {
          this.inputData.classDefinition.properties =
            ret.classDefinition.properties;
          this.updatePropertiesList();
        }
      });
  }

  previewClicked() {
    this.dialogFactory
      .openInstanceFormPreviewDialog(
        this.inputData.allClassDefinitions,
        this.inputData.allRelationships,
        this.inputData.classDefinition
      )
      .then(() => { });
  }

  updatePropertiesList() {
    this.entryList = [];
    this.entryList.push(...this.inputData.classDefinition.properties);
  }
}
