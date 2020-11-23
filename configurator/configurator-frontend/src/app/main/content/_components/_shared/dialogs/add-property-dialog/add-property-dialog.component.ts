import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import {
  MatDialogRef, MAT_DIALOG_DATA, MatDialog,
} from '@angular/material/dialog';
import {
  PropertyItem, ClassProperty, FlatPropertyDefinition,
} from '../../../../_model/configurator/property/property';
import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { FlatPropertyDefinitionService } from 'app/main/content/_service/meta/core/property/flat-property-definition.service';
import { SelectionModel } from '@angular/cdk/collections';
import { isNullOrUndefined } from 'util';
import {
  Relationship, RelationshipType,
} from 'app/main/content/_model/configurator/relationship';
import { TreePropertyDefinitionService } from 'app/main/content/_service/meta/core/property/tree-property-definition.service';
import { TreePropertyDefinition } from 'app/main/content/_model/configurator/property/tree-property';
import { ClassPropertyService } from 'app/main/content/_service/meta/core/property/class-property.service';
import {
  PropertyCreationDialogComponent, PropertyCreationDialogData,
} from '../../../configuration/class-configurator/_dialogs/property-creation-dialog/property-creation-dialog.component';

export interface AddPropertyDialogData {
  classDefinition: ClassDefinition;
  tenantId: string;
  allClassDefinitions: ClassDefinition[];
  allRelationships: Relationship[];
}

@Component({
  selector: "add-property-dialog",
  templateUrl: './add-property-dialog.component.html',
  styleUrls: ['./add-property-dialog.component.scss'],
})
export class AddPropertyDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<AddPropertyDialogData>,
    @Inject(MAT_DIALOG_DATA)
    public data: AddPropertyDialogData,
    private flatPropertyDefinitionService: FlatPropertyDefinitionService,
    private classPropertyService: ClassPropertyService,
    private treePropertyDefinitionService: TreePropertyDefinitionService,
    public dialog: MatDialog,
  ) { }

  flatPropertyDataSource = new MatTableDataSource<PropertyItem>();
  treePropertyDataSource = new MatTableDataSource<TreePropertyDefinition>();
  displayedColumns = ['checkbox', 'label', 'type'];

  allFlatPropertyDefinitions: FlatPropertyDefinition<any>[];
  allTreePropertyDefinitions: TreePropertyDefinition[];

  flatPropertySelection = new SelectionModel<PropertyItem>(true, []);
  treePropertySelection = new SelectionModel<TreePropertyDefinition>(true, []);

  initialFlatPropertyDefinitions: FlatPropertyDefinition<any>[];
  disabledFlatPropertyDefinitions: FlatPropertyDefinition<any>[];

  initialTreePropertyDefinitions: TreePropertyDefinition[];
  disabledTreePropertyDefinitions: TreePropertyDefinition[];

  loaded: boolean;
  tabIndex: number;


  @ViewChild(MatSort, { static: true }) sort: MatSort;

  async ngOnInit() {
    this.tabIndex = 0;

    Promise.all([
      this.flatPropertyDefinitionService.getAllPropertyDefinitonsForTenant(this.data.tenantId)
        .toPromise()
        .then((ret: FlatPropertyDefinition<any>[]) => {
          this.flatPropertyDataSource.data = ret;
          this.allFlatPropertyDefinitions = ret;

          this.initialFlatPropertyDefinitions = ret.filter((p) =>
            this.data.classDefinition.properties.find((q) => q.id === p.id)
          );

          const parentClassProperties = this.findParentProperties();
          const parentProperties = ret.filter((p) =>
            parentClassProperties.find((q) => q.id === p.id)
          );

          this.disabledFlatPropertyDefinitions = [];
          this.disabledFlatPropertyDefinitions.push(...this.initialFlatPropertyDefinitions, ...parentProperties);

          this.flatPropertySelection.select(...this.initialFlatPropertyDefinitions);
        }),
      this.treePropertyDefinitionService
        .getAllPropertyDefinitionsForTenant(this.data.tenantId)
        .toPromise()
        .then((ret: TreePropertyDefinition[]) => {
          this.treePropertyDataSource.data = ret;
          this.allTreePropertyDefinitions = ret;

          this.initialTreePropertyDefinitions = ret.filter((e) =>
            this.data.classDefinition.properties.find((f) => f.id === e.id)
          );
          this.disabledTreePropertyDefinitions = [];
          this.disabledTreePropertyDefinitions.push(...this.initialTreePropertyDefinitions);
          this.treePropertySelection
            .select(...this.initialTreePropertyDefinitions);
        }),
    ]).then(() => {
      this.loaded = true;
    });
  }

  findParentProperties() {
    let currentClassDefinition = this.data.classDefinition;
    const parentProperties = [];
    do {
      const relationship = this.data.allRelationships.find(
        (r) => r.target === currentClassDefinition.id
      );
      if (isNullOrUndefined(relationship) || relationship.relationshipType === RelationshipType.ASSOCIATION) {
        break;
      }

      currentClassDefinition = this.data.allClassDefinitions.find(
        (cd) => cd.id === relationship.source
      );
      parentProperties.push(...currentClassDefinition.properties);
    } while (!currentClassDefinition.root);

    return parentProperties;
  }

  isFlatPropertyRowDisabled(flatPropertyDefinition: FlatPropertyDefinition<any>) {
    return !isNullOrUndefined(
      this.disabledFlatPropertyDefinitions.find((p) => p.id === flatPropertyDefinition.id)
    );
  }

  isTreePropertyRowDisabled(treePropertyDefinition: TreePropertyDefinition) {
    return !isNullOrUndefined(
      this.disabledTreePropertyDefinitions.find((p) => p.id === treePropertyDefinition.id)
    );
  }

  applyFlatPropertyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.flatPropertyDataSource.filter = filterValue.trim().toLowerCase();
  }

  applyTreePropertyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.treePropertyDataSource.filter = filterValue.trim().toLowerCase();
  }

  onFlatRowClick(row: FlatPropertyDefinition<any>) {
    if (this.isFlatPropertyRowDisabled(row)) {
      return;
    }

    this.flatPropertySelection.isSelected(row) ? this.flatPropertySelection.deselect(row) : this.flatPropertySelection.select(row);
  }

  onTreeRowClick(row: TreePropertyDefinition) {
    if (this.isTreePropertyRowDisabled(row)) {
      return;
    }

    this.treePropertySelection.isSelected(row) ? this.treePropertySelection.deselect(row)
      : this.treePropertySelection.select(row);
  }

  onSubmit() {
    // Add selected, but filter out existing ones
    const addedFlatProperties = this.flatPropertySelection.selected.filter(
      (p) =>
        this.data.classDefinition.properties.findIndex((q) => p.id === q.id) ===
        -1
    );
    const addedTreeProperties = this.treePropertySelection
      .selected.filter(
        (e) =>
          this.data.classDefinition.properties.findIndex((q) => e.id === q.id) ===
          -1
      );

    this.classPropertyService
      .getClassPropertyFromDefinitionById(addedFlatProperties.map((p) => p.id), addedTreeProperties.map((e) => e.id))
      .toPromise()
      .then((ret: ClassProperty<any>[]) => {
        this.data.classDefinition.properties.push(...ret);
        this.dialogRef.close(this.data);
      });
  }

  createNewClicked(type: string) {
    const dialogRef = this.dialog.open(PropertyCreationDialogComponent, {
      width: '70vw',
      minWidth: '70vw',
      height: '90vh',
      minHeight: '90vh',
      data: {
        allFlatPropertyDefinitions: this.flatPropertyDataSource.data,
        builderType: type,
        tenantId: this.data.tenantId,
      },
      disableClose: true,
    });

    dialogRef.beforeClose().toPromise()
      .then((result: PropertyCreationDialogData) => {
        if (!isNullOrUndefined(result)) {
          if (!isNullOrUndefined(result.flatPropertyDefinition)) {
            this.allFlatPropertyDefinitions.push(result.flatPropertyDefinition);
            this.flatPropertyDataSource.data = this.allFlatPropertyDefinitions;
          } else if (!isNullOrUndefined(result.treePropertyDefinition)) {
            this.allTreePropertyDefinitions.push(result.treePropertyDefinition);
            this.treePropertyDataSource.data = this.allTreePropertyDefinitions;
          }
        }
      });
  }
}
