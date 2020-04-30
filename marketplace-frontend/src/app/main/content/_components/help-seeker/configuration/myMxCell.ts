import { mxgraph } from 'mxgraph';
import { ClassArchetype } from '../../../_model/meta/class';
import { MatchingOperatorType } from '../../../_model/matching';

declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

export class MyMxCell extends mx.mxCell {
  cellType?: MyMxCellType;
  classArchetype?: ClassArchetype;
  matchingOperatorType?: MatchingOperatorType;

  root?: boolean;
  property: boolean;
  propertyId?: string;
  newlyAdded: boolean;

}

export enum MyMxCellType {

  CLASS = 'CLASS',

  INHERITANCE = 'INHERITANCE', ASSOCIATION = 'ASSOCIATION', AGGREGATION = 'AGGREGATION', COMPOSITION = 'COMPOSITION',

  ASSOCIATION_LABEL = 'ASSOCIATION_LABEL',

  PROPERTY = 'PROPERTY', ENUM_PROPERTY = 'ENUM_PROPERTY',

  ADD_PROPERTY_ICON = 'ADD_PROPERTY_ICON', ADD_ASSOCIATION_ICON = 'ADD_ASSOCIATION_ICON',
  ADD_CLASS_SAME_LEVEL_ICON = 'ADD_CLASS_SAME_LEVEL_ICON', ADD_CLASS_NEXT_LEVEL_ICON = 'ADD_CLASS_NEXT_LEVEL_ICON',
  REMOVE_ICON = 'REMOVE_ICON', OPTIONS_ICON = 'OPTIONS_ICON',

  MATCHING_OPERATOR = 'MATCHING_OPERATOR', MATCHING_CONNECTOR = 'MATCHING_CONNECTOR',


}

export namespace MyMxCellType {
  export function isRelationship(s: string) {
    return (s === MyMxCellType.INHERITANCE || s === MyMxCellType.ASSOCIATION || s === MyMxCellType.COMPOSITION || s === MyMxCellType.AGGREGATION);
  }
}