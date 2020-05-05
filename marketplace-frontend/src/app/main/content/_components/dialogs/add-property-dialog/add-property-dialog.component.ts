import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { PropertyItem, ClassProperty, PropertyDefinition } from '../../../_model/meta/property';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { SelectionModel } from '@angular/cdk/collections';
import { isNullOrUndefined } from 'util';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import {
  PropertyCreationDialogComponent,
  PropertyCreationDialogData
} from 'app/main/content/_components/help-seeker/configuration/class-configurator/property-creation-dialog/property-creation-dialog.component';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { Helpseeker } from 'app/main/content/_model/helpseeker';

export interface AddPropertyDialogData {
  marketplace: Marketplace;
  helpseeker: Helpseeker;

  classDefinition: ClassDefinition;

  allClassDefinitions: ClassDefinition[];
  allRelationships: Relationship[];
}

@Component({
  selector: 'add-property-dialog',
  templateUrl: './add-property-dialog.component.html',
  styleUrls: ['./add-property-dialog.component.scss']
})
export class AddPropertyDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<AddPropertyDialogData>, @Inject(MAT_DIALOG_DATA)
    public data: AddPropertyDialogData,
    private propertyDefinitionService: PropertyDefinitionService,
    private classDefinitionService: ClassDefinitionService,
    public dialog: MatDialog,
  ) { }

  datasource = new MatTableDataSource<PropertyItem>();
  displayedColumns = ['checkbox', 'label', 'type'];

  allPropertyDefinitions: PropertyDefinition<any>[];

  loaded: boolean;
  selection = new SelectionModel<PropertyItem>(true, []);
  initialProperties: PropertyDefinition<any>[];
  disabledProperties: PropertyDefinition<any>[];

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  ngOnInit() {
    this.propertyDefinitionService.getAllPropertyDefinitons(this.data.marketplace, this.data.classDefinition.tenantId).toPromise().then((ret: PropertyDefinition<any>[]) => {
      this.datasource.data = ret;
      this.allPropertyDefinitions = ret;

      this.initialProperties = ret.filter(p => this.data.classDefinition.properties.find(q => q.id === p.id));

      const parentClassProperties = this.findParentProperties();
      const parentProperties = ret.filter(p => parentClassProperties.find(q => q.id === p.id));

      this.disabledProperties = [];
      this.disabledProperties.push(...this.initialProperties, ...parentProperties);

      this.selection.select(...this.initialProperties);
      this.loaded = true;
    });

  }

  findParentProperties() {
    let currentClassDefinition = this.data.classDefinition;
    const parentProperties = [];
    do {
      const relationship = this.data.allRelationships.find(r => r.target === currentClassDefinition.id);
      currentClassDefinition = this.data.allClassDefinitions.find(cd => cd.id === relationship.source);
      parentProperties.push(...currentClassDefinition.properties);
    } while (!currentClassDefinition.root);

    return parentProperties;
  }

  isDisabled(propertyDefinition: PropertyDefinition<any>) {
    return !isNullOrUndefined(this.disabledProperties.find(p => p.id === propertyDefinition.id));
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.datasource.filter = filterValue.trim().toLowerCase();
  }

  onRowClick(row: PropertyDefinition<any>) {
    if (this.isDisabled(row)) {
      return;
    }

    if (this.selection.isSelected(row)) {
      this.selection.deselect(row);
    } else {
      this.selection.select(row);
    }
  }

  onSubmit() {
    const addedProperties = this.selection.selected.filter(p => this.data.classDefinition.properties.findIndex(q => p.id === q.id) === -1);
    this.classDefinitionService
      .getClassPropertyFromPropertyDefinitionById(this.data.marketplace, addedProperties.map(p => p.id), null)
      .toPromise()
      .then((ret: ClassProperty<any>[]) => {
        this.data.classDefinition.properties.push(...ret);
        this.dialogRef.close(this.data);
      });
  }

  createNewPropertyClicked() {
    const dialogRef = this.dialog.open(PropertyCreationDialogComponent, {
      width: '50vw',
      minWidth: '50vw',
      height: '90vh',
      minHeight: '90vh',
      data: { marketplace: this.data.marketplace, helpseeker: this.data.helpseeker, allPropertyDefinitions: this.datasource.data },
      disableClose: true
    });

    dialogRef.beforeClose().toPromise().then((result: PropertyCreationDialogData) => {
      if (!isNullOrUndefined(result)) {
        this.allPropertyDefinitions.push(result.propertyDefinition);
        this.datasource.data = this.allPropertyDefinitions;
      }
    });


  }
}
