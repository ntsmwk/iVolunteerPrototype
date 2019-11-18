import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material";
import { Relationship, Inheritance, Association, RelationshipType, AssociationCardinality } from "../../../_model/meta/Relationship";

@Component({
  selector: 'relationship-dialog',
  templateUrl: './relationship-dialog.component.html',
  styleUrls:['./relationship-dialog.component.scss']
})
export class RelationshipDialogComponent implements OnInit{
  relationshipTypeSelector: string;
  relationship: Relationship;

  associationParameterOptions: {key: string, label: string}[];


  
  constructor(
    public dialogRef: MatDialogRef<RelationshipDialogComponent>, @Inject(MAT_DIALOG_DATA)
    public data: {classIdTarget: string},

    ) {
  }

  ngOnInit() {

    this.associationParameterOptions = [];
    for (let a in AssociationCardinality) {
      this.associationParameterOptions.push({key: a, label: AssociationCardinality[a]});
    }    
  }
  
  onNoClick(): void {
    this.dialogRef.close();
  }

  onRelationshipTypeSelect(event: any) {
    console.log("selection: ");
    console.log(event);

    this.relationshipTypeSelector = event.value;
    
    if (event.value === 'inheritance') {
      this.relationship = new Inheritance()
      this.relationship.relationshipType = RelationshipType.INHERITANCE;

    } else if (event.value === 'association') {
      this.relationship = new Association();
      this.relationship.relationshipType = RelationshipType.ASSOCIATION;
    }

    this.relationship.id = null;
    this.relationship.target = null;
    this.relationship.source = this.data.classIdTarget;

  
    
  }

  onSuperClassIdSelect(event: any) {
    (this.relationship as Inheritance).superClassId = event.value;
    console.log(this.relationship);
  }

  onParam1Select(event: any) {
    (this.relationship as Association).sourceCardinality = event.value;
    console.log(this.relationship);
  }

  onParam2Select(event: any) {
    (this.relationship as Association).targetCardinality = event.value;
    console.log(this.relationship);
  }

  getDataFromInput() {
  
  }


  
}


