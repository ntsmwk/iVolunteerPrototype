import { TemplateProperty } from './meta/property';

export class UserDefinedTaskTemplate {
    id: string;
    name: string;
    description: string;

    kind: string;

    templateProperties?: TemplateProperty<any>[];
    templates?: UserDefinedTaskTemplate[];

    order: number;
}

export class SingleUserDefinedTaskTemplate {
    id: string;
    name: string;
    description: string;
    templateProperties: TemplateProperty<any>[];

    order: number;
}

export class MultiUserDefinedTaskTemplate {
    id: string;
    name: string;
    description: string;
    templates: UserDefinedTaskTemplate[];

    order: number;
}

export class UserDefinedTaskTemplateStub {
    id: string;
    name: string;
    description: string;
    kind: string;
}
