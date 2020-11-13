// import { ClassDefinition } from './class';
// // import { DynamicFormItemBase } from '../dynamic-forms/item';
// import { FormGroup } from '@angular/forms';
// import { ClassProperty } from './property/property';
// import { Relationship } from './relationship';

// export class FormEntry {

//     id: string;
//     positionLevel: string;
//     classDefinitions: ClassDefinition[];
//     classProperties: ClassProperty<any>[];

//     subEntries: FormEntry[];

//     formItems: DynamicFormItemBase<any>[] = [];
//     formGroup: FormGroup;

//     imagePath: string;
//     multipleAllowed: boolean;
// }

// export class FormConfiguration {
//     id: string;
//     name: string;
//     formEntry: FormEntry;

// }

// export class FormConfigurationPreviewRequest {
//     classDefinitions: ClassDefinition[];
//     relationships: Relationship[];
//     rootClassDefinition: ClassDefinition;

//     constructor(classDefinitions: ClassDefinition[], relationships: Relationship[], rootClassDefinition: ClassDefinition) {
//         this.classDefinitions = classDefinitions;
//         this.relationships = relationships;
//         this.rootClassDefinition = rootClassDefinition;
//     }
// }

// export class FormEntryReturnEventData {
//     value: any;
//     formConfigurationId: string;

//     constructor(formConfigurationId: string, value: any[]) {
//         this.value = value;
//         this.formConfigurationId = formConfigurationId;
//     }
// }


