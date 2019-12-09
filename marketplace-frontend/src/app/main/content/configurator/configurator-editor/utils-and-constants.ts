import { ClassDefinition, ClassArchetype } from '../../_model/meta/Class';
import { Relationship, Inheritance, RelationshipType } from '../../_model/meta/Relationship';
import { ObjectIdService } from '../../_service/objectid.service.';

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
  propertyEnum: 'movable=0;resizable=0;editable=1;deletable=0;selectable=1;fillColor=#FFCC99;fontColor=#B05800;strokeColor=#B05800;align=left;html=1;overflow=hidden;' + 'portConstraint=eastwest',


  addIcon: 'shape=image;image=/assets/mxgraph_resources/images/add_green.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  removeIcon: 'shape=image;image=/assets/mxgraph_resources/images/remove_red.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  classHfiller: 'fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',

  inheritance: 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1;' ,
  inheritanceEnum: 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1;strokeColor=#B05800',


  association: 'endArrow=none;html=1;curved=1;' + 'edgeStyle=orthogonalEdgeStyle;',
  associationCell: 'resizable=0;html=1;align=left;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;',

  addClassSameLevelIcon: 'shape=image;image=/assets/mxgraph_resources/images/right_blue.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassNewLevelIcon: 'shape=image;image=/assets/mxgraph_resources/images/down_blue.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassNewLevelAssociationIcon: 'shape=image;image=/assets/mxgraph_resources/images/letter-a-icon.jpg;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',

};

const cellTypes = {
  property: {label: 'property', icon:''},
  property_enum: {label: 'property_enum', icon:''},

  add: {label: 'add', icon:''},
  add_association: {label: 'property', icon:''},
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

  public static addStandardObjects(marketplaceId: string, objectIdService: ObjectIdService): {classDefintions: ClassDefinition[], relationships: Relationship[]} {
    let configurableClasses: ClassDefinition[] = [];
    let relationships: Relationship[] = [];
   
   
    let fwPassEintrag = new ClassDefinition();
    fwPassEintrag.id = objectIdService.getNewObjectId();
    fwPassEintrag.marketplaceId = marketplaceId;
    fwPassEintrag.name = "Freiwilligenpass-\nEintrag";
    fwPassEintrag.root = true;
    fwPassEintrag.classArchetype = ClassArchetype.ROOT;
    configurableClasses.push(fwPassEintrag);

    let task = new ClassDefinition();
    task.id = objectIdService.getNewObjectId();
    task.marketplaceId = marketplaceId;
    task.name = "Task";
    task.root = false;
    task.classArchetype = ClassArchetype.TASK_HEAD;
    configurableClasses.push(task);

    let r1 = new Inheritance();
    r1.id = objectIdService.getNewObjectId();
    r1.relationshipType = RelationshipType.INHERITANCE;
    r1.target = task.id;
    r1.source = fwPassEintrag.id;
    r1.superClassId = r1.source;
    relationships.push(r1);

    let competence = new ClassDefinition();
    competence.id = objectIdService.getNewObjectId();
    competence.marketplaceId = marketplaceId;
    competence.name = "Competence";
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
    achievement.marketplaceId = marketplaceId;
    achievement.name = "Achievement";
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
    funktion.marketplaceId = marketplaceId;
    funktion.name = "Function";
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

    return {classDefintions: configurableClasses, relationships: relationships};

  }
}