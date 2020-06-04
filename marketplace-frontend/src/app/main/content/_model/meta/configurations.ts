import { MatchingOperatorRelationship, MatchingCollector } from '../matching';
import { EnumEntry, EnumRelationship } from './enum';

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

export class EnumConfiguration {
    id: string;
    name: string;
    description: string;

    timestamp: Date;

    enumEntries: EnumEntry[];
    enumRelstionships: EnumRelationship[];
}
