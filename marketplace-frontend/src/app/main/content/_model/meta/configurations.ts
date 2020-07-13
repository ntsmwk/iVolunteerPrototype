import { MatchingOperatorRelationship, MatchingCollector } from "../matching";

export class ClassConfiguration {
  id: string;
  name: string;
  description: string;
  classDefinitionIds: string[];
  relationshipIds: string[];
  timestamp: Date;
  userId: String;
}

export class MatchingConfiguration {
  id: string;
  name: string;
  timestamp: Date;

  leftClassConfigurationId: string;
  leftClassConfigurationName: string;

  rightClassConfigurationId: string;
  rightClassConfigurationName: string;

  relationships: MatchingOperatorRelationship[];
}

export class MatchingCollectorConfiguration {
  id: string;
  classConfigurationId: String;

  collectors: MatchingCollector[];
}
