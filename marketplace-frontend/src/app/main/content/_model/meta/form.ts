import { ClassDefinition } from './Class';
import { QuestionBase } from '../dynamic-forms/questions';
import { FormGroup } from '@angular/forms';
import { ClassProperty } from './Property';

export class FormEntry {
    positionLevel: string;
    classDefinitions: ClassDefinition[];
    classProperties: ClassProperty<any>[];
    subEntries: FormEntry[];

    questions: QuestionBase<any>[] = [];
    formGroup: FormGroup;
}

export class FormConfiguration {
    id: string;
    name: string;
    formEntries: FormEntry[] = [];
}

export class FormEntryReturnEventData {
    formGroup: FormGroup;
    formConfigurationId: string;

    constructor(formGroup: FormGroup, formConfigurationId: string) {
        this.formGroup = formGroup;
        this.formConfigurationId = formConfigurationId;
    }
}

