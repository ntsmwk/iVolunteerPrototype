import { ClassDefinition } from './meta/Class';

export class DerivationRule{
    id: string;
    marketplaceId: string;
    name: string;
    sources: SourceRuleEntry[];
    targets: ClassDefinition[];
}

export class SourceRuleEntry {
    classDefinition: ClassDefinition;
    mappingOperator: MappingOperator;
}

export class MappingOperator{ 
    mappingOperatorType: MappingOperatorType;
    value: string;
}

export enum MappingOperatorType{
    COUNT
}
