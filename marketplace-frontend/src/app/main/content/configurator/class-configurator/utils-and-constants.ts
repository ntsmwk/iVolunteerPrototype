import { ClassDefinition, ClassArchetype } from '../../_model/meta/Class';
import { Relationship, RelationshipType } from '../../_model/meta/Relationship';
import { ObjectIdService } from '../../_service/objectid.service.';
import { isNullOrUndefined } from 'util';

const sidebarPalettes = {
  id: 'building_blocks', label: 'Building Blocks',
  rows: [
    {
      c0: { id: 'competence', label: 'Competence', imgPath: '/assets/icons/class_editor/competence.png', type: 'class', archetype: ClassArchetype.COMPETENCE, shape: undefined },
      c1: { id: 'task', label: 'Task', imgPath: '/assets/icons/class_editor/task.png', type: 'class', archetype: ClassArchetype.TASK, shape: undefined },
    }, {
      c0: { id: 'function', label: 'Function', imgPath: '/assets/icons/class_editor/function.png', type: 'class', archetype: ClassArchetype.FUNCTION, shape: undefined },
      c1: { id: 'achievement', label: 'Achievement', imgPath: '/assets/icons/class_editor/achievement.png', type: 'class', archetype: ClassArchetype.ACHIEVEMENT, shape: undefined },
    }
  ]
};

const relationshipPalettes = {
  id: 'relationships', label: 'Relationships',
  rows: [
    { id: 'INHERITANCE', label: 'Inheritance', imgPath: '/assets/mxgraph_resources/images/custom/inheritance.svg', type: 'inheritance', shape: undefined },
    { id: 'ASSOCIATION', label: 'Association', imgPath: '/assets/mxgraph_resources/images/custom/association.svg', type: 'association', shape: undefined },
    { id: 'AGGREGATION', label: 'Aggregation', imgPath: '', type: 'aggregation', shape: undefined }
  ]
};

const matchingOperatorPalettes = [
  { id: 'EQUAL', label: 'gleich', imgPath: '/assets/icons/class_editor/matching/equal_reduced.png', type: 'matchingOperator' },
  { id: 'LESS', label: 'kleiner', imgPath: '/assets/icons/class_editor/matching/lt_reduced.png', type: 'matchingOperator' },
  { id: 'GREATER', label: 'größer', imgPath: '/assets/icons/class_editor/matching/gt_reduced.png', type: 'matchingOperator' },
  { id: 'LESS_EQUAL', label: 'kleiner gleich', imgPath: '/assets/icons/class_editor/matching/lteq_reduced.png', type: 'matchingOperator' },
  { id: 'GREATER_EQUAL', label: 'größer gleich', imgPath: '/assets/icons/class_editor/matching/gteq_reduced.png', type: 'matchingOperator' },
  { id: 'EXISTS', label: 'existiert', imgPath: '/assets/icons/class_editor/matching/exists_reduced.png', type: 'matchingOperator' },
  { id: 'ALL', label: 'alle', imgPath: '/assets/icons/class_editor/matching/all_reduced.png', type: 'matchingOperator' },
];

const matchingConnectorPalettes = [
  { id: 'connector', label: 'verbinder', imgPath: '/assets/mxgraph_resources/images/connect.gif', type: 'connector' },

];

const deleteOperationPalette =
  { id: 'delete', label: 'löschen', imgPath: '/assets/mxgraph_resources/images/delete.gif', type: 'operation' }

  ;

const mxStyles = {

  // Classes
  // classNormal: 'shape=swimlane;resizable=0;' + 'fontColor=#000e8a;strokeColor=#000e8a;',
  // classEnum: 'shape=swimlane;resizable=0;' + 'fillColor=#FFCC99;fontColor=#B05800;strokeColor=#B05800;' + 'portConstraint=north',
  // classFlexprodCollector: 'shape=swimlane;resizable=0;' + 'fillColor=#ffbdbd;fontColor=#700000;strokeColor=#700000;',
  classNormal: 'editable=0;' + 'shape=swimlane;resizable=0;' + 'fillColor=#000e8a;strokeColor=#000e8a;fontColor=#FFFFFF;fontSize=14;',
  classEnum: 'shape=swimlane;resizable=0;' + 'fillColor=#B05800;fontColor=#FFFFFF;strokeColor=#B05800;fontSize=14;' + 'portConstraint=north;',
  classFlexprodCollector: 'editable=0;' + 'shape=swimlane;resizable=0;' + 'fillColor=#700000;fontColor=#FFFFFF;strokeColor=#700000;fontSize=14;',

  // Fillers
  // classVfiller: 'fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  // classHfiller: 'fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',

  // property: 'movable=0;resizable=0;editable=0;deletable=0;selectable=1;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left;html=1;overflow=hidden',
  property: 'movable=0;resizable=0;editable=0;deletable=0;selectable=1;fillColor=rgb(54,115,41);fontColor=#FFFFFF;strokeColor=#FFFFFF;align=left;html=1;overflow=hidden;fontSize=14;',
  propertyEnum: 'movable=0;resizable=0;editable=0;deletable=0;selectable=1;fillColor=#FFCC99;fontColor=#B05800;strokeColor=#B05800;align=left;html=1;overflow=hidden;' + 'portConstraint=eastwest',

  // Icons
  // addIcon: 'shape=image;image=/assets/mxgraph_resources/images/add_green.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  // removeIcon: 'shape=image;image=/assets/mxgraph_resources/images/remove_red.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  optionsIcon: 'shape=image;image=/assets/icons/class_editor/options_icon.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',

  addClassSameLevelIcon: 'shape=image;image=/assets/mxgraph_resources/images/right_blue.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassNewLevelIcon: 'shape=image;image=/assets/mxgraph_resources/images/down_blue.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  // addClassNewLevelAssociationIcon: 'shape=image;image=/assets/mxgraph_resources/images/letter-a-icon.jpg;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',


  // Relationships
  // inheritance: 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1;',
  inheritanceEnum: 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1;strokeColor=#B05800',

  inheritance: 'sideToSideEdgeStyle=1;endArrow=none;startArrow=block;startSize=16;startFill=0;curved=1;html=1;strokeColor=#000e8a;',



  association: 'endArrow=none;html=1;curved=1;' + 'edgeStyle=orthogonalEdgeStyle;',
  associationCell: 'resizable=0;html=1;align=left;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;',

  aggregation: 'endArrow=none;html=1;startArrow=diamondThin;startSize=15;startFill=0;exitPerimeter=1;' +
    'strokeColor=#000e8a;' + 'curved=1;' + 'edgeStyle=orthogonalEdgeStyle;',

  composition: 'endArrow=none;html=1;startArrow=diamondThin;startSize=15;startFill=1;exitPerimeter=1;' +
    'strokeColor=#000e8a;' + 'curved=1;' + 'edgeStyle=orthogonalEdgeStyle;',

  // Matching

  matchingRowHeader: 'movable=0;resizable=0;editable=0;deletable=0;selectable=0;' +
    'fillColor=#000000;fontColor=#FFFFFF;strokeColor=#000000;' +
    'align=center;html=1;overflow=hidden;fontSize=30;fontFamily=roboto;fontStyle=bold;',

  matchingConnector: 'endArrow=classic;startArrow=none;html=1;curved=1;' + 'editable=0;selectable=1;deletable=1;' + 'edgeStyle=orthogonalEdgeStyle;',
  matchingOperator: 'resizable=0;editable=0;deletable=1;selectable=1;' + 'portConstraint=eastwest',
  matchingClassSeparator:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;foldable=0;' +
    'fillColor=#000e8a;fontColor=#FFFFFF;strokeColor=#000e8a;align=center;html=1;overflow=hidden;fontSize=14;' + 'portConstraint=eastwest;',
  matchingProperty:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;foldable=0;' +
    'fillColor=rgb(54,115,41);fontColor=#FFFFFF;strokeColor=#FFFFFF;align=left;html=1;overflow=hidden;fontSize=14;' + 'portConstraint=eastwest',
  matchingClassNormal:
    'shape=swimlane;movable=0;resizable=0;editable=0;deletable=0;selectable=0;foldable=0;' +
    'fillColor=#000e8a;strokeColor=#000e8a;fontColor=#FFFFFF;fontSize=14;' + 'portConstraint=eastwest',
  matchingClassFlexprodCollector:
    'shape=swimlane;movable=0;resizable=0;editable=0;deletable=0;selectable=0;foldable=0;' +
    'fillColor=#700000;fontColor=#FFFFFF;strokeColor=#700000;fontSize=14;' + 'portConstraint=eastwest',
};

const cellTypes = {
  property: { label: 'property', icon: '' },
  property_enum: { label: 'property_enum', icon: '' },

  add: { label: 'add', icon: '' },
  add_association: { label: 'property', icon: '' },
  add_class_new_level: {},
  add_class_same_level: {},
  remove: {},

  inheritance: {},
  association: {},
  inheritance_enum: {},
  association_label: {}

};


export class CConstants {
  public static sidebarPalettes = sidebarPalettes;
  public static relationshipPalettes = relationshipPalettes;
  public static matchingOperatorPalettes = matchingOperatorPalettes;
  public static matchingConnectorPalettes = matchingConnectorPalettes;
  public static deleteOperationPalette = deleteOperationPalette;
  public static mxStyles = mxStyles;
  public static cellTypes = cellTypes;
}

export class CUtils {

  public static getStandardObjects(marketplaceId: string, objectIdService: ObjectIdService, rootLabel?: string): { classDefinitions: ClassDefinition[], relationships: Relationship[] } {
    const classDefinitions: ClassDefinition[] = [];
    const relationships: Relationship[] = [];


    const root = new ClassDefinition();
    root.id = objectIdService.getNewObjectId();
    root.marketplaceId = marketplaceId;

    if (!isNullOrUndefined(rootLabel)) {
      root.name = rootLabel;
    } else {
      root.name = '<Maschninen-\nName>';
    }

    root.root = true;
    root.classArchetype = ClassArchetype.ROOT;

    root.properties = [];

    classDefinitions.push(root);

    const flexProdclass = new ClassDefinition();
    flexProdclass.id = objectIdService.getNewObjectId();
    flexProdclass.marketplaceId = marketplaceId;
    flexProdclass.name = '<Komponenten-\nName>';
    flexProdclass.root = false;
    flexProdclass.classArchetype = ClassArchetype.FLEXPROD;

    flexProdclass.properties = [];

    classDefinitions.push(flexProdclass);

    const r1 = new Relationship();
    r1.id = objectIdService.getNewObjectId();
    r1.relationshipType = RelationshipType.AGGREGATION;
    r1.source = root.id;
    r1.target = flexProdclass.id;

    relationships.push(r1);

    return { classDefinitions: classDefinitions, relationships: relationships };

  }
}
