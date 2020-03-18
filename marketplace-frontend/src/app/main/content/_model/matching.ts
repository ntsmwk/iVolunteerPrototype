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
    producerId: string;
    consumerId: string;
    matchingOperatorType: string;

    coordX: number;
    coordY: number;
}
