import { ClassDefinition, ClassArchetype } from '../../../../../_model/meta/Class';
import { Relationship, Inheritance, RelationshipType } from '../../../../../_model/meta/Relationship';
import { ObjectIdService } from '../../../../../_service/objectid.service.';
import { ClassProperty, PropertyType } from '../../../../../_model/meta/Property';

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
  ]
};

const mxStyles = {
  classNormal: 'shape=swimlane;resizable=0;',
  classEnum: 'shape=swimlane;resizable=0;' + 'fillColor=#FFCC99;fontColor=#B05800;strokeColor=#B05800;' + 'portConstraint=north',

  classVfiller: 'fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',

  property: 'movable=0;resizable=0;editable=0;deletable=0;selectable=1;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left;html=1;overflow=hidden',
  propertyEnum: 'movable=0;resizable=0;editable=0;deletable=0;selectable=1;fillColor=#FFCC99;fontColor=#B05800;strokeColor=#B05800;align=left;html=1;overflow=hidden;' + 'portConstraint=eastwest',


  addIcon: 'shape=image;image=/assets/mxgraph_resources/images/add_green.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  removeIcon: 'shape=image;image=/assets/mxgraph_resources/images/remove_red.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  classHfiller: 'fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',

  inheritance: 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1;',
  inheritanceEnum: 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1;strokeColor=#B05800',


  association: 'endArrow=none;html=1;curved=1;' + 'edgeStyle=orthogonalEdgeStyle;',
  associationCell: 'resizable=0;html=1;align=left;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;',

  addClassSameLevelIcon: 'shape=image;image=/assets/mxgraph_resources/images/right_blue.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassNewLevelIcon: 'shape=image;image=/assets/mxgraph_resources/images/down_blue.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassNewLevelAssociationIcon: 'shape=image;image=/assets/mxgraph_resources/images/letter-a-icon.jpg;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',

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
  public static mxStyles = mxStyles;
  public static cellTypes = cellTypes;
}

export class CUtils {

  public static addStandardObjects(marketplaceId: string, tenantId: string, objectIdService: ObjectIdService): { classDefintions: ClassDefinition[], relationships: Relationship[] } {
    let configurableClasses: ClassDefinition[] = [];
    let relationships: Relationship[] = [];


    let fwPassEintrag = new ClassDefinition();
    fwPassEintrag.id = objectIdService.getNewObjectId();
    fwPassEintrag.tenantId = tenantId;
    fwPassEintrag.marketplaceId = marketplaceId;
    fwPassEintrag.name = "Freiwilligenpass-\nEintrag";
    fwPassEintrag.root = true;
    fwPassEintrag.classArchetype = ClassArchetype.ROOT;

    fwPassEintrag.properties = [];

    let idProperty = new ClassProperty<string>();
    idProperty.name = 'id';
    idProperty.id = objectIdService.getNewObjectId();
    idProperty.type = PropertyType.TEXT;
    fwPassEintrag.properties.push(idProperty);


    let nameProperty = new ClassProperty<string>();
    nameProperty.name = 'name';
    nameProperty.id = objectIdService.getNewObjectId();
    nameProperty.type = PropertyType.TEXT;
    fwPassEintrag.properties.push(nameProperty);


    let evidenceProperty = new ClassProperty<string>();
    evidenceProperty.name = 'evidenz';
    evidenceProperty.id = objectIdService.getNewObjectId();
    evidenceProperty.type = PropertyType.TEXT;
    fwPassEintrag.properties.push(evidenceProperty);

    configurableClasses.push(fwPassEintrag);

    let task = new ClassDefinition();
    task.id = objectIdService.getNewObjectId();
    task.tenantId = tenantId;
    task.marketplaceId = marketplaceId;
    task.name = "Tätigkeit";
    task.root = false;

    task.classArchetype = ClassArchetype.TASK_HEAD;
    configurableClasses.push(task);

    let vonProperty = new ClassProperty<Date>();
    vonProperty.name = 'dateFrom';
    vonProperty.id = 'objectIdService.getNewObjectId();';
    vonProperty.type = PropertyType.DATE;
    task.properties.push(vonProperty);

    let bisProperty = new ClassProperty<Date>();
    bisProperty.name = 'dateTo';
    bisProperty.id = objectIdService.getNewObjectId();;
    bisProperty.type = PropertyType.DATE;
    task.properties.push(bisProperty);

    let r1 = new Inheritance();
    r1.id = objectIdService.getNewObjectId();
    r1.relationshipType = RelationshipType.INHERITANCE;
    r1.target = task.id;
    r1.source = fwPassEintrag.id;
    r1.superClassId = r1.source;
    relationships.push(r1);

    let competence = new ClassDefinition();
    competence.id = objectIdService.getNewObjectId();
    competence.tenantId = tenantId;
    competence.marketplaceId = marketplaceId;
    competence.name = 'Kompetenz';
    competence.root = false;
    competence.classArchetype = ClassArchetype.COMPETENCE_HEAD;
    configurableClasses.push(competence);

    let r2 = new Inheritance();
    r2.id = objectIdService.getNewObjectId();
    r2.relationshipType = RelationshipType.INHERITANCE;
    r2.target = competence.id;
    r2.source = fwPassEintrag.id;
    r2.superClassId = r2.source;
    relationships.push(r2);

    let achievement = new ClassDefinition();
    achievement.id = objectIdService.getNewObjectId();
    achievement.tenantId = tenantId;
    achievement.marketplaceId = marketplaceId;
    achievement.name = 'Verdienst';
    achievement.root = false;
    achievement.classArchetype = ClassArchetype.ACHIEVEMENT_HEAD;
    configurableClasses.push(achievement);

    let r3 = new Inheritance();
    r3.id = objectIdService.getNewObjectId();
    r3.relationshipType = RelationshipType.INHERITANCE;
    r3.target = achievement.id;
    r3.source = fwPassEintrag.id;
    r3.superClassId = r3.source;
    relationships.push(r3);

    let funktion = new ClassDefinition();
    funktion.id = objectIdService.getNewObjectId();
    funktion.tenantId = tenantId;
    funktion.marketplaceId = marketplaceId;
    funktion.name = 'Funktion';
    funktion.root = false;
    funktion.classArchetype = ClassArchetype.FUNCTION_HEAD;
    configurableClasses.push(funktion);

    let r4 = new Inheritance();
    r4.id = objectIdService.getNewObjectId();
    r4.relationshipType = RelationshipType.INHERITANCE;
    r4.target = funktion.id;
    r4.source = fwPassEintrag.id;
    r4.superClassId = r4.source;
    relationships.push(r4);

    let myTask = new ClassDefinition();
    myTask.id = objectIdService.getNewObjectId();
    myTask.tenantId = tenantId;
    myTask.marketplaceId = marketplaceId;
    myTask.name = 'myTask';
    myTask.root = false;
    myTask.classArchetype = ClassArchetype.TASK;

    myTask.properties = [];
    let tt1 = new ClassProperty<string>();
    tt1.name = 'taskType1';
    tt1.id = objectIdService.getNewObjectId();
    tt1.type = PropertyType.TEXT;
    myTask.properties.push(tt1);

    let tt2 = new ClassProperty<string>();
    tt2.name = 'taskType2';
    tt2.id = objectIdService.getNewObjectId();
    tt2.type = PropertyType.TEXT;
    myTask.properties.push(tt2);

    let tt3 = new ClassProperty<string>();
    tt3.name = 'taskType3';
    tt3.id = objectIdService.getNewObjectId();
    tt3.type = PropertyType.TEXT;
    myTask.properties.push(tt3);

    let location = new ClassProperty<string>();
    location.name = 'location';
    location.id = objectIdService.getNewObjectId();
    location.type = PropertyType.TEXT;
    myTask.properties.push(location);

    let rank = new ClassProperty<string>();
    rank.name = 'rank';
    rank.id = objectIdService.getNewObjectId();
    rank.type = PropertyType.TEXT;
    myTask.properties.push(rank);

    let duration = new ClassProperty<number>();
    duration.name = 'rank';
    duration.id = objectIdService.getNewObjectId();
    duration.type = PropertyType.FLOAT_NUMBER;
    myTask.properties.push(duration);

    configurableClasses.push(myTask);

    let r5 = new Inheritance();
    r5.id = objectIdService.getNewObjectId();
    r5.relationshipType = RelationshipType.INHERITANCE;
    r5.target = myTask.id;
    r5.source = task.id;
    r5.superClassId = r5.source;
    relationships.push(r5);

    return { classDefintions: configurableClasses, relationships: relationships };

  }
}