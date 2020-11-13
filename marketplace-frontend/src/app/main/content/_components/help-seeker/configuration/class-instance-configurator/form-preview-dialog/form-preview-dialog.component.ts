// import { Marketplace } from 'app/main/content/_model/marketplace';
// import { ClassDefinition, ClassInstance } from 'app/main/content/_model/meta/class';
// import { Relationship } from 'app/main/content/_model/meta/relationship';
// import { Component, OnInit, Inject } from '@angular/core';
// import { DynamicFormItemService } from 'app/main/content/_service/dynamic-form-item.service';
// import { DynamicFormItemControlService } from 'app/main/content/_service/dynamic-form-item-control.service';
// import { FormConfiguration, FormEntry, FormEntryReturnEventData } from 'app/main/content/_model/meta/form';
// import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
// import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
// import { isNullOrUndefined } from 'util';
// import { LoginService } from 'app/main/content/_service/login.service';
// import { GlobalInfo } from 'app/main/content/_model/global-info';

// export interface ClassInstanceFormPreviewDialogData {
//     classDefinitions: ClassDefinition[];
//     relationships: Relationship[];
//     rootClassDefinition: ClassDefinition;
// }

// @Component({
//     selector: 'class-instance-form-preview-dialog',
//     templateUrl: './form-preview-dialog.component.html',
//     styleUrls: ['./form-preview-dialog.component.scss'],
//     providers: [DynamicFormItemService, DynamicFormItemControlService]
// })
// export class ClassInstanceFormPreviewDialogComponent implements OnInit {

//     formConfigurations: FormConfiguration[];
//     currentFormConfiguration: FormConfiguration;

//     returnedClassInstances: ClassInstance[];

//     expectedNumberOfResults: number;

//     marketplace: Marketplace;

//     isLoaded = false;


//     constructor(
//         public dialogRef: MatDialogRef<ClassInstanceFormPreviewDialogComponent>,
//         @Inject(MAT_DIALOG_DATA) public data: ClassInstanceFormPreviewDialogData,

//         private classDefinitionService: ClassDefinitionService,
//         private formItemService: DynamicFormItemService,
//         private formItemControlService: DynamicFormItemControlService,
//         private loginService: LoginService,
//     ) {
//     }

//     async ngOnInit() {

//         const globalInfo = <GlobalInfo>(await this.loginService.getGlobalInfo().toPromise());

//         this.returnedClassInstances = [];

//         this.classDefinitionService
//             .getFormConfigurationPreview(globalInfo.marketplace, this.data.classDefinitions, this.data.relationships, this.data.rootClassDefinition)
//             .toPromise()
//             .then((ret: FormConfiguration[]) => {
//                 this.formConfigurations = ret;

//                 for (const config of this.formConfigurations) {
//                     config.formEntry = this.addQuestionsAndFormGroup(config.formEntry, config.formEntry.classDefinitions[0].id + '.');
//                 }

//             }).then(() => {
//                 this.currentFormConfiguration = this.formConfigurations.pop();
//                 this.isLoaded = true;
//             });
//     }

//     private addQuestionsAndFormGroup(formEntry: FormEntry, idPrefix: string) {
//         formEntry.formItems = this.formItemService.getFormItemsFromProperties(formEntry.classProperties, idPrefix);
//         formEntry.formGroup = this.formItemControlService.toFormGroup(formEntry.formItems);

//         if (!isNullOrUndefined(formEntry.subEntries)) {
//             for (let subEntry of formEntry.subEntries) {
//                 const newIdPrefix = idPrefix + subEntry.classDefinitions[0].id + '.';
//                 subEntry = this.addQuestionsAndFormGroup(subEntry, newIdPrefix);
//             }
//         }
//         return formEntry;
//     }

//     handleResultEvent(event: FormEntryReturnEventData) {

//     }

//     handleCloseClick() {
//         this.dialogRef.close();
//     }

//     printAnything(anything: any) {
//         console.log(anything);
//     }

//     navigateBack() {
//         window.history.back();
//     }

// }