import { ClassDefinition } from './meta/Class';

export class MatchingClassDefinitionCollection {
    collector: ClassDefinition;
    collectionEntries: MatchingClassDefinitionCollectionEntry[];
    numberOfProperties: number;
    numberOfDefinitions: number;
}

export class MatchingClassDefinitionCollectionEntry {
    path: string;
    classDefinition: ClassDefinition;
    sourceRelationshipId: string;
    targetRelationshipId: string;
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
