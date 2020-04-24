import { Directive } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UserDefinedTaskTemplate } from 'app/main/content/_model/user-defined-task-template';
import { PropertyItem } from 'app/main/content/_model/meta/Property';
import { AddOrRemoveDialogComponent, AddOrRemoveDialogData } from '../deprecrated-add-or-remove-dialog/add-or-remove-dialog.component';
import { isNullOrUndefined } from 'util';
import { TextFieldDialogComponent, TextFieldDialogData } from '../text-field-dialog/text-field-dialog.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SortDialogComponent, SortDialogData } from '../sort-dialog/sort-dialog.component';
import { ChooseTemplateToCopyDialogComponent, ChooseTemplateToCopyDialogData } from '../choose-dialog/choose-dialog.component';
import { OpenClassConfigurationDialogComponent, OpenClassConfigurationDialogData } from 'app/main/content/configurator/class-configurator/open-dialog/open-dialog.component';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { SaveClassConfigurationAsDialogComponent, SaveClassConfigurationAsDialogData } from 'app/main/content/configurator/class-configurator/save-as-dialog/save-as-dialog.component';
import { ClassInstanceFormPreviewDialogComponent } from 'app/main/content/configurator/class-instances/form-preview-dialog/form-preview-dialog.component';
import { ChangeIconDialogData, ChangeIconDialogComponent } from 'app/main/content/configurator/class-configurator/icon-dialog/icon-dialog.component';
import { NewMatchingDialogComponent, NewMatchingDialogData } from 'app/main/content/configurator/matching-configurator/new-dialog/new-dialog.component';
import { OpenMatchingDialogComponent, OpenMatchingDialogData } from 'app/main/content/configurator/matching-configurator/open-dialog/open-dialog.component';
import { ClassConfiguration, MatchingConfiguration } from 'app/main/content/_model/configurations';
import { DeleteMatchingDialogComponent, DeleteMatchingDialogData } from 'app/main/content/configurator/matching-configurator/delete-dialog/delete-dialog.component';
import { NewClassConfigurationDialogComponent, NewClassConfigurationDialogData } from 'app/main/content/configurator/class-configurator/new-dialog/new-dialog.component';
import { ConfirmClassConfigurationSaveDialogComponent, ConfirmClassConfigurationSaveDialogData } from 'app/main/content/configurator/class-configurator/confirm-save-dialog/confirm-save-dialog.component';
import { Relationship } from 'app/main/content/_model/meta/Relationship';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { DeleteClassConfigurationDialogData, DeleteClassConfigurationDialogComponent } from 'app/main/content/configurator/class-configurator/delete-dialog/delete-dialog.component';
import { AddPropertyDialogComponent, AddPropertyDialogData } from '../add-property-dialog/add-property-dialog.component';
import { RemoveDialogData, RemoveDialogComponent } from '../remove-dialog/remove-dialog.component';

@Directive({
  selector: 'app-dialog-factory'
})
export class DialogFactoryDirective {

  constructor(public dialog: MatDialog) { }

  // /**
  //  * ADD PROPERTY DIALOG
  //  * 
  //  * Dialog displaying properties to be added to template @param template 
  //  * 
  //  * @param properties: list of all properties from server
  //  */

  // addPropertyDialog(template: UserDefinedTaskTemplate, properties: PropertyItem[]) {
  //   const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
  //     position: { top: '50px', },
  //     width: '500px',
  //     data: this.prepareDataForAdd('Add Properties', template, properties)
  //   });

  //   let propIds: string[];

  //   dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
  //     if (!isNullOrUndefined(result)) {
  //       propIds = [];

  //       for (const s of result.checkboxStates) {
  //         if (s.dirty) {
  //           propIds.push(s.propertyItem.id);

  //         }
  //       }
  //     }
  //   });

  //   return dialogRef.afterClosed().toPromise().then(() => {
  //     return propIds;
  //   });
  // }

  // addPropertyDialogGeneric(properties: PropertyItem[], addedProperties: PropertyItem[]) {
  //   const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
  //     position: { top: '50px' },
  //     width: '500px',
  //     data: this.prepareDataForGenericAdd('Add Properties', addedProperties, properties)
  //   });

  //   const returnValue: { propertyItems: PropertyItem[], key: string } = { propertyItems: [], key: undefined };

  //   dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
  //     if (!isNullOrUndefined(result)) {

  //       returnValue.key = result.key;

  //       if (result.key === 'new_property') {
  //         return;
  //       }

  //       if (!isNullOrUndefined(result.checkboxStates)) {
  //         returnValue.propertyItems = [];
  //         for (const s of result.checkboxStates) {
  //           if (s.dirty) {
  //             returnValue.propertyItems.push(s.propertyItem);
  //           }
  //         }
  //       }
  //     }
  //   });

  //   return dialogRef.afterClosed().toPromise().then(() => {
  //     return returnValue;
  //   });
  // }

  // private prepareDataForGenericAdd(label: string, addedPropertyItems: PropertyItem[], propertyItems: PropertyItem[]): AddOrRemoveDialogData {
  //   const states: { propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean }[] = [];

  //   for (const p of propertyItems) {
  //     states.push({ propertyItem: p, disabled: false, checked: false, dirty: false });
  //     for (const ap of addedPropertyItems) {
  //       if (p.id === ap.id) {
  //         states[states.length - 1].disabled = true;
  //         states[states.length - 1].checked = true;
  //       }
  //     }
  //   }

  //   const data: AddOrRemoveDialogData = { label: label, checkboxStates: states, key: 'add' };
  //   return data;
  // }

  // private prepareDataForAdd(label: string, template: UserDefinedTaskTemplate, propertyItems: PropertyItem[]): AddOrRemoveDialogData {
  //   const states: { propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean }[] = [];

  //   for (const p of propertyItems) {
  //     states.push({ propertyItem: p, disabled: false, checked: false, dirty: false });
  //     for (const tp of template.templateProperties) {
  //       if (p.id === tp.id) {
  //         states[states.length - 1].disabled = true;
  //         states[states.length - 1].checked = true;
  //       }
  //     }
  //   }

  //   const data: AddOrRemoveDialogData = { label: label, checkboxStates: states, key: 'add' };
  //   return data;
  // }

  // removePropertyDialogGeneric(addedProperties: PropertyItem[]) {
  //   const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
  //     width: '500px',
  //     data: this.prepareDataForGenericRemove('Remove Properties', addedProperties)
  //   });

  //   const returnValue: { propertyItems: PropertyItem[], key: string } = { propertyItems: [], key: undefined };
  //   dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
  //     if (!isNullOrUndefined(result)) {

  //       if (!isNullOrUndefined(result.checkboxStates)) {
  //         for (const s of result.checkboxStates) {
  //           if (s.dirty) {
  //             returnValue.propertyItems.push(s.propertyItem);
  //           }
  //         }
  //       }
  //     }
  //   });

  //   return dialogRef.afterClosed().toPromise().then(() => {
  //     return returnValue.propertyItems;
  //   });
  // }

  // prepareDataForGenericRemove(label: string, addedProperties: PropertyItem[]) {

  //   const states: { propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean }[] = [];

  //   for (const property of addedProperties) {
  //     states.push({ propertyItem: property, disabled: false, checked: false, dirty: false });
  //   }

  //   const data: AddOrRemoveDialogData = { label: label, checkboxStates: states, key: 'remove' };
  //   return data;
  // }

  // removePropertyDialog(template: UserDefinedTaskTemplate) {
  //   const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
  //     width: '500px',
  //     data: this.prepareDataForRemove('Remove Properties', template)
  //   });

  //   let propIds: string[];

  //   dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
  //     if (!isNullOrUndefined(result)) {
  //       propIds = [];
  //       for (const s of result.checkboxStates) {
  //         if (s.dirty) {
  //           propIds.push(s.propertyItem.id);
  //         }
  //       }
  //     }
  //   });

  //   return dialogRef.afterClosed().toPromise().then(() => {
  //     return propIds;
  //   });
  // }

  // private prepareDataForRemove(label: string, template: UserDefinedTaskTemplate): AddOrRemoveDialogData {
  //   const states: { propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean }[] = [];

  //   for (const tp of template.templateProperties) {
  //     states.push({ propertyItem: tp, disabled: false, checked: false, dirty: false });
  //   }

  //   const data: AddOrRemoveDialogData = { label: label, checkboxStates: states, key: 'remove' };
  //   return data;
  // }


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

  // newRelationshipDialog(classIdTarget: string) {
  //   const dialogRef = this.dialog.open(RelationshipDialogComponent, {
  //     width: '500px',
  //     data: {classIdTarget: classIdTarget},
  //     disableClose: true
  //   });

  //   let ret: Relationship = undefined;
  //   dialogRef.beforeClose().toPromise().then((result: Relationship) => {
  //     ret = result;
  //   });

  //   return dialogRef.afterClosed().toPromise().then(() => {
  //     return ret;
  //   });

  // }

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

  openInstanceFormPreviewDialog(marketplace: Marketplace, classConfigurationIds: string[]) {
    const dialogRef = this.dialog.open(ClassInstanceFormPreviewDialogComponent, {
      width: '90vw',
      minWidth: '90vw',
      height: '90vh',
      minHeight: '90vh',
      data: { marketplace: marketplace, classConfigurationIds: classConfigurationIds },
      disableClose: true
    });

    let classConfiguration: ClassConfiguration;
    dialogRef.beforeClose().toPromise().then((result: OpenClassConfigurationDialogData) => {
      if (!isNullOrUndefined(result)) {
        classConfiguration = result.classConfiguration;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return classConfiguration;
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


  openAddPropertyDialog(marketplace: Marketplace, classDefinition: ClassDefinition) {
    const dialogRef = this.dialog.open(AddPropertyDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace, classDefinition: classDefinition }
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
