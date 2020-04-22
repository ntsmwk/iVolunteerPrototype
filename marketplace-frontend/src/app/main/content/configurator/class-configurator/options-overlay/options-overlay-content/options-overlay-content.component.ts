import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { Relationship, RelationshipType } from 'app/main/content/_model/meta/Relationship';
import { DialogFactoryDirective } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { CConstants } from '../../utils-and-constants';
import { PropertyType } from 'app/main/content/_model/meta/Property';
import { DomSanitizer } from '@angular/platform-browser';
import { AddPropertyDialogData } from 'app/main/content/_components/dialogs/add-property-dialog/add-property-dialog.component';

export class ClassOptionsOverlayContentData {
    marketplace: Marketplace;

    classDefinition: ClassDefinition;
    relationship: Relationship;
}

@Component({
    selector: 'class-options-overlay-content',
    templateUrl: './options-overlay-content.component.html',
    styleUrls: ['./options-overlay-content.component.scss']
})
export class ClassOptionsOverlayContentComponent implements OnInit {

    @Input() inputData: ClassOptionsOverlayContentData;
    @Output() resultData = new EventEmitter<ClassOptionsOverlayContentData>();

    relationshipPalettes = CConstants.relationshipPalettes;
    propertyTypePalettes = CConstants.propertyTypePalettes;
    constructor(
        private dialogFactory: DialogFactoryDirective,
        private _sanitizer: DomSanitizer,

    ) { }

    ngOnInit() {
    }

    onSubmit() {

    }

    changeIconClicked() {
        this.dialogFactory.openChangeIconDialog(this.inputData.marketplace, this.inputData.classDefinition.imagePath).then((result: any) => {
            this.inputData.classDefinition.imagePath = result;
        });
    }

    getImagePathForRelationship(relationshipType: RelationshipType) {
        return this.relationshipPalettes.rows.find(r => r.id === relationshipType).imgPath;
    }

    getLabelForRelationship(relationshipType: RelationshipType) {
        return RelationshipType.getLabelFromRelationshipType(relationshipType);
    }

    getImagePathPropertyType(propertyType: PropertyType) {
        return this.propertyTypePalettes.find(p => p.id === propertyType).imgPath;
    }

    getPropertyEntryStyle(index: number) {
        if (index < this.inputData.classDefinition.properties.length - 1) {
            return this._sanitizer.bypassSecurityTrustStyle('height: 20px; border-bottom: solid 1px rgb(80, 80, 80)');
        } else {
            return this._sanitizer.bypassSecurityTrustStyle('height: 20px; border-bottom: none');
        }
    }

    addPropertyClicked() {
        this.dialogFactory.openAddPropertyDialog(this.inputData.marketplace, this.inputData.classDefinition).then((ret: AddPropertyDialogData) => {
            console.log(ret);
            this.inputData.classDefinition.properties = ret.classDefinition.properties;
        });
    }

    addEnumClicked() {

    }

    removeClicked() {

    }
}
