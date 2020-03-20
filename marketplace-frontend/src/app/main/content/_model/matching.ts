import { ClassDefinition } from './meta/Class';

export class MatchingCollector {
    classDefinition: ClassDefinition;
    path: string;
    pathDelimiter: string;

    collectorEntries: MatchingCollectorEntry[];
    numberOfProperties: number;
    numberOfDefinitions: number;
}

export class MatchingCollectorEntry {
    classDefinition: ClassDefinition;
    path: string;
    pathDelimiter: string;

    sourceRelationshipId: string;
    targetRelationshipId: string;
}

export class MatchingOperatorRelationship {
    id: string;

    producerPath: string;
    producerType: MatchingProducerConsumerType;

    consumerPath: string;
    consumerType: MatchingProducerConsumerType;

    matchingOperatorType: MatchingOperatorType;

    weighting: number;
    necessary: boolean;
    fuzzyness: number;

    coordX: number;
    coordY: number;
}

export enum MatchingProducerConsumerType {
    PROPERTY = 'PROPERTY',
    CLASS = 'CLASS'
}

export enum MatchingOperatorType {
    EQUAL = 'EQUAL',
    LESS = 'LESS',
    GREATER = 'GREATER',
    LESS_EQUAL = 'LESS_EQUAL',
    GREATER_EQUAL = 'GREATER_EQUAL',
    EXISTS = 'EXISTS',
    ALL = 'ALL'
}
