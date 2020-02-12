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

    imagePath: string;
}

export class CompetenceClassDefinition extends ClassDefinition {

}

export class ClassInstance {
    id: string;
    name: string;
    classDefinitionId: string;
    properties: PropertyInstance<any>[];
    marketplaceId: string;
    timestamp: Date;
    userId: string;
    issuerId: string;
    imagePath: string;
    classArchetype: ClassArchetype;

    subClassInstances: ClassInstance[];

    constructor(classDefinition: ClassDefinition, properties: PropertyInstance<any>[]) {
        this.name = classDefinition.name;
        this.classDefinitionId = classDefinition.id;
        this.properties = properties;
        this.marketplaceId = classDefinition.marketplaceId;
        this.timestamp = new Date();
        this.userId = null;
        this.classArchetype = classDefinition.classArchetype;
    }
}

export class CompetenceClassInstance extends ClassInstance {
    constructor(classDefintion: ClassDefinition, properties: PropertyInstance<any>[]) {
        super(classDefintion, properties);
        this.classArchetype = ClassArchetype.COMPETENCE;
    }
}

export class TaskClassInstance extends ClassInstance {
    constructor(classDefintion: ClassDefinition, properties: PropertyInstance<any>[]) {
        super(classDefintion, properties);
        this.classArchetype = ClassArchetype.TASK;
    }
}

export class FunctionClassInstance extends ClassInstance {
    constructor(classDefintion: ClassDefinition, properties: PropertyInstance<any>[]) {
        super(classDefintion, properties);
        this.classArchetype = ClassArchetype.FUNCTION;
    }
}

export class AchievementClassInstance extends ClassInstance {
    constructor(classDefintion: ClassDefinition, properties: PropertyInstance<any>[]) {
        super(classDefintion, properties);
        this.classArchetype = ClassArchetype.ACHIEVEMENT;
    }
}


export enum ClassArchetype {
    COMPETENCE = 'COMPETENCE',
    TASK = 'TASK',
    FUNCTION = 'FUNCTION',
    ACHIEVEMENT = 'ACHIEVEMENT',
    ENUM_HEAD = 'ENUM_HEAD',
    ENUM_ENTRY = 'ENUM_ENTRY',
    ROOT = 'ROOT',
    COMPETENCE_HEAD = 'COMPETENCE_HEAD',
    TASK_HEAD = 'TASK_HEAD',
    FUNCTION_HEAD = 'FUNCTION_HEAD',
    ACHIEVEMENT_HEAD = 'ACHIEVEMENT_HEAD',

    FLEXPROD = 'FLEXPROD',
    FLEXPROD_COLLECTOR = 'FLEXPROD_COLLECTOR'
}
export namespace ClassArchetype {

    export function getClassArchetypeLabel(classArchetype: ClassArchetype) {
        switch (classArchetype) {
            case 'COMPETENCE': return 'Competence';
            case 'TASK': return 'Task';
            case 'FUNCTION': return 'Function';
            case 'ACHIEVEMENT': return 'Achievement';
            case 'ENUM_HEAD': return 'Enum head';
            case 'ENUM_ENTRY': return 'Enum entry';
            case 'ROOT': return 'Root';
            case 'FLEXPROD': return 'Flexprod';
            case 'FLEXPROD_COLLECTOR': return 'Flexprod';
        }
    }
}

