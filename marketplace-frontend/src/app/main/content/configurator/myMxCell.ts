import { mxgraph } from 'mxgraph';
import { ClassArchetype } from '../_model/meta/Class';
import { MatchingOperatorType } from '../_model/matching';

export class MyMxCell extends mxgraph.mxCell {
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
  REMOVE_ICON = 'REMOVE_ICON',

  MATCHING_OPERATOR = 'MATCHING_OPERATOR', MATCHING_CONNECTOR = 'MATCHING_CONNECTOR',


}