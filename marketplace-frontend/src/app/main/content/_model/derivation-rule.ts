// import { ClassDefinition } from "./meta/class";
// import { FlatPropertyDefinition, ClassProperty } from './meta/property/property';

// export class DerivationRule {
//   id: string;
//   tenantId: string;
//   marketplaceId: string;
//   name: string;
//   container: string;
//   generalConditions: GeneralCondition[];/*  */
//   conditions: ClassCondition[];
//   classActions: ClassAction[];
//   active: boolean;
// }

// export class GeneralCondition {
//   propertyDefinition: FlatPropertyDefinition<any>;
//   comparisonOperatorType: ComparisonOperatorType;
//   value: any;

//   private retrieveComparisonOperatorValueOf(op) {
//     let x: ComparisonOperatorType =
//       ComparisonOperatorType[op as keyof typeof ComparisonOperatorType];
//     return x;
//   }

// }

// export class AttributeCondition {

//   classDefinition: ClassDefinition;
//   classProperty: ClassProperty<any>;
//   comparisonOperatorType: ComparisonOperatorType;
//   value: any;

//   constructor(classDefinition: ClassDefinition) {
//     this.classDefinition = classDefinition;
//   }

//   private retrieveComparisonOperatorValueOf(op) {
//     let x: ComparisonOperatorType =
//       ComparisonOperatorType[op as keyof typeof ComparisonOperatorType];
//     return x;
//   }
// }

// export class ClassCondition {
//   classDefinition: ClassDefinition;
//   attributeConditions: AttributeCondition[] = [];
//   aggregationOperatorType: AggregationOperatorType;
//   value: any;
//   classProperty: ClassProperty<any>;
// }

// /*export class AttributeTarget{
//   classDefinition: ClassDefinition;
//   classProperty: ClassProperty<any>;
//   value: any;

//   constructor(classDefinition: ClassDefinition){
//     this.classDefinition = classDefinition;
//   }
// }*/

// export class ClassAction {
//   actionType: ActionType;
//   classDefinition: ClassDefinition;
//   attributes: AttributeCondition[] = [];

//   constructor(classDefinition: ClassDefinition) {
//     //this.classDefinition = classDefinition;
//     this.actionType = ActionType.NEW;
//   }
// }

// export enum ComparisonOperatorType {
//   EQ = "=",
//   LT = "<",
//   LE = "<=",
//   GT = ">",
//   GE = ">=",
//   NE = "!=",
// }

// export enum AggregationOperatorType {
//   COUNT = "Anzahl",
//   EXISTS = "Existiert",
//   NOT_EXISTS = "Existiert nicht",
//   MIN = "Min",
//   MAX = "Max",
//   SUM = "Summe"
// }

// export enum ActionType {
//   NEW, UPDATE
// }
