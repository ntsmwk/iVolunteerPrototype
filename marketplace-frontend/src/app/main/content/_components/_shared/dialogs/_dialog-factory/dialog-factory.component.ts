import { MatDialog } from '@angular/material';
import { Directive } from '@angular/core';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import {
  NewClassConfigurationDialogComponent,
  NewClassConfigurationDialogData,
} from '../../../help-seeker/configuration/class-configurator/_dialogs/new-dialog/new-dialog.component';
import {
  OpenClassConfigurationDialogComponent,
  OpenClassConfigurationDialogData,
} from '../../../help-seeker/configuration/class-configurator/_dialogs/open-dialog/open-dialog.component';
import {
  ClassConfiguration,
  MatchingConfiguration,
  MatchingEntityMappingConfiguration,
} from 'app/main/content/_model/meta/configurations';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import {
  ConfirmClassConfigurationSaveDialogComponent,
  ConfirmClassConfigurationSaveDialogData,
} from '../../../help-seeker/configuration/class-configurator/_dialogs/confirm-save-dialog/confirm-save-dialog.component';
import {
  DeleteClassConfigurationDialogComponent,
  DeleteClassConfigurationDialogData,
} from '../../../help-seeker/configuration/class-configurator/_dialogs/delete-dialog/delete-dialog.component';
import {
  ClassInstanceFormPreviewDialogComponent,
  ClassInstanceFormPreviewDialogData,
} from '../../../help-seeker/configuration/class-instance-configurator/form-preview-dialog/form-preview-dialog.component';
import {
  ClassInstanceFormPreviewExportDialogComponent,
  ClassInstanceFormPreviewExportDialogData,
} from '../../../help-seeker/configuration/class-instance-configurator/form-preview-export-dialog/form-preview-export-dialog.component';
import {
  ChangeIconDialogComponent,
  ChangeIconDialogData,
} from '../../../help-seeker/configuration/class-configurator/_dialogs/icon-dialog/icon-dialog.component';
import {
  PropertyCreationDialogComponent,
  PropertyCreationDialogData,
} from '../../../help-seeker/configuration/class-configurator/_dialogs/property-creation-dialog/property-creation-dialog.component';
import {
  NewMatchingDialogComponent,
  NewMatchingDialogData,
} from '../../../help-seeker/configuration/matching-configurator/_dialogs/new-dialog/new-dialog.component';
import {
  OpenMatchingDialogComponent,
  OpenMatchingDialogData,
} from '../../../help-seeker/configuration/matching-configurator/_dialogs/open-dialog/open-dialog.component';
import {
  DeleteMatchingDialogComponent,
  DeleteMatchingDialogData,
} from '../../../help-seeker/configuration/matching-configurator/_dialogs/delete-dialog/delete-dialog.component';
import {
  AddPropertyDialogComponent,
  AddPropertyDialogData,
} from '../add-property-dialog/add-property-dialog.component';
import {
  RemovePropertyDialogData,
  RemovePropertyDialogComponent,
} from '../remove-dialog/remove-dialog.component';
import { isNullOrUndefined } from 'util';
import { User } from 'app/main/content/_model/user';
import { AddClassDefinitionDialogComponent, AddClassDefinitionDialogData } from '../../../help-seeker/configuration/matching-configurator/_dialogs/add-class-definition-dialog/add-class-definition-dialog.component';
import { MatchingEntity } from 'app/main/content/_model/matching';
import { AddHelpseekerDialogComponent, AddHelpseekerDialogData } from '../../../admin/tenant-form/tenant-form-content/helpseekers-form/add-helpseeker-dialog/add-helpseeker-dialog.component';

@Directive({
  selector: "app-dialog-factory",
})
export class DialogFactoryDirective {
  constructor(public dialog: MatDialog) { }

  confirmationDialog(title: string, description: string) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '500px',
      data: { title: title, description: description },
    });

    let ret = false;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: boolean) => {
        if (result) {
          ret = result;
        }
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return ret;
      });
  }

  /**
   * ******CLASS CONFIGURATION******
   */

  openNewClassConfigurationDialog(currentClassConfiguration?: ClassConfiguration) {
    const dialogRef = this.dialog.open(NewClassConfigurationDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: {
        classConfiguration: currentClassConfiguration,
      },
      disableClose: true,
    });

    let returnData: NewClassConfigurationDialogData;
    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: NewClassConfigurationDialogData) => {
        returnData = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnData;
      });
  }

  openOpenClassConfigurationDialog() {
    const dialogRef = this.dialog.open(OpenClassConfigurationDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { configurator: undefined },
      disableClose: true,
    });

    let returnData: OpenClassConfigurationDialogData;
    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: OpenClassConfigurationDialogData) => {
        returnData = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnData;
      });
  }

  openSaveClassConfigurationConfirmationDialog(
    classConfiguration: ClassConfiguration,
    classDefinitions: ClassDefinition[],
    relationships: Relationship[],
    deletedClassDefintions: string[],
    deletedRelationships: string[]
  ) {
    const dialogRef = this.dialog.open(
      ConfirmClassConfigurationSaveDialogComponent,
      {
        width: '500px',
        data: {
          classConfiguration: classConfiguration,
          classDefinitions: classDefinitions,
          relationships: relationships,

          deletedClassDefintions: deletedClassDefintions,
          deletedRelationships: deletedRelationships,
        },
      }
    );

    let returnData: ConfirmClassConfigurationSaveDialogData;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: ConfirmClassConfigurationSaveDialogData) => {
        returnData = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnData;
      });
  }

  openDeleteClassConfigurationDialog() {
    const dialogRef = this.dialog.open(
      DeleteClassConfigurationDialogComponent,
      {
        width: '500px',
        minWidth: '500px',
        height: '400px',
        minHeight: '400px',
        data: { configurator: undefined },
        disableClose: true,
      }
    );

    let returnData: DeleteClassConfigurationDialogData;
    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: DeleteClassConfigurationDialogData) => {
        returnData = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnData;
      });
  }

  openChangeIconDialog(currentImagePath: string) {
    const dialogRef = this.dialog.open(ChangeIconDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { imagePath: currentImagePath },
      disableClose: true,
    });

    let imagePath: string;
    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: ChangeIconDialogData) => {
        if (!isNullOrUndefined(result)) {
          imagePath = result.imagePath;
        }
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return imagePath;
      });
  }

  openPropertyCreationDialog(tenantAdmin: User) {
    const dialogRef = this.dialog.open(PropertyCreationDialogComponent, {
      width: '90vw',
      minWidth: '90vw',
      height: '90vh',
      minHeight: '90vh',
      data: { tenantAdmin: tenantAdmin },
      disableClose: true,
    });

    let returnValue: PropertyCreationDialogData;
    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: PropertyCreationDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }

  /**
   * ******INSTANTIATION******
   */


  openInstanceFormPreviewDialog(
    classDefinitions: ClassDefinition[],
    relationships: Relationship[],
    rootClassDefinition: ClassDefinition
  ) {
    const dialogRef = this.dialog.open(
      ClassInstanceFormPreviewDialogComponent,
      {
        width: '90vw',
        minWidth: '90vw',
        height: '90vh',
        minHeight: '90vh',
        data: {
          classDefinitions: classDefinitions,
          relationships: relationships,
          rootClassDefinition: rootClassDefinition,
        },
        disableClose: true,
      }
    );

    let returnData: ClassInstanceFormPreviewDialogData;
    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: ClassInstanceFormPreviewDialogData) => {
        returnData = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnData;
      });
  }

  openPreviewExportDialog(
    classConfigurationIds: string[]
  ) {
    const dialogRef = this.dialog.open(
      ClassInstanceFormPreviewExportDialogComponent,
      {
        width: '90vw',
        minWidth: '90vw',
        height: '90vh',
        minHeight: '90vh',
        data: {
          classConfigurationIds: classConfigurationIds,
        },
        disableClose: true,
      }
    );

    let returnValue: ClassInstanceFormPreviewExportDialogData;
    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: ClassInstanceFormPreviewExportDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }



  /*
   *  Matching-Configurator Dialogs
   */
  openNewMatchingDialog() {
    const dialogRef = this.dialog.open(NewMatchingDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '450px',
      minHeight: '450px',
      data: {},
      disableClose: true,
    });

    let returnValue: NewMatchingDialogData;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: NewMatchingDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }

  openOpenMatchingDialog() {
    const dialogRef = this.dialog.open(OpenMatchingDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: {},
      disableClose: true,
    });

    let matchingConfiguration: MatchingConfiguration;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: OpenMatchingDialogData) => {
        if (!isNullOrUndefined(result)) {
          matchingConfiguration = result.matchingConfiguration;
        }
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return matchingConfiguration;
      });
  }

  openDeleteMatchingDialog() {
    const dialogRef = this.dialog.open(DeleteMatchingDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: {},
      disableClose: true,
    });

    let idsDeleted: string[];

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: DeleteMatchingDialogData) => {
        if (!isNullOrUndefined(result)) {
          idsDeleted = result.idsToDelete;
        }
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return idsDeleted;
      });
  }

  openAddClassDefinitionDialog(
    matchingEntityConfiguration: MatchingEntityMappingConfiguration, existingEntityPaths: string[]
  ) {
    const dialogRef = this.dialog.open(AddClassDefinitionDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '418px',
      minHeight: '418px',
      data: {
        matchingEntityConfiguration: matchingEntityConfiguration,
        existingEntityPaths: existingEntityPaths,
        addedEntities: [],
      },
    });

    let returnValue: AddClassDefinitionDialogData;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: AddClassDefinitionDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }

  openAddPropertyDialog(
    classDefinition: ClassDefinition,
    allClassDefinitions: ClassDefinition[],
    allRelationships: Relationship[]
  ) {
    const dialogRef = this.dialog.open(AddPropertyDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '418px',
      minHeight: '418px',
      data: {
        classDefinition: classDefinition,
        allClassDefinitions: allClassDefinitions,
        allRelationships: allRelationships,
      },
    });

    let returnValue: AddPropertyDialogData;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: AddPropertyDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }

  openRemovePropertyDialog(classDefinition: ClassDefinition) {
    const dialogRef = this.dialog.open(RemovePropertyDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { classDefinition: classDefinition },
    });

    let returnValue: RemovePropertyDialogData;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: RemovePropertyDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }

  openAddHelpseekerDialog(helpseekers: User[]) {
    const dialogRef = this.dialog.open(AddHelpseekerDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '418px',
      minHeight: '418px',
      data: {
        helpseekers
      },
    });

    let returnValue: AddHelpseekerDialogData;

    dialogRef
      .beforeClose()
      .toPromise()
      .then((result: AddHelpseekerDialogData) => {
        returnValue = result;
      });

    return dialogRef
      .afterClosed()
      .toPromise()
      .then(() => {
        return returnValue;
      });
  }
}
