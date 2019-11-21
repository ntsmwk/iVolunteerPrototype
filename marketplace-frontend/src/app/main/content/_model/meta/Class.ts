import { ClassProperty, PropertyInstance } from './Property';

export class ClassDefinition {
    id: string;
    parentId: string;
    root: boolean;
    name: string;
    classArchetype: ClassArchetype;
    properties: ClassProperty<any>[] = [];
    marketplaceId: string;
    timestamp: Date;
}

export class CompetenceClassDefinition extends ClassDefinition{

}

export class ClassInstance {
    id: string;
    name: string;
    classDefinition: ClassDefinition;
    properties: PropertyInstance<any>;
    marketplaceId: string;
    timestamp: Date;
    userId: string;
}

export class CompetenceClassInstance extends ClassInstance{

}

export enum ClassArchetype {
    COMPETENCE='COMPETENCE',
    TASK='TASK',
    FUNCTION='FUNCTION',
    ACHIEVEMENT='ACHIEVEMENT'
}