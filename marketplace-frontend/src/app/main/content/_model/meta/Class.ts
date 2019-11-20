import { ClassProperty, PropertyInstance } from './Property';

export class ClassDefinition {
    id: string;
    parentId: string;
    root: boolean;
    name: string;
    classArchetype: ClassArchetype;
    properties: ClassProperty<any>[] = [];
    matchingRules: any[]
}

export class ClassInstance {
    id: string;
    classDefinition: ClassDefinition;
    properties: PropertyInstance<any>;
    matchingRules: any[] = [];
}

export enum ClassArchetype {
    COMPETENCE='COMPETENCE',
    TASK='TASK',
    FUNCTION='FUNCTION',
    ACHIEVEMENT='ACHIEVEMENT'
}