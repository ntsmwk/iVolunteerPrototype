import { Directive } from '@angular/core';
import { MatDialog } from '@angular/material';
import { UserDefinedTaskTemplate } from 'app/main/content/_model/user-defined-task-template';
import { TextFieldDialogComponent, TextFieldDialogData } from '../text-field-dialog/text-field-dialog.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { PropertyItem } from 'app/main/content/_model/meta/property';
import { SortDialogComponent, SortDialogData } from '../sort-dialog/sort-dialog.component';
import { ChooseTemplateToCopyDialogComponent, ChooseTemplateToCopyDialogData } from '../choose-dialog/choose-dialog.component';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { NewClassConfigurationDialogComponent, NewClassConfigurationDialogData } from 'app/main/content/configurator/class-configurator/new-dialog/new-dialog.component';
import { OpenClassConfigurationDialogComponent, OpenClassConfigurationDialogData } from 'app/main/content/configurator/class-configurator/open-dialog/open-dialog.component';
import { ClassConfiguration, MatchingConfiguration } from 'app/main/content/_model/configurations';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { ConfirmClassConfigurationSaveDialogComponent, ConfirmClassConfigurationSaveDialogData } from 'app/main/content/configurator/class-configurator/confirm-save-dialog/confirm-save-dialog.component';
import { SaveClassConfigurationAsDialogComponent, SaveClassConfigurationAsDialogData } from 'app/main/content/configurator/class-configurator/save-as-dialog/save-as-dialog.component';
import { DeleteClassConfigurationDialogComponent, DeleteClassConfigurationDialogData } from 'app/main/content/configurator/class-configurator/delete-dialog/delete-dialog.component';
import { ClassInstanceFormPreviewDialogComponent, ClassInstanceFormPreviewDialogData } from 'app/main/content/_components/help-seeker/configuration/configurator/class-instances/form-preview-dialog/form-preview-dialog.component';
import { ClassInstanceFormPreviewExportDialogComponent, ClassInstanceFormPreviewExportDialogData } from 'app/main/content/_components/help-seeker/configuration/configurator/class-instances/form-preview-export-dialog/form-preview-export-dialog.component';
import { ChangeIconDialogComponent, ChangeIconDialogData } from 'app/main/content/_components/help-seeker/configuration/configurator/configurator-editor/icon-dialog/icon-dialog.component';
import { NewMatchingDialogComponent, NewMatchingDialogData } from 'app/main/content/configurator/matching-configurator/new-dialog/new-dialog.component';
import { OpenMatchingDialogComponent, OpenMatchingDialogData } from 'app/main/content/configurator/matching-configurator/open-dialog/open-dialog.component';
import { DeleteMatchingDialogComponent, DeleteMatchingDialogData } from 'app/main/content/configurator/matching-configurator/delete-dialog/delete-dialog.component';
import { AddPropertyDialogComponent, AddPropertyDialogData } from 'app/main/content/_components/dialogs/add-property-dialog/add-property-dialog.component';
import { RemoveDialogComponent, RemoveDialogData } from 'app/main/content/_components/dialogs/remove-dialog/remove-dialog.component';
import { isNullOrUndefined } from 'util';

@Directive({
  selector: 'app-dialog-factory'
})
export class DialogFactoryDirective {

  constructor(public dialog: MatDialog) { }

  /**
   * EDIT TEMPLATE DESCRIPTION DIALOG
   * 
   * Dialog utilized to chance the description of a template @param template 
   *
   * returns new description
   */

  editTemplateDescriptionDialog(template: UserDefinedTaskTemplate) {
    const dialogRef = this.dialog.open(TextFieldDialogComponent, {
      width: '500px',
      data: {
        label: 'Edit Description',
        fields: [{ description: 'Descripton', hintText: 'Description', value: template.description }]
      }
    });

    let ret: string;

    dialogRef.beforeClose().toPromise().then((result: TextFieldDialogData) => {
      if (!isNullOrUndefined(result)) {
        ret = result.fields[0].value;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }

  editTemplateNameDialog(template: UserDefinedTaskTemplate) {
    const dialogRef = this.dialog.open(TextFieldDialogComponent, {
      width: '500px',
      data: {
        label: 'Edit Name',
        fields: [{ description: 'Name', hintText: 'Name', value: template.name }]
      }
    });

    let ret: string;

    dialogRef.beforeClose().toPromise().then((result: TextFieldDialogData) => {

      if (!isNullOrUndefined(result)) {
        ret = result.fields[0].value;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }

  newTaskTemplateDialog() {
    const dialogRef = this.dialog.open(TextFieldDialogComponent, {
      width: '500px',
      data: {
        label: 'New Template',
        fields: [{ description: 'Name', hintText: 'Name', value: null },
        { description: 'Description', hintText: 'Description', value: null }]
      }
    });

    let ret: string[];

    dialogRef.beforeClose().toPromise().then((result: TextFieldDialogData) => {
      ret = [];
      if (!isNullOrUndefined(result)) {

        for (const val of result.fields) {
          ret.push(val.value);
        }
      }
    });

    return dialogRef.beforeClose().toPromise().then(() => {
      return ret;
    });

  }

  confirmationDialog(title: string, description: string) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '500px',
      data: { title: title, description: description }
    });

    let ret = false;

    dialogRef.beforeClose().toPromise().then((result: boolean) => {
      if (result) {
        ret = result;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }

  changePropertyOrderDialog(properties: PropertyItem[]) {
    const dialogRef = this.dialog.open(SortDialogComponent, {
      width: '500px',
      // height: '80%',
      data: { list: properties, label: 'Change Property Order' }
    });

    let ret: any;

    dialogRef.beforeClose().toPromise().then((result: SortDialogData) => {
      ret = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }

  changeSubtemplatesOrderDialog(template: UserDefinedTaskTemplate) {
    const dialogRef = this.dialog.open(SortDialogComponent, {
      width: '500px',
      // height: '80%',
      data: { list: template.templates, label: 'Change Sub-Template Order' }
    });

    let ret: any;

    dialogRef.beforeClose().toPromise().then((result: SortDialogData) => {
      ret = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }

  chooseTemplateToCopyDialog(entries: { id: string, label: string, description: string }[]) {
    const dialogRef = this.dialog.open(ChooseTemplateToCopyDialogComponent, {
      width: '1000px',
      data: { entries: entries },
      disableClose: true

    });

    let ret: { copyId: string, newName: string, newDescription: string };
    dialogRef.beforeClose().toPromise().then((result: ChooseTemplateToCopyDialogData) => {
      ret = { copyId: result.returnId, newName: result.newLabel, newDescription: result.newDescription };
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }

  genericDialog1Textfield(label: string, description: string, hintText: string, value: string) {
    const dialogRef = this.dialog.open(TextFieldDialogComponent, {
      width: '500px',
      data: {
        label: label,
        fields: [{ description: description, hintText: hintText, value: value }]
      }
    });

    let ret: string;

    dialogRef.beforeClose().toPromise().then((result: TextFieldDialogData) => {

      if (!isNullOrUndefined(result)) {
        ret = result.fields[0].value;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }


  /**
   *  Class-Configurator Dialogs
   */

  openNewClassConfigurationDialog(marketplace: Marketplace) {
    const dialogRef = this.dialog.open(NewClassConfigurationDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace },
      disableClose: true
    });

    let returnData: NewClassConfigurationDialogData;
    dialogRef.beforeClose().toPromise().then((result: NewClassConfigurationDialogData) => {
      returnData = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnData;
    });
  }

  openConfiguratorDialog(marketplace: Marketplace) {
    const dialogRef = this.dialog.open(OpenClassConfigurationDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace, configurator: undefined },
      disableClose: true
    });

    let returnData: OpenClassConfigurationDialogData;
    dialogRef.beforeClose().toPromise().then((result: OpenClassConfigurationDialogData) => {
      returnData = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnData;
    });
  }

  openSaveConfirmationDialog(marketplace: Marketplace, classConfiguration: ClassConfiguration, classDefinitions: ClassDefinition[],
    relationships: Relationship[], deletedClassDefintions: string[], deletedRelationships: string[]) {

    const dialogRef = this.dialog.open(ConfirmClassConfigurationSaveDialogComponent, {
      width: '500px',
      data: {
        classConfiguration: classConfiguration,
        classDefinitions: classDefinitions,
        relationships: relationships,

        deletedClassDefintions: deletedClassDefintions,
        deletedRelationships: deletedRelationships,

        marketplace: marketplace
      }
    });

    let returnData: ConfirmClassConfigurationSaveDialogData;


    dialogRef.beforeClose().toPromise().then((result: ConfirmClassConfigurationSaveDialogData) => {
      returnData = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnData;
    });
  }

  openSaveClassConfigurationAsDialog(marketplace: Marketplace, classConfiguration: ClassConfiguration, classDefinitions: ClassDefinition[],
    relationships: Relationship[], deletedClassDefintions: string[], deletedRelationships: string[]) {

    const dialogRef = this.dialog.open(SaveClassConfigurationAsDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: {
        classConfiguration: classConfiguration,
        classDefinitions: classDefinitions,
        relationships: relationships,

        deletedClassDefinitions: deletedClassDefintions,
        deletedRelationships: deletedRelationships,

        marketplace: marketplace
      },
      disableClose: true
    });

    let returnData: SaveClassConfigurationAsDialogData;
    dialogRef.beforeClose().toPromise().then((result: SaveClassConfigurationAsDialogData) => {
      returnData = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnData;
    });
  }

  openDeleteClassConfiguratorDialog(marketplace: Marketplace) {
    const dialogRef = this.dialog.open(DeleteClassConfigurationDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace, configurator: undefined },
      disableClose: true
    });

    let returnData: DeleteClassConfigurationDialogData;
    dialogRef.beforeClose().toPromise().then((result: DeleteClassConfigurationDialogData) => {
      returnData = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnData;
    });
  }

  openInstanceFormPreviewDialog(marketplace: Marketplace, classDefinitions: ClassDefinition[], relationships: Relationship[]) {
    const dialogRef = this.dialog.open(ClassInstanceFormPreviewDialogComponent, {
      width: '90vw',
      minWidth: '90vw',
      height: '90vh',
      minHeight: '90vh',
      data: { marketplace: marketplace, classDefinitions: classDefinitions, relationships: relationships },
      disableClose: true
    });

    let returnData: ClassInstanceFormPreviewDialogData;
    dialogRef.beforeClose().toPromise().then((result: ClassInstanceFormPreviewDialogData) => {
      returnData = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnData;
    });
  }

  openPreviewExportDialog(marketplace: Marketplace, classConfigurationIds: string[]) {
    const dialogRef = this.dialog.open(ClassInstanceFormPreviewExportDialogComponent, {
      width: '90vw',
      minWidth: '90vw',
      height: '90vh',
      minHeight: '90vh',
      data: { marketplace: marketplace, classConfigurationIds: classConfigurationIds },
      disableClose: true
    });

    let returnValue: ClassInstanceFormPreviewExportDialogData;
    dialogRef.beforeClose().toPromise().then((result: ClassInstanceFormPreviewExportDialogData) => {
      returnValue = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnValue;
    });
  }

  openChangeIconDialog(marketplace: Marketplace, currentImagePath: string) {
    const dialogRef = this.dialog.open(ChangeIconDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace, imagePath: currentImagePath },
      disableClose: true
    });

    let imagePath: string;
    dialogRef.beforeClose().toPromise().then((result: ChangeIconDialogData) => {
      if (!isNullOrUndefined(result)) {
        imagePath = result.imagePath;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return imagePath;
    });
  }

  /*  
   *  Matching-Configurator Dialogs
   */
  openNewMatchingDialog(marketplace: Marketplace) {
    const dialogRef = this.dialog.open(NewMatchingDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace },
      disableClose: true
    });

    let returnValue: NewMatchingDialogData;

    dialogRef.beforeClose().toPromise().then((result: NewMatchingDialogData) => {
      returnValue = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnValue;
    });
  }

  openOpenMatchingDialog(marketplace: Marketplace) {
    const dialogRef = this.dialog.open(OpenMatchingDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace },
      disableClose: true
    });

    let matchingConfiguration: MatchingConfiguration;

    dialogRef.beforeClose().toPromise().then((result: OpenMatchingDialogData) => {
      if (!isNullOrUndefined(result)) {
        matchingConfiguration = result.matchingConfiguration;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return matchingConfiguration;
    });
  }

  openDeleteMatchingDialog(marketplace: Marketplace) {
    const dialogRef = this.dialog.open(DeleteMatchingDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace },
      disableClose: true
    });

    let idsDeleted: string[];

    dialogRef.beforeClose().toPromise().then((result: DeleteMatchingDialogData) => {
      if (!isNullOrUndefined(result)) {
        idsDeleted = result.idsToDelete;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return idsDeleted;
    });
  }


  openAddPropertyDialog(marketplace: Marketplace, classDefinition: ClassDefinition, allClassDefinitions: ClassDefinition[], allRelationships: Relationship[]) {
    const dialogRef = this.dialog.open(AddPropertyDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace, classDefinition: classDefinition, allClassDefinitions: allClassDefinitions, allRelationships: allRelationships }
    });

    let returnValue: AddPropertyDialogData;

    dialogRef.beforeClose().toPromise().then((result: AddPropertyDialogData) => {
      returnValue = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnValue;
    });
  }

  openRemoveDialog(marketplace: Marketplace, classDefinition: ClassDefinition) {
    const dialogRef = this.dialog.open(RemoveDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace, classDefinition: classDefinition }
    });

    let returnValue: RemoveDialogData;

    dialogRef.beforeClose().toPromise().then((result: RemoveDialogData) => {
      returnValue = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnValue;
    });
  }


}
