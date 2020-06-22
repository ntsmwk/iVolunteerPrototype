import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { PropertyItem, ClassProperty, PropertyDefinition } from '../../../../_model/meta/property';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { SelectionModel } from '@angular/cdk/collections';
import { isNullOrUndefined } from 'util';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import {
  PropertyOrEnumCreationDialogComponent,
  PropertyOrEnumCreationDialogData
} from 'app/main/content/_components/help-seeker/configuration/class-configurator/property-enum-creation-dialog/property-enum-creation-dialog.component';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { EnumDefinitionService } from 'app/main/content/_service/meta/core/enum/enum-configuration.service';
import { EnumDefinition } from 'app/main/content/_model/meta/enum';
import { ClassPropertyService } from 'app/main/content/_service/meta/core/property/class-property.service';

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
    private classPropertyService: ClassPropertyService,
    private enumDefinitionService: EnumDefinitionService,
    public dialog: MatDialog,
  ) { }

  propertyDatasource = new MatTableDataSource<PropertyItem>();
  enumDataSource = new MatTableDataSource<EnumDefinition>();
  displayedColumns = ['checkbox', 'label', 'type'];

  allPropertyDefinitions: PropertyDefinition<any>[];
  allEnumDefinitions: EnumDefinition[];

  propertySelection = new SelectionModel<PropertyItem>(true, []);
  enumSelection = new SelectionModel<EnumDefinition>(true, []);

  initialProperties: PropertyDefinition<any>[];
  disabledProperties: PropertyDefinition<any>[];

  initialEnums: EnumDefinition[];
  disabledEnums: EnumDefinition[];

  loaded: boolean;

  tabIndex: number;


  @ViewChild(MatSort, { static: true }) sort: MatSort;

  ngOnInit() {

    this.tabIndex = 0;

    Promise.all([
      this.propertyDefinitionService.getAllPropertyDefinitons(this.data.marketplace, this.data.classDefinition.tenantId).toPromise().then((ret: PropertyDefinition<any>[]) => {
        this.propertyDatasource.data = ret;
        this.allPropertyDefinitions = ret;

        this.initialProperties = ret.filter(p => this.data.classDefinition.properties.find(q => q.id === p.id));

        const parentClassProperties = this.findParentProperties();
        const parentProperties = ret.filter(p => parentClassProperties.find(q => q.id === p.id));

        this.disabledProperties = [];
        this.disabledProperties.push(...this.initialProperties, ...parentProperties);

        this.propertySelection.select(...this.initialProperties);
      }),
      this.enumDefinitionService.getAllEnumDefinitionsForTenant(this.data.marketplace, this.data.classDefinition.tenantId).toPromise().then((ret: EnumDefinition[]) => {
        this.enumDataSource.data = ret;
        this.allEnumDefinitions = ret;
      })
    ]).then(() => {
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
    this.propertyDatasource.filter = filterValue.trim().toLowerCase();
  }

  applyEnumFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.enumDataSource.filter = filterValue.trim().toLowerCase();
  }

  onRowClick(row: PropertyDefinition<any>) {
    if (this.isDisabled(row)) {
      return;
    }

    if (this.propertySelection.isSelected(row)) {
      this.propertySelection.deselect(row);
    } else {
      this.propertySelection.select(row);
    }
  }

  onEnumRowClick(row: EnumDefinition) {
    // if (this.isDisabled(row)) {
    //   return;
    // }

    if (this.enumSelection.isSelected(row)) {
      this.enumSelection.deselect(row);
    } else {
      this.enumSelection.select(row);
    }
  }

  onSubmit() {
    const addedProperties = this.propertySelection.selected.filter(p => this.data.classDefinition.properties.findIndex(q => p.id === q.id) === -1);
    const addedEnums = this.enumSelection.selected;

    this.classPropertyService
      .getClassPropertyFromDefinitionById(this.data.marketplace, addedProperties.map(p => p.id), addedEnums.map(e => e.id))
      .toPromise()
      .then((ret: ClassProperty<any>[]) => {
        this.data.classDefinition.properties.push(...ret);
        this.dialogRef.close(this.data);
      });
  }

  createNewClicked(type: string) {
    const dialogRef = this.dialog.open(PropertyOrEnumCreationDialogComponent, {
      width: '70vw',
      minWidth: '70vw',
      height: '90vh',
      minHeight: '90vh',
      data: {
        marketplace: this.data.marketplace,
        helpseeker: this.data.helpseeker,
        allPropertyDefinitions: this.propertyDatasource.data,
        builderType: type
      },
      disableClose: true
    });

    dialogRef.beforeClose().toPromise().then((result: PropertyOrEnumCreationDialogData) => {
      if (!isNullOrUndefined(result)) {
        if (!isNullOrUndefined(result.propertyDefinition)) {
          this.allPropertyDefinitions.push(result.propertyDefinition);
          this.propertyDatasource.data = this.allPropertyDefinitions;
        } else if (!isNullOrUndefined(result.enumDefinition)) {
          this.allEnumDefinitions.push(result.enumDefinition);
          this.enumDataSource.data = this.allEnumDefinitions;
        }
      }
    });


  }
}
