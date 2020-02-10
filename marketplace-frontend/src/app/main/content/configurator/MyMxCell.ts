import { mxgraph } from 'mxgraph';
import { ClassArchetype } from '../_model/meta/Class';

export class myMxCell extends mxgraph.mxCell {
  cellType?: string;
  classArchetype?: ClassArchetype;
  operatorType?: string;

  root?: boolean;
  property: boolean;
  propertyId?: string;
  newlyAdded: boolean;

}