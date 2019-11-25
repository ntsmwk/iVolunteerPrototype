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
    classDefinitionId: string;
    properties: PropertyInstance<any>[];
    marketplaceId: string;
    timestamp: Date;
    userId: string;
    classArchetype: ClassArchetype;

    constructor(classDefinition: ClassDefinition, properties: PropertyInstance<any>[]) {
        this.name = classDefinition.name;
        this.classDefinitionId = classDefinition.id;
        this.properties = properties;
        this.marketplaceId = classDefinition.marketplaceId;
        this.timestamp = new Date();
        this.userId = null;
    }
}

export class CompetenceClassInstance extends ClassInstance{
    constructor(classDefintion: ClassDefinition, properties: PropertyInstance<any>[]) {
        super(classDefintion, properties);
        this.classArchetype = ClassArchetype.COMPETENCE;
    }
}

export enum ClassArchetype {
    COMPETENCE='COMPETENCE',
    TASK='TASK',
    FUNCTION='FUNCTION',
    ACHIEVEMENT='ACHIEVEMENT'

}
export namespace ClassArchetype {

    export function getClassArchetypeLabel(classArchetype: ClassArchetype) {
        switch (classArchetype) {
            case "COMPETENCE": return "Competence";
            case "TASK": return "Task";
            case "FUNCTION": return "Function";
            case "ACHIEVEMENT": return "Achievement";
        }
    }
}

