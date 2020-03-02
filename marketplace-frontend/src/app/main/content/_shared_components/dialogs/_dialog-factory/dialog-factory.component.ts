import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { UserDefinedTaskTemplate } from 'app/main/content/_model/user-defined-task-template';
import { PropertyItem } from 'app/main/content/_model/meta/Property';
import { AddOrRemoveDialogComponent, AddOrRemoveDialogData } from '../add-or-remove-dialog/add-or-remove-dialog.component';
import { isNullOrUndefined } from 'util';
import { TextFieldDialogComponent, TextFieldDialogData } from '../text-field-dialog/text-field-dialog.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SortDialogComponent, SortDialogData } from '../sort-dialog/sort-dialog.component';
import { ChooseTemplateToCopyDialogComponent, ChooseTemplateToCopyDialogData } from '../choose-dialog/choose-dialog.component';
import { OpenDialogComponent, OpenDialogData } from 'app/main/content/_components/help-seeker/configurator/configurator-editor/open-dialog/open-dialog.component';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { SaveAsDialogComponent } from 'app/main/content/_components/help-seeker/configurator/configurator-editor/save-as-dialog/save-as-dialog.component';
import { ClassInstanceFormPreviewDialogComponent } from 'app/main/content/_components/help-seeker/configurator/class-instances/form-preview-dialog/form-preview-dialog.component';
import { ChangeIconDialogData, ChangeIconDialogComponent } from 'app/main/content/_components/help-seeker/configurator/configurator-editor/icon-dialog/icon-dialog.component';

@Component({
  selector: 'app-dialog-factory',
  templateUrl: './dialog-factory.component.html',
  styleUrls: ['./dialog-factory.component.scss']
})
export class DialogFactoryComponent implements OnInit {


  constructor(public dialog: MatDialog) { }

  ngOnInit() {


  }


  //// TODO EXPAND DIALOG FACTORY


  /**
   * ADD PROPERTY DIALOG
   * 
   * Dialog displaying properties to be added to template @param template 
   * 
   * @param properties: list of all properties from server
   */

  addPropertyDialog(template: UserDefinedTaskTemplate, properties: PropertyItem[]) {
    const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
      position: { top: '50px', },
      width: '500px',
      data: this.prepareDataForAdd('Add Properties', template, properties)
    });

    let propIds: string[];

    dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
      if (!isNullOrUndefined(result)) {
        propIds = [];

        for (const s of result.checkboxStates) {
          if (s.dirty) {
            propIds.push(s.propertyItem.id);

          }
        }
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return propIds;
    });

  }

  addPropertyDialogGeneric(properties: PropertyItem[], addedProperties: PropertyItem[]) {
    const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
      position: { top: '50px' },
      width: '500px',
      data: this.prepareDataForGenericAdd('Add Properties', addedProperties, properties)
    });

    const returnValue: { propertyItems: PropertyItem[], key: string } = { propertyItems: [], key: undefined };

    dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
      if (!isNullOrUndefined(result)) {

        returnValue.key = result.key;

        if (result.key == 'new_property') {
          return;
        }

        if (!isNullOrUndefined(result.checkboxStates)) {
          returnValue.propertyItems = [];
          for (const s of result.checkboxStates) {
            if (s.dirty) {
              returnValue.propertyItems.push(s.propertyItem);
            }
          }
        }
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnValue;
    });

  }

  private prepareDataForGenericAdd(label: string, addedPropertyItems: PropertyItem[], propertyItems: PropertyItem[]): AddOrRemoveDialogData {
    const states: { propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean }[] = [];

    for (const p of propertyItems) {
      states.push({ propertyItem: p, disabled: false, checked: false, dirty: false });
      for (const ap of addedPropertyItems) {
        if (p.id === ap.id) {
          states[states.length - 1].disabled = true;
          states[states.length - 1].checked = true;
        }
      }
    }

    const data: AddOrRemoveDialogData = { label: label, checkboxStates: states, key: 'add' };
    return data;

  }

  private prepareDataForAdd(label: string, template: UserDefinedTaskTemplate, propertyItems: PropertyItem[]): AddOrRemoveDialogData {
    const states: { propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean }[] = [];

    for (const p of propertyItems) {
      states.push({ propertyItem: p, disabled: false, checked: false, dirty: false });
      for (const tp of template.templateProperties) {
        if (p.id === tp.id) {
          states[states.length - 1].disabled = true;
          states[states.length - 1].checked = true;
        }
      }
    }

    const data: AddOrRemoveDialogData = { label: label, checkboxStates: states, key: 'add' };
    return data;
  }

  removePropertyDialogGeneric(addedProperties: PropertyItem[]) {
    const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
      width: '500px',
      data: this.prepareDataForGenericRemove('Remove Properties', addedProperties)
    });

    const returnValue: { propertyItems: PropertyItem[], key: string } = { propertyItems: [], key: undefined };
    dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
      if (!isNullOrUndefined(result)) {

        if (!isNullOrUndefined(result.checkboxStates)) {
          for (const s of result.checkboxStates) {
            if (s.dirty) {
              returnValue.propertyItems.push(s.propertyItem);
            }
          }
        }
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return returnValue.propertyItems;
    });
  }

  prepareDataForGenericRemove(label: string, addedProperties: PropertyItem[]) {

    const states: { propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean }[] = [];

    for (const property of addedProperties) {
      states.push({ propertyItem: property, disabled: false, checked: false, dirty: false });
    }

    const data: AddOrRemoveDialogData = { label: label, checkboxStates: states, key: 'remove' };
    return data;
  }

  removePropertyDialog(template: UserDefinedTaskTemplate) {
    const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
      width: '500px',
      data: this.prepareDataForRemove('Remove Properties', template)
    });

    let propIds: string[];

    dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
      if (!isNullOrUndefined(result)) {

        propIds = [];
        for (const s of result.checkboxStates) {
          if (s.dirty) {
            propIds.push(s.propertyItem.id);
          }
        }
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return propIds;
    });
  }

  private prepareDataForRemove(label: string, template: UserDefinedTaskTemplate): AddOrRemoveDialogData {
    const states: { propertyItem: PropertyItem, disabled: boolean, checked: boolean, dirty: boolean }[] = [];

    for (const tp of template.templateProperties) {
      states.push({ propertyItem: tp, disabled: false, checked: false, dirty: false });
    }

    const data: AddOrRemoveDialogData = { label: label, checkboxStates: states, key: 'remove' };
    return data;
  }


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

  openConfiguratorDialog(marketplace: Marketplace) {
    const dialogRef = this.dialog.open(OpenDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace, configurator: undefined },
      disableClose: true

    });

    let configurator: Configurator;
    dialogRef.beforeClose().toPromise().then((result: OpenDialogData) => {
      configurator = result.configurator;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return configurator;
    });
  }

  openSaveConfiguratorDialog(marketplace: Marketplace) {
    const dialogRef = this.dialog.open(SaveAsDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace, configurator: undefined },
      disableClose: true
    });

    let configurator: Configurator;
    dialogRef.beforeClose().toPromise().then((result: OpenDialogData) => {
      if (!isNullOrUndefined(result)) {
        configurator = result.configurator;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return configurator;
    });
  }

  openInstanceFormPreviewDialog(marketplace: Marketplace, classConfigurationIds: string[]) {
    const dialogRef = this.dialog.open(ClassInstanceFormPreviewDialogComponent, {
      width: '90vw',
      minWidth: '90vw',
      height: '90vh',
      minHeight: '90vh',
      data: { marketplace: marketplace, classConfigurationIds: classConfigurationIds},
      disableClose: true
    });

    let configurator: Configurator;
    dialogRef.beforeClose().toPromise().then((result: OpenDialogData) => {
      if (!isNullOrUndefined(result)) {
        configurator = result.configurator;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return configurator;
    });
  }

  openChangeIconDialog(marketplace: Marketplace, currentImagePath: string) {
    const dialogRef = this.dialog.open(ChangeIconDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { marketplace: marketplace, imagePath: currentImagePath},
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





}
