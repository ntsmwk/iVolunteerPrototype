import { ClassProperty, PropertyInstance } from "./property/property";

export class ClassDefinition {
  id: string;
  tenantId: string;
  configurationId: string;
  parentId: string;
  root: boolean;
  name: string;

  classArchetype: ClassArchetype;

  writeProtected: boolean;

  properties: ClassProperty<any>[] = [];

  marketplaceId: string;
  timestamp: Date;

  imagePath: string;

  visible: boolean;
  tabId: number;
}

export class CompetenceClassDefinition extends ClassDefinition {}

export class ClassInstance {
  id: string;
  classDefinitionId: string;
  name: string;
  properties: PropertyInstance<any>[];
  userId: string;
  issuerId: string;
  published: boolean;
  inUserRepository: boolean;
  inIssuerInbox: boolean;
  classArchetype: ClassArchetype;
  tenantId: string;
  timestamp: Date;

  marketplaceId: string;
  imagePath: string;

  childClassInstances: ClassInstance[];

  visible: boolean;
  tabId: number;

  constructor(
    classDefinition: ClassDefinition,
    properties?: PropertyInstance<any>[]
  ) {
    this.name = classDefinition.name;
    this.classDefinitionId = classDefinition.id;
    this.properties = properties;
    this.marketplaceId = classDefinition.marketplaceId;
    this.timestamp = new Date();
    this.userId = null;
    this.classArchetype = classDefinition.classArchetype;
    this.visible = classDefinition.visible;
    this.tabId = classDefinition.tabId;
  }
}

export class ClassInstanceDTO {
  name: string;
  id: string;
  marketplaceId: string;
  tenantId: string;
  issuerId: string;
  blockchainDate: Date;
  classArchetype: ClassArchetype;
  purpose: string;
  dateFrom: Date;
  dateTo: Date;
  location: string;
  description: string;
  duration: number;
  rank: string;
  taskType1: string;
  taskType2: string;
  taskType3: string;
  hash: string;
  imagePath: string;
  timestamp: Date;
  published: boolean;
}

export class CompetenceClassInstance extends ClassInstance {
  constructor(
    classDefintion: ClassDefinition,
    properties: PropertyInstance<any>[]
  ) {
    super(classDefintion, properties);
    this.classArchetype = ClassArchetype.COMPETENCE;
  }
}

export class TaskClassInstance extends ClassInstance {
  constructor(
    classDefintion: ClassDefinition,
    properties: PropertyInstance<any>[]
  ) {
    super(classDefintion, properties);
    this.classArchetype = ClassArchetype.TASK;
  }
}

export class FunctionClassInstance extends ClassInstance {
  constructor(
    classDefintion: ClassDefinition,
    properties: PropertyInstance<any>[]
  ) {
    super(classDefintion, properties);
    this.classArchetype = ClassArchetype.FUNCTION;
  }
}

export class AchievementClassInstance extends ClassInstance {
  constructor(
    classDefintion: ClassDefinition,
    properties: PropertyInstance<any>[]
  ) {
    super(classDefintion, properties);
    this.classArchetype = ClassArchetype.ACHIEVEMENT;
  }
}

export enum ClassArchetype {
  COMPETENCE = "COMPETENCE",
  TASK = "TASK",
  FUNCTION = "FUNCTION",
  ACHIEVEMENT = "ACHIEVEMENT",
  TREE_HEAD = "TREE_HEAD",
  TREE_ENTRY = "TREE_ENTRY",
  ROOT = "ROOT",
  // COMPETENCE_HEAD = 'COMPETENCE_HEAD',
  // TASK_HEAD = 'TASK_HEAD',
  // FUNCTION_HEAD = 'FUNCTION_HEAD',
  // ACHIEVEMENT_HEAD = 'ACHIEVEMENT_HEAD',

  FLEXPROD = "FLEXPROD"
}

export namespace ClassArchetype {
  export function getClassArchetypeLabel(classArchetype: ClassArchetype) {
    switch (classArchetype) {
      case "COMPETENCE":
        return "Kompetenz";
      case "TASK":
        return "Tätigkeit";
      case "FUNCTION":
        return "Funktion";
      case "ACHIEVEMENT":
        return "Verdienst";
      case "TREE_HEAD":
        return "Tree head";
      case "TREE_ENTRY":
        return "Tree entry";
      case "ROOT":
        return "Root";
      case "FLEXPROD":
        return "Flexprod";
    }
  }
}
