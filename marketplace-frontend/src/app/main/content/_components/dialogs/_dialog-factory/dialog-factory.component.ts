import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { UserDefinedTaskTemplate } from 'app/main/content/_model/user-defined-task-template';
import { Property } from 'app/main/content/_model/properties/Property';
import { AddOrRemoveDialogComponent, AddOrRemoveDialogData } from '../add-or-remove-dialog/add-or-remove-dialog.component';
import { isNullOrUndefined } from 'util';
import { TextFieldDialogComponent, TextFieldDialogData } from '../text-field-dialog/text-field-dialog.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SortDialogComponent, SortDialogData } from '../sort-dialog/sort-dialog.component';
import { ChooseTemplateToCopyDialogComponent, ChooseTemplateToCopyDialogData } from '../choose-dialog/choose-dialog.component';

@Component({
  selector: 'app-dialog-factory',
  templateUrl: './dialog-factory.component.html',
  styleUrls: ['./dialog-factory.component.scss']
})
export class DialogFactoryComponent {

  constructor(public dialog: MatDialog) { }
  
  //TODO EXPAND DIALOG FACTORY
  
  /**
   * ADD PROPERTY DIALOG
   * 
   * Dialog displaying properties to be added to template @param template 
   * 
   * @param properties: list of all properties from server
   */

  addPropertyDialog(template: UserDefinedTaskTemplate, properties: Property<any>[]) {
    const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
      position: {top: '50px', },
      width: '500px',
      data: this.prepareDataForAdd('Add Properties', template, properties)
    });

    let propIds: string[] = undefined;

    dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
      if (!isNullOrUndefined(result)) {       
        propIds = [];

        for (let s of result.checkboxStates) {
          if (s.dirty) {
            propIds.push(s.property.id); 
          } 
        }
      }
    });

    return dialogRef.afterClosed().toPromise().then( () => {
      return propIds;
    });

  }

  private prepareDataForAdd(label: string, template: UserDefinedTaskTemplate, properties: Property<any>[]): AddOrRemoveDialogData {    
    let states: {property: Property<any>, disabled: boolean, checked: boolean, dirty: boolean}[] = [];

    for (let p of properties) {
      states.push({property: p, disabled: false, checked: false, dirty: false});
      for (let tp of template.properties) {
        if (p.id === tp.id) {
          states[states.length-1].disabled = true;
          states[states.length-1].checked = true;
        } 
      }
    }
    
    let data: AddOrRemoveDialogData = { label: label, checkboxStates: states}
    console.log(data);
    return data;
  }

  removePropertyDialog(template: UserDefinedTaskTemplate) {
    const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
      width: '500px',
      data: this.prepareDataForRemove('Remove Properties', template)
    });

    let propIds: string[] = undefined;

    dialogRef.beforeClose().toPromise().then((result: AddOrRemoveDialogData) => {
      if (!isNullOrUndefined(result)) {
        propIds = [];
        for (let s of result.checkboxStates) {
          if (s.dirty) {
            propIds.push(s.property.id);
          }
        }
      } 
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return propIds;
    })
  }

  private prepareDataForRemove(label: string, template: UserDefinedTaskTemplate): AddOrRemoveDialogData {
    let states: {property: Property<any>, disabled: boolean, checked: boolean, dirty: boolean}[] = [];
    
    for (let tp of template.properties) {
        states.push({property: tp, disabled: false, checked: false, dirty: false});   
    } 

    let data: AddOrRemoveDialogData = { label: label, checkboxStates: states};
    return data;
  }


  /**
   * EDIT TEMPALTE DESCRIPTION DIALOG
   * 
   * Dialog utilized to chance the description of a template @param template 
   *
   * returns new description
   */

  editTemplateDescriptionDialog(template: UserDefinedTaskTemplate) {
    const dialogRef = this.dialog.open(TextFieldDialogComponent, {
      width: '500px',
      data: {label: 'Edit Description', 
             fields: [{description: 'Descripton', hintText: 'Description', value: template.description}]
          }
    });

    let ret: string = undefined;

    dialogRef.beforeClose().toPromise().then((result: TextFieldDialogData) => {
      if (!isNullOrUndefined(result)) {
        console.log("Result: " + result.fields[0].value);
        console.log(result);
        ret = result.fields[0].value;
      }
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    })
  }

  editTemplateNameDialog(template: UserDefinedTaskTemplate) {
    const dialogRef = this.dialog.open(TextFieldDialogComponent, {
      width: '500px',
      data: {label: 'Edit Name', 
             fields: [{description: 'Name', hintText: 'Name', value: template.name}]}
    });

    let ret: string = undefined;

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
      data: {label: 'New Template',
             fields: [{description: 'Name', hintText: 'Name', value: null},
                      {description: 'Description', hintText: 'Description', value: null}]
      }
    });

    let ret: string[] = undefined;

    dialogRef.beforeClose().toPromise().then((result: TextFieldDialogData) => {   
      ret = [];
      if (!isNullOrUndefined(result)) {

        for (let val of result.fields) {
          console.log(val);
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
      data: {title: title, description: description}
    });

    let ret: boolean = undefined;

    dialogRef.beforeClose().toPromise().then((result: boolean) => {
      if (result) {
        ret = result;
      } 
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }
  
  changePropertyOrderDialog(properties: Property<any>[]) {
    const dialogRef = this.dialog.open(SortDialogComponent, {
      width: '500px',
      data: {order: properties, label: "Change Property Order"}
    });

    let ret: any = undefined;
    
    dialogRef.beforeClose().toPromise().then((result: SortDialogData) => {
      ret = result;
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }

  chooseTemplateToCopyDialog(entries: {id: string, label: string, description: string}[]) {
    const dialogRef = this.dialog.open(ChooseTemplateToCopyDialogComponent, {
      width: '1000px',
      data: {entries: entries},
      disableClose: true
      
    });

    let ret: {copyId: string, newName: string, newDescription: string} = undefined;
    dialogRef.beforeClose().toPromise().then((result: ChooseTemplateToCopyDialogData) => {
      ret = {copyId: result.returnId, newName: result.newLabel, newDescription: result.newDescription};
    });

    return dialogRef.afterClosed().toPromise().then(() => {
      return ret;
    });
  }

  entries: {id: string, label: string, description: string}[];
  returnId: string;
  returnLabel: string;

  

  
  

}
