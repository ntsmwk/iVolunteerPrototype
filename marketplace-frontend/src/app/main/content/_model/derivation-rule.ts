import { ClassDefinition } from './meta/Class';
import { ClassProperty } from './meta/Property';

export class DerivationRule {
  id: string;
  tenantId: string;
  marketplaceId: string;
  name: string;
  attributeSourceRules: AttributeSourceRuleEntry[];
  classSourceRules: ClassSourceRuleEntry[];
  target: ClassDefinition;
}

export class AttributeSourceRuleEntry {
  classDefinition: ClassDefinition;
  classProperty: ClassProperty<any>;
  aggregationOperatorType: AttributeAggregationOperatorType;
  mappingOperatorType: MappingOperatorType;
  value: any;
}

export class ClassSourceRuleEntry {
  classDefinition: ClassDefinition;
  aggregationOperatorType: ClassAggregationOperatorType;
  mappingOperatorType: MappingOperatorType;
  value: any;
}

export enum MappingOperatorType {
  EQ = "=", LT = "<", LE = "<=", GT = ">", GE = ">=", NE = "!="
}

export enum AttributeAggregationOperatorType {
  SUM = "Summe", MIN = "Min", MAX = "Max"
}


export enum ClassAggregationOperatorType {
  COUNT = "Anzahl"
}
