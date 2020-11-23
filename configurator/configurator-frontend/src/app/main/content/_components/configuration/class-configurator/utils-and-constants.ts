import { RelationshipType } from 'app/main/content/_model/configurator/relationship';
import { MatchingOperatorType } from 'app/main/content/_model/matching';
import { PropertyType } from 'app/main/content/_model/configurator/property/property';

const relationshipPalettes = {
  id: 'relationships',
  label: 'Relationships',
  rows: [
    {
      id: RelationshipType.INHERITANCE,
      label: RelationshipType.getLabelFromRelationshipType(
        RelationshipType.INHERITANCE
      ),
      imgPath: '/assets/icons/class_editor/relationships/inheritance.png',
      type: RelationshipType.INHERITANCE,
      shape: undefined,
    },
    {
      id: RelationshipType.ASSOCIATION,
      label: RelationshipType.getLabelFromRelationshipType(
        RelationshipType.ASSOCIATION
      ),
      imgPath: '/assets/icons/class_editor/relationships/aggregation.png',
      type: RelationshipType.ASSOCIATION,
      shape: undefined,
    },
  ],
};

const matchingOperatorPalettes = [
  {
    id: MatchingOperatorType.EQUAL,
    label: MatchingOperatorType.getLabelForMatchingOperatorType(
      MatchingOperatorType.EQUAL
    ),
    imgPath: '/assets/icons/class_editor/matching/equal_reduced.png',
    type: 'matchingOperator',
  },
  {
    id: MatchingOperatorType.LESS,
    label: MatchingOperatorType.getLabelForMatchingOperatorType(
      MatchingOperatorType.LESS
    ),
    imgPath: '/assets/icons/class_editor/matching/lt_reduced.png',
    type: 'matchingOperator',
  },
  {
    id: MatchingOperatorType.GREATER,
    label: MatchingOperatorType.getLabelForMatchingOperatorType(
      MatchingOperatorType.GREATER
    ),
    imgPath: '/assets/icons/class_editor/matching/gt_reduced.png',
    type: 'matchingOperator',
  },
  {
    id: MatchingOperatorType.LESS_EQUAL,
    label: MatchingOperatorType.getLabelForMatchingOperatorType(
      MatchingOperatorType.LESS_EQUAL
    ),
    imgPath: '/assets/icons/class_editor/matching/lteq_reduced.png',
    type: 'matchingOperator',
  },
  {
    id: MatchingOperatorType.GREATER_EQUAL,
    label: MatchingOperatorType.getLabelForMatchingOperatorType(
      MatchingOperatorType.GREATER_EQUAL
    ),
    imgPath: '/assets/icons/class_editor/matching/gteq_reduced.png',
    type: 'matchingOperator',
  },
  {
    id: MatchingOperatorType.EXISTS,
    label: MatchingOperatorType.getLabelForMatchingOperatorType(
      MatchingOperatorType.EXISTS
    ),
    imgPath: '/assets/icons/class_editor/matching/exists_reduced.png',
    type: 'matchingOperator',
  },
  {
    id: MatchingOperatorType.ALL,
    label: MatchingOperatorType.getLabelForMatchingOperatorType(
      MatchingOperatorType.ALL
    ),
    imgPath: '/assets/icons/class_editor/matching/all_reduced.png',
    type: 'matchingOperator',
  },
];

const matchingConnectorPalettes = [
  {
    id: 'connector',
    label: 'verbinden',
    imgPath: '/assets/mxgraph_resources/images/connect.gif',
    type: 'connector',
  },
];

const deleteOperationPalette = {
  id: 'delete',
  label: 'löschen',
  imgPath: '/assets/mxgraph_resources/images/delete.gif',
  type: 'operation',
};

const propertyTypePalettes = [
  {
    id: PropertyType.TEXT,
    label: PropertyType.getLabelForPropertyType(PropertyType.TEXT),
    imgPath: '/assets/icons/datatypes/s.png',
  },
  {
    id: PropertyType.LONG_TEXT,
    label: PropertyType.getLabelForPropertyType(PropertyType.LONG_TEXT),
    imgPath: '/assets/icons/datatypes/s.png',
  },
  {
    id: PropertyType.WHOLE_NUMBER,
    label: PropertyType.getLabelForPropertyType(PropertyType.WHOLE_NUMBER),
    imgPath: '/assets/icons/datatypes/n.png',
  },
  {
    id: PropertyType.FLOAT_NUMBER,
    label: PropertyType.getLabelForPropertyType(PropertyType.FLOAT_NUMBER),
    imgPath: '/assets/icons/datatypes/f.png',
  },
  {
    id: PropertyType.BOOL,
    label: PropertyType.getLabelForPropertyType(PropertyType.BOOL),
    imgPath: '/assets/icons/datatypes/tf2.png',
  },
  {
    id: PropertyType.DATE,
    label: PropertyType.getLabelForPropertyType(PropertyType.DATE),
    imgPath: '/assets/icons/datatypes/d.png',
  },
  {
    id: PropertyType.LIST,
    label: PropertyType.getLabelForPropertyType(PropertyType.LIST),
    imgPath: '/assets/icons/datatypes/o.png',
  },
  {
    id: PropertyType.TREE,
    label: PropertyType.getLabelForPropertyType(PropertyType.TREE),
    imgPath: '/assets/icons/datatypes/null.png',
  },
  {
    id: PropertyType.LOCATION,
    label: PropertyType.getLabelForPropertyType(PropertyType.LOCATION),
    imgPath: '/assets/icons/datatypes/null.png',
  },

];

const mxStyles = {
  // Classes
  classNormal:
    'editable=0;' +
    'shape=swimlane;resizable=0;' +
    'fillColor=#000e8a;strokeColor=#000e8a;fontColor=#FFFFFF;fontSize=14;',
  classTree:
    'shape=swimlane;resizable=0;foldable=0;' +
    'fillColor=#B05800;fontColor=#FFFFFF;strokeColor=#B05800;fontSize=14;' +
    'portConstraint=north;',
  classFlexprodCollector:
    'editable=0;' +
    'shape=swimlane;resizable=0;' +
    'fillColor=#700000;fontColor=#FFFFFF;strokeColor=#700000;fontSize=14;',
  classHighlighted:
    'editable=0;resizable=0;selectable=0;foldable=0;' +
    'shape=swimlane;' +
    'fillColor=#700000;fontColor=#FFFFFF;strokeColor=#700000;fontSize=14;',
  classDisabled:
    'editable=0;resizable=0;selectable=0;foldable=0;' +
    'shape=swimlane;' +
    'fillColor=#707070;fontColor=#FFFFFF;strokeColor=#707070;fontSize=14;',


  property:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;' +
    'fillColor=rgb(54,115,41);fontColor=#FFFFFF;strokeColor=#FFFFFF;align=left;html=1;overflow=hidden;fontSize=14;',
  propertyTree:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;' +
    'fillColor=#B05800;fontColor=#FFFFFF;strokeColor=#FFFFFF;fontSize=14;align=left;html=1;overflow=hidden;' +
    'portConstraint=eastwest',

  // Icons
  optionsIcon:
    'shape=image;image=/assets/icons/class_editor/options_icon.png;noLabel=1;imageBackground=none;imageBorder=none;' +
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassSameLevelIcon:
    'shape=image;image=/assets/mxgraph_resources/images/right_blue.png;noLabel=1;imageBackground=none;imageBorder=none;' +
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassNewLevelIcon:
    'shape=image;image=/assets/mxgraph_resources/images/down_blue.png;noLabel=1;imageBackground=none;imageBorder=none;' +
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassPlusIcon:
    'shape=image;image=/assets/icons/class_editor/plus_icon.png;noLabel=1;imageBackground=none;imageBorder=none;' +
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;',

  // Relationships
  inheritanceTree:
    'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1;strokeColor=#B05800',
  inheritance:
    'sideToSideEdgeStyle=1;endArrow=none;startArrow=block;startSize=16;startFill=0;curved=1;html=1;strokeColor=#000e8a;',
  genericConnection:
    'sideToSideEdgeStyle=1;endArrow=none;startArrow=none;curved=1;html=1;strokeColor=#000e8a;',

  association:
    'endArrow=none;html=1;curved=1;' + 'edgeStyle=orthogonalEdgeStyle;',
  associationCell:
    'resizable=0;html=1;align=left;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;',
  aggregationAssocation:
    'endArrow=none;html=1;startArrow=diamondThin;startSize=15;startFill=0;exitPerimeter=1;' +
    'strokeColor=#000e8a;' +
    'curved=1;' +
    'edgeStyle=orthogonalEdgeStyle;',
  composition:
    'endArrow=none;html=1;startArrow=diamondThin;startSize=15;startFill=1;exitPerimeter=1;' +
    'strokeColor=#000e8a;' +
    'curved=1;' +
    'edgeStyle=orthogonalEdgeStyle;',

  // Matching

  matchingRowHeader:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;' +
    'fillColor=#000000;fontColor=#FFFFFF;strokeColor=#000000;' +
    'align=center;html=1;overflow=hidden;fontSize=30;fontFamily=roboto;fontStyle=bold;',
  matchingAddButton:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;' +
    'fillColor=rgb(62,125,219);fontColor=#FFFFFF;strokeColor=rgb(62,125,219);' +
    'align=center;verticalAlign=top;html=1;overflow=hidden;fontSize=30;fontFamily=roboto;fontStyle=bold;' +
    'rounded=1;',
  matchingConnector:
    'endArrow=classic;startArrow=none;html=1;curved=1;' +
    'editable=0;selectable=1;deletable=1;' +
    'edgeStyle=orthogonalEdgeStyle;',
  matchingOperator:
    'resizable=0;editable=0;deletable=1;selectable=1;' +
    'portConstraint=eastwest',
  matchingClassSeparator:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;foldable=0;' +
    'fillColor=#000e8a;fontColor=#FFFFFF;strokeColor=#000e8a;align=center;html=1;overflow=hidden;fontSize=14;' +
    'portConstraint=eastwest;',
  matchingProperty:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;foldable=0;' +
    'fillColor=rgb(54,115,41);fontColor=#FFFFFF;strokeColor=#FFFFFF;align=left;html=1;overflow=hidden;fontSize=14;' +
    'portConstraint=eastwest',
  matchingClassNormal:
    'shape=swimlane;movable=0;resizable=0;editable=0;deletable=0;selectable=0;foldable=0;' +
    'fillColor=#000e8a;strokeColor=#000e8a;fontColor=#FFFFFF;fontSize=14;' +
    'portConstraint=eastwest',
  matchingClassFlexprodCollector:
    'shape=swimlane;movable=0;resizable=0;editable=0;deletable=0;selectable=0;foldable=0;' +
    'fillColor=#700000;fontColor=#FFFFFF;strokeColor=#700000;fontSize=14;' +
    'portConstraint=eastwest',
};

const cellTypes = {
  property: { label: 'property', icon: '' },
  property_tree: { label: 'property_tree', icon: '' },

  add: { label: 'add', icon: '' },
  add_association: { label: 'property', icon: '' },
  add_class_new_level: {},
  add_class_same_level: {},
  remove: {},

  inheritance: {},
  association: {},
  inheritance_tree: {},
  association_label: {},
};

export class CConstants {
  // public static sidebarPalettes = sidebarPalettes;
  public static relationshipPalettes = relationshipPalettes;
  public static propertyTypePalettes = propertyTypePalettes;
  public static matchingOperatorPalettes = matchingOperatorPalettes;
  public static matchingConnectorPalettes = matchingConnectorPalettes;
  public static deleteOperationPalette = deleteOperationPalette;
  public static mxStyles = mxStyles;
  public static cellTypes = cellTypes;
}

