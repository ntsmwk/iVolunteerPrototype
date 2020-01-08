import { ClassDefinition } from './meta/Class';
import { PropertyDefinition, ClassProperty } from './meta/Property';

export class DerivationRule {
    id: string;
    marketplaceId: string;
    name: string;
    sources: SourceRuleEntry[];
    target: ClassDefinition;
}

export class SourceRuleEntry {
    classDefinition: ClassDefinition;
    classProperty: ClassProperty<any>;
    aggregationOperatorType: AggregationOperatorType;
    mappingOperatorType: MappingOperatorType;
    value: any;
}

export enum MappingOperatorType {
    EQ = "=", LT = "<", LE = "<=", GT = ">", GE = ">=", NE = "!="
}

export enum AggregationOperatorType {
    COUNT = "Anzahl", SUM = "Summe", MIN = "Min", MAX = "Max"
}
