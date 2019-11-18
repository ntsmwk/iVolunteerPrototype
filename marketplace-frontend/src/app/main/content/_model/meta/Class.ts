import { ClassProperty, PropertyInstance } from "./Property";

export class ClassDefinition {
    id: string;
    parentId: string; 
    root: boolean;
    name: string;
    classArchetype: ClassArchetype;

    properties: ClassProperty<any>[];
    matchingRules: any[]
}

export class ClassInstance {
    id: string;
    classDefinitionId: string;
    properties: PropertyInstance<any>;
    matchingRules: any[];
}

export type ClassArchetype = "COMPETENCE" | "TASK" | "FUNCTION" | "ACHIEVEMENT" | "OTHER"