// import { ClassDefinition } from "./meta/class";
// import { MatchingConfiguration, MatchingEntityMappingConfiguration } from './meta/configurations';

// export class MatchingDataRequestDTO {
//   matchingConfiguration: MatchingConfiguration;

//   relationships: MatchingOperatorRelationship[];

//   leftMappingConfigurations: MatchingEntityMappingConfiguration;
//   rightMappingConfigurations: MatchingEntityMappingConfiguration;

//   pathDelimiter: string;
// }

// export class MatchingEntityMappings {
//   // classDefinition: ClassDefinition;
//   // path: string;
//   pathDelimiter: string;

//   entities: MatchingEntity[];

//   numberOfProperties: number;
//   numberOfDefinitions: number;
// }

// export class MatchingEntity {
//   classDefinition: ClassDefinition;
//   path: string;
//   pathDelimiter: string;

//   sourceRelationshipId: string;
//   targetRelationshipId: string;
// }

// export class MatchingOperatorRelationship {
//   id: string;
//   tenantId: string;
//   matchingConfigurationId: string;

//   leftMatchingEntityPath: string;
//   leftMatchingEntityType: MatchingEntityType;

//   rightMatchingEntityPath: string;
//   rightMatchingEntityType: MatchingEntityType;

//   matchingOperatorType: MatchingOperatorType;

//   weighting: number;
//   necessary: boolean;
//   fuzzyness: number;

//   coordX: number;
//   coordY: number;
// }

// export enum MatchingEntityType {
//   PROPERTY = "PROPERTY",
//   CLASS = "CLASS"
// }

// export enum MatchingOperatorType {
//   EQUAL = "EQUAL",
//   LESS = "LESS",
//   GREATER = "GREATER",
//   LESS_EQUAL = "LESS_EQUAL",
//   GREATER_EQUAL = "GREATER_EQUAL",
//   EXISTS = "EXISTS",
//   ALL = "ALL"
// }

// export namespace MatchingOperatorType {
//   export function getLabelForMatchingOperatorType(
//     matchingOperatorType: string
//   ) {
//     switch (matchingOperatorType) {
//       case MatchingOperatorType.EQUAL:
//         return "gleich";
//       case MatchingOperatorType.LESS:
//         return "kleiner";
//       case MatchingOperatorType.GREATER:
//         return "größer";
//       case MatchingOperatorType.LESS_EQUAL:
//         return "kleiner oder gleich";
//       case MatchingOperatorType.GREATER_EQUAL:
//         return "größer oder gleich";
//       case MatchingOperatorType.EXISTS:
//         return "existiert";
//       case MatchingOperatorType.ALL:
//         return "alle";
//     }
//   }
// }
