import { Property } from "./properties/Property";

export class UserDefinedTaskTemplate {
    id: string;
    name: string;
    properties: Property<any>[];
}