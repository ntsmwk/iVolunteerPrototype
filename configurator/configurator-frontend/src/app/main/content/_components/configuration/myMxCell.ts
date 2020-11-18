import { mxgraph } from 'mxgraph';
import { ClassArchetype } from '../../_model/configurator/class';
import { MatchingOperatorType } from '../../_model/matching';

declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

export class MyMxCell extends mx.mxCell {
  cellType?: MyMxCellType;
  writeProtected: boolean;

  classArchetype?: ClassArchetype;
  matchingOperatorType?: MatchingOperatorType;

  root?: boolean;

  // propertyClass: 'FLAT' | 'TREE';
  // propertyId?: string;
}

export enum MyMxCellType {

  CLASS = 'CLASS',

  INHERITANCE = 'INHERITANCE', ASSOCIATION = 'ASSOCIATION', AGGREGATION = 'AGGREGATION', COMPOSITION = 'COMPOSITION',

  ASSOCIATION_LABEL = 'ASSOCIATION_LABEL',

  FLAT_PROPERTY = 'FLAT_PROPERTY', TREE_PROPERTY = 'TREE_PROPERTY',

  ADD_PROPERTY_ICON = 'ADD_PROPERTY_ICON', ADD_ASSOCIATION_ICON = 'ADD_ASSOCIATION_ICON',
  ADD_CLASS_SAME_LEVEL_ICON = 'ADD_CLASS_SAME_LEVEL_ICON', ADD_CLASS_NEXT_LEVEL_ICON = 'ADD_CLASS_NEXT_LEVEL_ICON',
  REMOVE_ICON = 'REMOVE_ICON', OPTIONS_ICON = 'OPTIONS_ICON',

  ADD_CLASS_BUTTON = 'ADD_CLASS_BUTTON',


  MATCHING_OPERATOR = 'MATCHING_OPERATOR', MATCHING_CONNECTOR = 'MATCHING_CONNECTOR',

  TREE_ENTRY = 'TREE_ENTRY', TREE_CONNECTOR = 'TREE_CONNECTOR', TREE_HEAD = 'TREE_HEAD',

}

export namespace MyMxCellType {
  export function isRelationship(s: string) {
    return (s === MyMxCellType.INHERITANCE || s === MyMxCellType.ASSOCIATION || s === MyMxCellType.COMPOSITION || s === MyMxCellType.AGGREGATION);
  }
}