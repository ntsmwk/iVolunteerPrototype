import { ClassDefinition } from './meta/Class';
import { PropertyDefinition } from './meta/Property';

export class DerivationRule{
    id: string;
    marketplaceId: string;
    name: string;
    sources: SourceRuleEntry[];
    targets: ClassDefinition[];
}

export class SourceRuleEntry {
    classDefinition: ClassDefinition;
    propertyDefinition: PropertyDefinition<any>;
    mappingOperatorType: MappingOperatorType;
    value: any;
}

export enum MappingOperatorType{
    EQ, LT, LE, GT, GE, NE
}
