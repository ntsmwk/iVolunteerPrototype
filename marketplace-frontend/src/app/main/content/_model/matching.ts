import { ClassDefinition } from './meta/Class';

export class MatchingCollectorConfig {
    classDefinition: ClassDefinition;
    path: string;
    pathDelimiter: string;

    collectorEntries: MatchingCollectorConfigEntry[];
    numberOfProperties: number;
    numberOfDefinitions: number;
}

export class MatchingCollectorConfigEntry {
    classDefinition: ClassDefinition;
    path: string;
    pathDelimiter: string;

    sourceRelationshipId: string;
    targetRelationshipId: string;
}

export class MatchingConfigurator {
    id: string;
    name: string;

    producerClassConfiguratorId: string;
    consumerClassConfiguratorId: string;

    producerClassConfiguratorName: string;
    consumerClassConfiguratorName: string;

    relationships: MatchingOperatorRelationship[];
}

export class MatchingOperatorRelationship {
    producerId: string;
    consumerId: string;
    matchingOperatorType: string;

    coordX: number;
    coordY: number;
}