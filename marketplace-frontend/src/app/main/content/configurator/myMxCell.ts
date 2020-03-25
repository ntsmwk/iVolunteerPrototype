import { mxgraph } from 'mxgraph';
import { ClassArchetype } from '../_model/meta/Class';
import { MatchingOperatorType } from '../_model/matching';

export class myMxCell extends mxgraph.mxCell {
  cellType?: string;
  classArchetype?: ClassArchetype;
  matchingOperatorType?: MatchingOperatorType;

  root?: boolean;
  property: boolean;
  propertyId?: string;
  newlyAdded: boolean;

}