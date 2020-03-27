import { MatchingOperatorRelationship, MatchingCollector } from './matching';

export class ClassConfiguration {
    id: string;
    name: string;
    description: string;
    classDefinitionIds: string[];
    relationshipIds: string[];
    date: Date;
    userId: String;

}

export class MatchingConfiguration {
    id: string;
    name: string;
    timestamp: Date;

    producerClassConfigurationId: string;
    consumerClassConfigurationId: string;

    producerClassConfigurationName: string;
    consumerClassConfigurationName: string;

    relationships: MatchingOperatorRelationship[];
}

export class MatchingCollectorConfiguration {
    id: string;
    classConfigurationId: String;

    collectors: MatchingCollector[];
}
