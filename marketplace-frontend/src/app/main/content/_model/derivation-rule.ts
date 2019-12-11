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
    mappingOperatorType: MappingOperatorType;
    value: any;
}

export enum MappingOperatorType {
    EQ = "=", LT = "<", LE = "<=", GT = ">", GE = ">=", NE = "!="
}
