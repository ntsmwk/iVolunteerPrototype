import { ClassDefinition } from './Class';
import { QuestionBase } from '../dynamic-forms/questions';
import { FormGroup } from '@angular/forms';
import { ClassProperty } from './Property';
import { ClassConfiguration } from '../configurations';
import { Relationship } from './Relationship';

export class FormEntry {
    positionLevel: string;
    classDefinitions: ClassDefinition[];
    classProperties: ClassProperty<any>[];

    enumRepresentations: EnumRepresentation[];

    subEntries: FormEntry[];

    questions: QuestionBase<any>[] = [];
    formGroup: FormGroup;

    imagePath: string;
}

export class FormConfiguration {
    id: string;
    name: string;
    formEntry: FormEntry;

}

export class FormConfigurationPreviewRequest {
    classDefinitions: ClassDefinition[];
    relationships: Relationship[];

    constructor(classDefinitions: ClassDefinition[], relationships: Relationship[]) {
        this.classDefinitions = classDefinitions;
        this.relationships = relationships;
    }
}

export class FormEntryReturnEventData {
    formGroup: FormGroup;
    formConfigurationId: string;

    constructor(formGroup: FormGroup, formConfigurationId: string) {
        this.formGroup = formGroup;
        this.formConfigurationId = formConfigurationId;
    }
}

export class EnumRepresentation {
    id: string;
    enumEntries: EnumEntry[];
    selectedEntries: EnumEntry[];
    classDefinition: ClassDefinition;
}

export class EnumEntry {
    level: number;
    position: number[];
    value: string;
    selectable: boolean;
}

