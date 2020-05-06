import { ClassDefinition, ClassArchetype, AchievementClassInstance } from '../../../../_model/meta/class';
import {
  Relationship,
  RelationshipType,
  Inheritance,
} from '../../../../_model/meta/relationship';
import { ObjectIdService } from '../../../../_service/objectid.service.';
import { isNullOrUndefined } from 'util';
import { MatchingOperatorType } from '../../../../_model/matching';
import { PropertyType, ClassProperty } from '../../../../_model/meta/property';

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
      type: 'inheritance',
      shape: undefined,
    },
    {
      id: RelationshipType.ASSOCIATION,
      label: RelationshipType.getLabelFromRelationshipType(
        RelationshipType.ASSOCIATION
      ),
      imgPath: '/assets/icons/class_editor/relationships/',
      type: 'association',
      shape: undefined,
    },
    {
      id: RelationshipType.AGGREGATION,
      label: RelationshipType.getLabelFromRelationshipType(
        RelationshipType.AGGREGATION
      ),
      imgPath: '/assets/icons/class_editor/relationships/aggregation.png',
      type: 'aggregation',
      shape: undefined,
    },
    {
      id: RelationshipType.COMPOSITION,
      label: RelationshipType.getLabelFromRelationshipType(
        RelationshipType.COMPOSITION
      ),
      imgPath: '/assets/icons/class_editor/relationships/composition.png',
      type: 'composition',
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
    id: PropertyType.ENUM,
    label: PropertyType.getLabelForPropertyType(PropertyType.ENUM),
    imgPath: '/assets/icons/datatypes/o.png',
  },
];

const mxStyles = {
  // Classes
  classNormal:
    'editable=0;' +
    'shape=swimlane;resizable=0;' +
    'fillColor=#000e8a;strokeColor=#000e8a;fontColor=#FFFFFF;fontSize=14;',
  classEnum:
    'shape=swimlane;resizable=0;' +
    'fillColor=#B05800;fontColor=#FFFFFF;strokeColor=#B05800;fontSize=14;' +
    'portConstraint=north;',
  classFlexprodCollector:
    'editable=0;' +
    'shape=swimlane;resizable=0;' +
    'fillColor=#700000;fontColor=#FFFFFF;strokeColor=#700000;fontSize=14;',

  property:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;' +
    'fillColor=rgb(54,115,41);fontColor=#FFFFFF;strokeColor=#FFFFFF;align=left;html=1;overflow=hidden;fontSize=14;',
  propertyEnum:
    'movable=0;resizable=0;editable=0;deletable=0;selectable=0;' +
    'fillColor=#FFCC99;fontColor=#B05800;strokeColor=#B05800;align=left;html=1;overflow=hidden;' +
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

  // Relationships
  inheritanceEnum:
    'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1;strokeColor=#B05800',
  inheritance:
    'sideToSideEdgeStyle=1;endArrow=none;startArrow=block;startSize=16;startFill=0;curved=1;html=1;strokeColor=#000e8a;',

  association:
    'endArrow=none;html=1;curved=1;' + 'edgeStyle=orthogonalEdgeStyle;',
  associationCell:
    'resizable=0;html=1;align=left;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;',
  aggregation:
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
  property_enum: { label: 'property_enum', icon: '' },

  add: { label: 'add', icon: '' },
  add_association: { label: 'property', icon: '' },
  add_class_new_level: {},
  add_class_same_level: {},
  remove: {},

  inheritance: {},
  association: {},
  inheritance_enum: {},
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

export class CUtils {
  public static getStandardObjects(
    marketplaceId: string,
    tenantId: string,
    objectIdService: ObjectIdService
  ): { classDefinitions: ClassDefinition[]; relationships: Relationship[] } {
    const classDefinitions: ClassDefinition[] = [];
    const relationships: Relationship[] = [];

    const fwPassEintrag = new ClassDefinition();
    fwPassEintrag.id = objectIdService.getNewObjectId();
    fwPassEintrag.tenantId = tenantId;
    fwPassEintrag.marketplaceId = marketplaceId;
    fwPassEintrag.name = 'Freiwilligenpass-\nEintrag';
    fwPassEintrag.root = true;
    fwPassEintrag.collector = true;
    fwPassEintrag.classArchetype = ClassArchetype.ROOT;
    fwPassEintrag.writeProtected = true;

    fwPassEintrag.properties = [];

    const idProperty = new ClassProperty<string>();
    idProperty.name = 'id';
    idProperty.id = objectIdService.getNewObjectId();
    idProperty.type = PropertyType.TEXT;
    fwPassEintrag.properties.push(idProperty);

    const nameProperty = new ClassProperty<string>();
    nameProperty.name = 'name';
    nameProperty.id = objectIdService.getNewObjectId();
    nameProperty.type = PropertyType.TEXT;
    fwPassEintrag.properties.push(nameProperty);

    const evidenceProperty = new ClassProperty<string>();
    evidenceProperty.name = 'evidenz';
    evidenceProperty.id = objectIdService.getNewObjectId();
    evidenceProperty.type = PropertyType.TEXT;
    fwPassEintrag.properties.push(evidenceProperty);

    classDefinitions.push(fwPassEintrag);

    const task = new ClassDefinition();
    task.id = objectIdService.getNewObjectId();
    task.tenantId = tenantId;
    task.marketplaceId = marketplaceId;
    task.name = 'Tätigkeit';
    task.root = false;

    task.classArchetype = ClassArchetype.TASK;
    task.writeProtected = true;

    classDefinitions.push(task);

    const vonProperty = new ClassProperty<Date>();
    vonProperty.name = 'Starting Date';
    vonProperty.id = 'objectIdService.getNewObjectId();';
    vonProperty.type = PropertyType.DATE;
    task.properties.push(vonProperty);

    const bisProperty = new ClassProperty<Date>();
    bisProperty.name = 'End Date';
    bisProperty.id = objectIdService.getNewObjectId();
    bisProperty.type = PropertyType.DATE;
    task.properties.push(bisProperty);

    const r1 = new Inheritance();
    r1.id = objectIdService.getNewObjectId();
    r1.relationshipType = RelationshipType.INHERITANCE;
    r1.target = task.id;
    r1.source = fwPassEintrag.id;
    r1.superClassId = r1.source;
    relationships.push(r1);

    const competence = new ClassDefinition();
    competence.id = objectIdService.getNewObjectId();
    competence.tenantId = tenantId;
    competence.marketplaceId = marketplaceId;
    competence.name = 'Kompetenz';
    competence.root = false;
    competence.classArchetype = ClassArchetype.COMPETENCE;
    competence.writeProtected = true;
    classDefinitions.push(competence);

    const r2 = new Inheritance();
    r2.id = objectIdService.getNewObjectId();
    r2.relationshipType = RelationshipType.INHERITANCE;
    r2.target = competence.id;
    r2.source = fwPassEintrag.id;
    r2.superClassId = r2.source;
    relationships.push(r2);

    const achievement = new ClassDefinition();
    achievement.id = objectIdService.getNewObjectId();
    achievement.tenantId = tenantId;
    achievement.marketplaceId = marketplaceId;
    achievement.name = 'Verdienst';
    achievement.root = false;
    achievement.classArchetype = ClassArchetype.ACHIEVEMENT;
    achievement.writeProtected = true;
    classDefinitions.push(achievement);

    const r3 = new Inheritance();
    r3.id = objectIdService.getNewObjectId();
    r3.relationshipType = RelationshipType.INHERITANCE;
    r3.target = achievement.id;
    r3.source = fwPassEintrag.id;
    r3.superClassId = r3.source;
    relationships.push(r3);

    const funktion = new ClassDefinition();
    funktion.id = objectIdService.getNewObjectId();
    funktion.tenantId = tenantId;
    funktion.marketplaceId = marketplaceId;
    funktion.name = 'Funktion';
    funktion.root = false;
    funktion.classArchetype = ClassArchetype.FUNCTION;
    funktion.writeProtected = true;
    classDefinitions.push(funktion);

    const r4 = new Inheritance();
    r4.id = objectIdService.getNewObjectId();
    r4.relationshipType = RelationshipType.INHERITANCE;
    r4.target = funktion.id;
    r4.source = fwPassEintrag.id;
    r4.superClassId = r4.source;
    relationships.push(r4);

    const myTask = new ClassDefinition();
    myTask.id = objectIdService.getNewObjectId();
    myTask.tenantId = tenantId;
    myTask.marketplaceId = marketplaceId;
    myTask.name = 'myTask';
    myTask.root = false;
    myTask.classArchetype = ClassArchetype.TASK;

    myTask.properties = [];
    const tt1 = new ClassProperty<string>();
    tt1.name = 'taskType1';
    tt1.id = objectIdService.getNewObjectId();
    tt1.type = PropertyType.TEXT;
    myTask.properties.push(tt1);

    const tt2 = new ClassProperty<string>();
    tt2.name = 'taskType2';
    tt2.id = objectIdService.getNewObjectId();
    tt2.type = PropertyType.TEXT;
    myTask.properties.push(tt2);

    const tt3 = new ClassProperty<string>();
    tt3.name = 'taskType3';
    tt3.id = objectIdService.getNewObjectId();
    tt3.type = PropertyType.TEXT;
    myTask.properties.push(tt3);

    const location = new ClassProperty<string>();
    location.name = 'Location';
    location.id = objectIdService.getNewObjectId();
    location.type = PropertyType.TEXT;
    myTask.properties.push(location);

    const rank = new ClassProperty<string>();
    rank.name = 'rank';
    rank.id = objectIdService.getNewObjectId();
    rank.type = PropertyType.TEXT;
    myTask.properties.push(rank);

    const duration = new ClassProperty<number>();
    duration.name = 'duration';
    duration.id = objectIdService.getNewObjectId();
    duration.type = PropertyType.FLOAT_NUMBER;
    myTask.properties.push(duration);

    classDefinitions.push(myTask);

    const r5 = new Inheritance();
    r5.id = objectIdService.getNewObjectId();
    r5.relationshipType = RelationshipType.INHERITANCE;
    r5.target = myTask.id;
    r5.source = task.id;
    r5.superClassId = r5.source;
    relationships.push(r5);

    return { classDefinitions: classDefinitions, relationships: relationships };
  }
}
