// import { MatchingEntityMappings } from "../matching";
import { Relationship } from './relationship';
import { ClassDefinition } from './class';

export class ClassConfiguration {
  id: string;
  name: string;
  description: string;
  classDefinitionIds: string[];
  relationshipIds: string[];
  timestamp: Date;
  userId: string;
  tenantId: string;
}

export class ClassConfigurationDTO {
  classConfiguration: ClassConfiguration;
  classDefinitions: ClassDefinition[];
  relationships: Relationship[];
}

export class MatchingConfiguration {
  id: string;
  name: string;
  timestamp: Date;
  tenantId: string;

  leftSideId: string;
  leftSideName: string;
  leftIsUser: boolean;

  leftAddedClassDefinitionPaths: string[] = [];

  rightSideId: string;
  rightSideName: string;
  rightIsUser: boolean;

  rightAddedClassDefinitionPaths: string[] = [];

  public constructor(init?: Partial<MatchingConfiguration>) {
    Object.assign(this, init);
  }
}

// export class MatchingEntityMappingConfiguration {
//   id: string;
//   classConfigurationId: string;

//   mappings: MatchingEntityMappings;
// }
