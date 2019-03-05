import { Property } from "./properties/Property";

export class UserDefinedTaskTemplate {
    id: string;
    name: string;

    description: string;

    properties: Property<any>[];
}

export class UserDefinedTaskTemplateStub {
    id: string;
    name: string;
    description: string;
}