
import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { RelationshipType, AssociationCardinality } from 'app/main/content/_model/configurator/relationship';
import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { PropertyEntry } from '../class-options-overlay-content/class-options-overlay-content.component';
import { CConstants } from '../../utils-and-constants';
import { OptionsOverlayContentData } from '../options-overlay-control/options-overlay-control.component';



@Component({
  selector: "relationship-options-overlay-content",
  templateUrl: "./relationship-options-overlay-content.component.html",
  styleUrls: ["./relationship-options-overlay-content.component.scss"],
})
export class RelationshipOptionsOverlayContentComponent implements OnInit {
  @Input() inputData: OptionsOverlayContentData;
  @Output() resultData = new EventEmitter<OptionsOverlayContentData>();

  relationshipPalettes = CConstants.relationshipPalettes;

  entryList: PropertyEntry[];
  sourceClass: ClassDefinition;
  targetClass: ClassDefinition;

  constructor(
  ) { }

  ngOnInit() {
    console.log(this.inputData);
    this.initSourceAndTargetClasses();
  }

  private initSourceAndTargetClasses() {
    this.sourceClass = this.inputData.allClassDefinitions.find(c => c.id === this.inputData.relationship.source);
    this.targetClass = this.inputData.allClassDefinitions.find(c => c.id === this.inputData.relationship.target);
  }

  onSubmit() {
    this.resultData.emit(this.inputData);
  }

  onCancel() {
    this.resultData.emit(undefined);
  }

  getImagePathForRelationship(relationshipType: RelationshipType) {
    return this.relationshipPalettes.rows.find((r) => r.id === relationshipType)
      .imgPath;
  }

  getLabelForRelationship(relationshipType: RelationshipType) {
    return RelationshipType.getLabelFromRelationshipType(relationshipType);
  }

  onRelationshipTypeSelect(relationshipType: RelationshipType) {
    this.inputData.relationship.relationshipType = relationshipType;

    if (relationshipType === RelationshipType.ASSOCIATION) {
      this.onChangeCardinalities('1:1');
    }
  }

  getCardinalityLabel(key: string) {
    if (key === 'source') {
      return AssociationCardinality.getLabelForAssociationCardinality(this.inputData.relationship.sourceCardinality);
    } else if (key === 'target') {
      return AssociationCardinality.getLabelForAssociationCardinality(this.inputData.relationship.targetCardinality);
    }
    return '';
  }

  onChangeCardinalities(key: string) {
    if (key === '1:1') {
      this.inputData.relationship.sourceCardinality = AssociationCardinality.ONE;
      this.inputData.relationship.targetCardinality = AssociationCardinality.ONE;
    } else if (key === '1:N') {
      this.inputData.relationship.sourceCardinality = AssociationCardinality.ONE;
      this.inputData.relationship.targetCardinality = AssociationCardinality.N;
    } else {
      this.inputData.relationship.sourceCardinality = null;
      this.inputData.relationship.targetCardinality = null;
    }
  }

}
