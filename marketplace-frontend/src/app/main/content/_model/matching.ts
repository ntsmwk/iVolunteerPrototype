import { ClassDefinition } from './meta/Class';

export class MatchingConfiguratorClassDefinitionCollection {
    collector: ClassDefinition;
    classDefinitions: ClassDefinition[];
    numberOfProperties: number;
    numberOfDefinitions: number;
}

export class MatchingOperatorRelationshipStorage {
    id: string;
    name: string;

    producerConfiguratorId: string;
    consumerConfiguratorId: string;

    producerConfiguratorName: string;
    consumerConfiguratorName: string;

    relationships: MatchingOperatorRelationship[];
}

export class MatchingOperatorRelationship {
    producerId: string;
    consumerId: string;
    matchingOperatorType: string;

    coordX: number;
    coordY: number;
}
