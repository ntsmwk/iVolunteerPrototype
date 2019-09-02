import { ClassProperty, PropertyInstance, Property } from "./Property";

export class ClassDefintion {
    id: string;
    parentId: string; 

    name: string;

    //TODO
    // properties: ClassProperty<any>[];
    
    properties: Property<any>[];
    matchingRules: any[]
}

export class ClassInstance {
    id: string;
    classDefinitionId: string;
    properties: PropertyInstance<any>;
    matchingRules: any[];
}