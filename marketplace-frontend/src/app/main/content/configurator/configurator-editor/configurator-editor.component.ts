import { Component, OnInit, Input, AfterViewInit, ViewChild, ElementRef, AfterContentInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassDefinition, ClassArchetype } from 'app/main/content/_model/meta/Class';
import { mxgraph } from "mxgraph";
import { Relationship, RelationshipType, Association, AssociationCardinality, Inheritance } from 'app/main/content/_model/meta/Relationship';
import { isNullOrUndefined } from 'util';
import { DialogFactoryComponent } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { PropertyDefinition, PropertyItem, ClassProperty } from 'app/main/content/_model/meta/Property';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { RelationshipService } from 'app/main/content/_service/meta/core/relationship/relationship.service';
import { EditorPopupMenu } from './popup-menu';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from '../../_service/meta/core/configurator/configurator.service';
import { DataTransportService } from '../../_service/data-transport/data-transport.service';
import { ObjectIdService } from "../../_service/objectid.service.";

declare var require: any;


const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

export class myMxCell extends mx.mxCell {
  cellType?: string;
  classArchetype?: ClassArchetype;

  root?: boolean;
  property: boolean;
  propertyId?: string;
  newlyAdded: boolean;

}

const sidebarPalettes =
{
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
}

const relationshipPalettes = {
  id: 'relationships', label: 'Relationships',
  rows: [
    { id: 'INHERITANCE', label: 'Inheritance', imgPath: '/assets/mxgraph_resources/images/custom/inheritance.svg', type: 'inheritance', shape: undefined },
    { id: 'ASSOCIATION', label: 'Association', imgPath: '/assets/mxgraph_resources/images/custom/association.svg', type: 'association', shape: undefined },
  ]
}
  ;

const mxStyles = {
  classNormal: 'shape=swimlane;resizable=0;',
  classEnum:  'shape=swimlane;resizable=0;'+ 'fillColor=#FFCC99;fontColor=#B05800;strokeColor=#B05800;',

  classVfiller: 'fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  property: 'movable=0;resizable=0;editable=0;deletable=0;selectable=0;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left;html=1;overflow=hidden',
  addIcon: 'shape=image;image=/assets/mxgraph_resources/images/add_green.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  removeIcon: 'shape=image;image=/assets/mxgraph_resources/images/remove_red.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  classHfiller: 'fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
 
  inheritance: 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1',
  inheritanceEnum: 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1;strokeColor=#B05800',


  association: 'endArrow=none;html=1;curved=1',
  associationCell: 'resizable=0;html=1;align=left;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;',

  addClassSameLevelIcon: 'shape=image;image=/assets/mxgraph_resources/images/right_blue.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassNewLevelIcon: 'shape=image;image=/assets/mxgraph_resources/images/down_blue.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',
  addClassNewLevelAssociationIcon: 'shape=image;image=/assets/mxgraph_resources/images/letter-a-icon.jpg;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;',

}


@Component({
  selector: 'app-configurator-editor',
  templateUrl: './configurator-editor.component.html',
  styleUrls: ['./configurator-editor.component.scss'],
  providers: [DialogFactoryComponent]

})
export class ConfiguratorEditorComponent implements OnInit, AfterContentInit {

  @Input() marketplace: Marketplace;

  configurableClasses: ClassDefinition[];
  deletedClassIds: string[]

  relationships: Relationship[];
  deletedRelationshipIds: string[];

  currentConfigurator: Configurator;

  isLoaded: boolean = false;
  allPropertyDefinitions: PropertyDefinition<any>[];
  sidebarPalettes = sidebarPalettes;
  relationshipPalettes = relationshipPalettes;

  popupMenu: EditorPopupMenu;

  eventResponseAction: string;

  modelUpdated: boolean;

  rightSidebarVisible: boolean;
  

  constructor(private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService,
    private propertyDefinitionService: PropertyDefinitionService,
    private relationshipService: RelationshipService,
    private dialogFactory: DialogFactoryComponent,
    private snackBar: MatSnackBar,
    private configuratorService: ConfiguratorService,
    private dataTransportService: DataTransportService,
    private objectIdService: ObjectIdService) {

  }

  @ViewChild('graphContainer', { static: true }) graphContainer: ElementRef;
  @ViewChild('leftSidebarContainer', { static: true }) leftSidebarContainer: ElementRef;
  @ViewChild('rightSidebarContainer', {static: true}) rightSidebarContainer: ElementRef;

  graph: mxgraph.mxGraph;

  ngOnInit() {
    this.fetchPropertyDefinitions();
    this.configurableClasses = [];
    this.deletedClassIds = [];
    this.relationships = [];
    this.deletedRelationshipIds = [];
    this.rightSidebarVisible = true;

    console.log(this.configurableClasses);
    console.log(this.relationships);
  }

  fetchPropertyDefinitions() {
    this.propertyDefinitionService.getAllPropertyDefinitons(this.marketplace).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
      if (!isNullOrUndefined(propertyDefinitions)) {
        this.allPropertyDefinitions = propertyDefinitions;
      }
    });
  }

  ngAfterContentInit() {
    this.graphContainer.nativeElement.style.position = 'absolute';
    this.graphContainer.nativeElement.style.overflow = 'hidden';
    this.graphContainer.nativeElement.style.left = '200px';
    this.graphContainer.nativeElement.style.top = '30px';
    this.graphContainer.nativeElement.style.right = '0px';
    this.graphContainer.nativeElement.style.bottom = '0px';
    this.graphContainer.nativeElement.style.background = 'white';
    // this.graphContainer.nativeElement.style.margin = '5px';

    this.leftSidebarContainer.nativeElement.style.position = 'absolute';
    this.leftSidebarContainer.nativeElement.style.overflow = 'auto';
    this.leftSidebarContainer.nativeElement.style.padding = '2px';
    this.leftSidebarContainer.nativeElement.style.left = '0px';
    this.leftSidebarContainer.nativeElement.style.top = '30px';
    this.leftSidebarContainer.nativeElement.style.width = '200px';
    this.leftSidebarContainer.nativeElement.style.bottom = '0px';
    this.leftSidebarContainer.nativeElement.style.background = 'rgba(214, 239, 249, 0.9)';

    this.rightSidebarContainer.nativeElement.style.position = 'absolute';
    this.rightSidebarContainer.nativeElement.style.overflow = 'auto';
    this.rightSidebarContainer.nativeElement.style.padding = '2px';
    this.rightSidebarContainer.nativeElement.style.right = '0px';
    this.rightSidebarContainer.nativeElement.style.top = '30px';
    this.rightSidebarContainer.nativeElement.style.width = '300px';
    this.rightSidebarContainer.nativeElement.style.bottom = '0px';
    this.rightSidebarContainer.nativeElement.style.background = 'rgba(214, 239, 249, 0.9)';
    this.rightSidebarContainer.nativeElement.style.borderLeft = "solid 1px rgb(160, 160, 160)";

    // this.rightSidebarContainer.nativeElement.style.background = 'rgba(214, 239, 249, 0.0)';
    // this.rightSidebarContainer.nativeElement.style.borderLeft = "none";


    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);

    this.graph.isCellSelectable = function (cell) {
      var state = this.view.getState(cell);
      var style = (state != null) ? state.style : this.getCellStyle(cell);

      return this.isCellsSelectable() && !this.isCellLocked(cell) && style['selectable'] != 0;
    };

    this.graph.getCursorForCell = function (cell: myMxCell) {
      if (cell.cellType == 'property' || cell.cellType == 'add' || cell.cellType == 'remove' || 
          cell.cellType == "add_class_new_level" || cell.cellType == "add_class_same_level" || cell.cellType == "add_association") {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      }
    }

    //  this.graphContainer.nativeElement.style.background = 'url("assets/mxgraph_resources/images/grid.gif")';

    if (!mx.mxClient.isBrowserSupported()) {
      mx.mxUtils.error('Browser is not supported!', 200, false);
    }

    else {
      // Disables the built-in context menu
      mx.mxEvent.disableContextMenu(this.graphContainer.nativeElement);

      // Enables rubberband selection
      new mx.mxRubberband(this.graph);

      this.graph.setPanning(true);

      this.graph.popupMenuHandler = this.createPopupMenu(this.graph);

      const outer = this; //preserve outer scope
      this.graph.addListener(mx.mxEvent.CLICK, function (sender, evt) {
        outer.handleMXGraphClickEvent(evt);
      });

      this.showServerContent(true);
    }
  }

  private createPopupMenu(graph) {
    this.popupMenu = new EditorPopupMenu(graph, this);
    return this.popupMenu.createPopupMenuHandler(graph);
  }

  showServerContent(newGraph: boolean) {
    this.clearEditor();
    if (newGraph) {
      this.addStandardObjects();
    }
    this.parseGraphContent();
    this.setLayout('vertical');
  }

  clearEditor() {
    this.graph.getModel().beginUpdate();
    try {
      this.graph.getModel().clear();
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private addStandardObjects() {
    let fwPassEintrag = new ClassDefinition();
    fwPassEintrag.id = this.objectIdService.getNewObjectId();
    fwPassEintrag.marketplaceId = this.marketplace.id;
    fwPassEintrag.name = "Freiwilligenpass-\nEintrag";
    fwPassEintrag.root = true;
    fwPassEintrag.classArchetype = ClassArchetype.ROOT;
    this.configurableClasses.push(fwPassEintrag);

    let task = new ClassDefinition();
    task.id = this.objectIdService.getNewObjectId();
    task.marketplaceId = this.marketplace.id;
    task.name = "Task";
    task.classArchetype = ClassArchetype.TASK_HEAD;
    this.configurableClasses.push(task);

    let r1 = new Inheritance();
    r1.id = this.objectIdService.getNewObjectId();
    r1.relationshipType = RelationshipType.INHERITANCE;
    r1.target = task.id;
    r1.source = fwPassEintrag.id;
    r1.superClassId = r1.source;
    this.relationships.push(r1);

    let competence = new ClassDefinition();
    competence.id = this.objectIdService.getNewObjectId();
    competence.marketplaceId = this.marketplace.id;
    competence.name = "Competence";
    competence.classArchetype = ClassArchetype.COMPETENCE_HEAD;
    this.configurableClasses.push(competence);

    let r2 = new Inheritance();
    r2.id = this.objectIdService.getNewObjectId();
    r2.relationshipType = RelationshipType.INHERITANCE;
    r2.target = competence.id;
    r2.source = fwPassEintrag.id;
    r2.superClassId = r2.source;
    this.relationships.push(r2);

    let achievement = new ClassDefinition();
    achievement.id = this.objectIdService.getNewObjectId();
    achievement.marketplaceId = this.marketplace.id;
    achievement.name = "Achievement";
    achievement.classArchetype = ClassArchetype.ACHIEVEMENT_HEAD;
    this.configurableClasses.push(achievement);

    let r3 = new Inheritance();
    r3.id = this.objectIdService.getNewObjectId();
    r3.relationshipType = RelationshipType.INHERITANCE;
    r3.target = achievement.id;
    r3.source = fwPassEintrag.id;
    r3.superClassId = r3.source;
    this.relationships.push(r3);

    let funktion = new ClassDefinition();
    funktion.id = this.objectIdService.getNewObjectId();
    funktion.marketplaceId = this.marketplace.id;
    funktion.name = "Function";
    funktion.classArchetype = ClassArchetype.FUNCTION_HEAD;
    this.configurableClasses.push(funktion);

    let r4 = new Inheritance();
    r4.id = this.objectIdService.getNewObjectId();
    r4.relationshipType = RelationshipType.INHERITANCE;
    r4.target = funktion.id;
    r4.source = fwPassEintrag.id;
    r4.superClassId = r4.source;
    this.relationships.push(r4);

  }

  private parseGraphContent() {
    this.parseIncomingClasses();
    this.parseIncomingRelationships();
  }

  private parseIncomingClasses() {
    var modelGetStyle = this.graph.model.getStyle;
    this.graph.model.getStyle = function (cell) {
      if (cell != null) {
        var style = modelGetStyle.apply(this, arguments);

        if (this.isCollapsed(cell)) {
          style = style + ';shape=rectangle';
        }
        return style;
      }
      return null;
    };

    this.graph.getModel().beginUpdate();
    try {
      for (let c of this.configurableClasses) {
        this.insertClassIntoGraph(c, new mx.mxGeometry(0, 0, 80, 30), false);
      }

    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private parseIncomingRelationships() {
    this.graph.getModel().beginUpdate();
    try {
      for (let r of this.relationships) {
        this.insertRelationshipIntoGraph(r, new mx.mxPoint(0, 0), false);
      }
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private insertClassIntoGraph(classDefinition: ClassDefinition, geometry: mxgraph.mxGeometry, createNew: boolean) {
    //create class cell

    let cell: myMxCell = new mx.mxCell(classDefinition.name, geometry, mxStyles.classNormal) as myMxCell;
    cell.root = classDefinition.root;
    cell.setCollapsed(false);
    cell.cellType = 'class';
    cell.classArchetype = classDefinition.classArchetype;
    cell.newlyAdded = createNew;
    cell.value = classDefinition.name;
    cell.setVertex(true);
    cell.setConnectable(true);

    if (cell.classArchetype.startsWith('ENUM')) {
      cell.setStyle(mxStyles.classEnum);
    }

    if (!isNullOrUndefined(classDefinition.id)) {
      cell.id = classDefinition.id;
    }

    //create vertical space before properties
    let vfiller = this.graph.insertVertex(cell, "vfiller", null, 105, 45, 5, 130, mxStyles.classVfiller)
    vfiller.setConnectable(false);
    cell.geometry.alternateBounds = new mx.mxRectangle(0, 0, 80, 30);
    cell.geometry.setRect(cell.geometry.x, cell.geometry.y, cell.geometry.width, classDefinition.properties.length * 20 + 25);

    //create properties
    let i = 5;
    if (!isNullOrUndefined(classDefinition.properties)) {
      for (let p of classDefinition.properties) {
        let propertyEntry: myMxCell = this.graph.insertVertex(cell, p.id, p.name, 5, i + 45, 100, 20, mxStyles.property) as myMxCell;
        propertyEntry.cellType = 'property';
        propertyEntry.propertyId = p.id;
        propertyEntry.setConnectable(false);
        i = i + 20;
      }
    }

    //add property icon
    if (cell.classArchetype != ClassArchetype.ENUM_HEAD && cell.classArchetype != ClassArchetype.ROOT && cell.classArchetype != ClassArchetype.ENUM_ENTRY && !cell.classArchetype.endsWith("_HEAD")) {  
      let addIcon: myMxCell = this.graph.insertVertex(cell, "add", 'add', 5, i + 50, 20, 20, mxStyles.addIcon) as myMxCell;
      addIcon.setConnectable(false);
      addIcon.cellType = 'add';
    }

    //add association icon
    if (cell.classArchetype != ClassArchetype.ENUM_HEAD && cell.classArchetype != ClassArchetype.ROOT && cell.classArchetype != ClassArchetype.ENUM_ENTRY && !cell.classArchetype.endsWith("_HEAD")) {
      let downAssociationIcon: myMxCell = this.graph.insertVertex(cell, "add association", "add_association", 45, i + 50, 20, 20, mxStyles.addClassNewLevelAssociationIcon) as myMxCell;
      downAssociationIcon.setConnectable(false);
      downAssociationIcon.cellType = "add_association";
    }

    //next icon
    if (cell.classArchetype != ClassArchetype.ENUM_HEAD && cell.classArchetype != ClassArchetype.ROOT && cell.classArchetype && !cell.classArchetype.endsWith("_HEAD")) {
      let nextIcon: myMxCell = this.graph.insertVertex(cell, "add another", "add_class_same_level", 85, i + 50, 20, 20, mxStyles.addClassSameLevelIcon) as myMxCell;
      nextIcon.setConnectable(false);
      nextIcon.cellType = 'add_class_same_level';

    }

    //down icon
    if (cell.classArchetype != ClassArchetype.ROOT) {
      let downIcon: myMxCell = this.graph.insertVertex(cell, "add another", "add_class_new_level", 65, i + 50, 20, 20, mxStyles.addClassNewLevelIcon) as myMxCell;
      downIcon.setConnectable(false);
      downIcon.cellType = 'add_class_new_level';
    }

    //remove icon
    if (classDefinition.properties.length > 0 && cell.classArchetype != ClassArchetype.ENUM_HEAD && cell.classArchetype != ClassArchetype.ENUM_ENTRY && cell.classArchetype != ClassArchetype.ROOT && !cell.classArchetype.endsWith("_HEAD")) {
      let removeIcon: myMxCell = this.graph.insertVertex(cell, "remove", 'remove', 25, i + 50, 20, 20, mxStyles.removeIcon) as myMxCell;
      removeIcon.setConnectable(false);
      removeIcon.cellType = 'remove';
    }

    //create horizonal filler in front of properties
    let hfiller = this.graph.insertVertex(cell, "hfiller", null, 0, i + 50 + 20, 85, 5, mxStyles.classHfiller)
    hfiller.setConnectable(false);

    return this.graph.addCell(cell);
  }

  private insertRelationshipIntoGraph(r: Relationship, coords: mxgraph.mxPoint, createNew: boolean) {

    const parent = this.graph.getDefaultParent();

    let source: myMxCell = this.graph.getModel().getCell(r.source) as myMxCell;
    let target: myMxCell = this.graph.getModel().getCell(r.target) as myMxCell;

    let cell: myMxCell;
    if (r.relationshipType == RelationshipType.INHERITANCE) {
      cell = new mx.mxCell(undefined, new mx.mxGeometry(coords.x, coords.y, 0, 0), mxStyles.inheritance) as myMxCell;
      cell.cellType = 'inheritance'

      if(source.classArchetype.startsWith("ENUM_")) {
        cell.setStyle(mxStyles.inheritanceEnum);
      }

    } else if (r.relationshipType == RelationshipType.ASSOCIATION) {
      cell = new mx.mxCell('', new mx.mxGeometry(coords.x, coords.y, 0, 0), mxStyles.association) as myMxCell;
      cell.cellType = 'association';

      let cell1 = new mx.mxCell(AssociationCardinality[(r as Association).sourceCardinality], new mx.mxGeometry(-0.8, 0, 0, 0), mxStyles.associationCell) as myMxCell;
      cell1.geometry.relative = true;
      cell1.setConnectable(false);
      cell1.vertex = true;
      cell1.cellType = 'associationLabel';

      if (isNullOrUndefined(cell1.value)) {
        cell1.value = 'start';
      }
      cell.insert(cell1);

      let cell2 = new mx.mxCell(AssociationCardinality[(r as Association).targetCardinality], new mx.mxGeometry(0.8, 0, 0, 0), mxStyles.associationCell) as myMxCell;
      cell2.geometry.relative = true;
      cell2.setConnectable(false);
      cell2.vertex = true;
      cell2.cellType = 'associationLabel'

      if (isNullOrUndefined(cell2.value)) {
        cell2.value = 'end';
      }
      cell.insert(cell2);

    } else {
      console.error("invalid RelationshipType");
    }

    if (!createNew) {
      cell.id = r.id;
    } else {
      cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x - 100, coords.y - 20), true);
      cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x + 100, coords.y), false)

      source = undefined;
      target = undefined;
    }

    cell.newlyAdded = createNew;
    cell.geometry.relative = true;
    cell.edge = true;

    return this.graph.addEdge(cell, parent, source, target);
  }

  private setLayout(layoutType: string) {

    if (layoutType == 'radial') {
      let layout = new mx.mxRadialTreeLayout(this.graph);
      layout.execute(this.graph.getDefaultParent(), getRootCells(this.graph));

    } else if (layoutType == 'vertical') {
      let layout = new mx.mxHierarchicalLayout(this.graph, mx.mxConstants.DIRECTION_NORTH);
      layout.execute(this.graph.getDefaultParent(), getRootCells(this.graph));

    } else if (layoutType == 'horizontal') {
      let layout = new mx.mxHierarchicalLayout(this.graph, mx.mxConstants.DIRECTION_WEST);
      layout.execute(this.graph.getDefaultParent(), getRootCells(this.graph));
    }

    function getRootCells(graph: mxgraph.mxGraph): mxgraph.mxCell[] {
      let rootCells = graph.getModel().getChildCells(graph.getDefaultParent()).filter((cell: myMxCell) => {
        return cell.root;
      });
      return rootCells;
    }


    this.resetViewport();
  }



  //TODO @Alex fix issue in regards to saved Geometry
  redrawContent() {
    let bounds = this.graph.getView().getGraphBounds();
    let translate = this.graph.getView().getTranslate();


    // let savedGeometry = this.saveGeometry();
    this.clearEditor();
    this.showServerContent(false);
    // this.restoreGeometry(savedGeometry);

    this.setLayout('vertical');
    bounds.y *= -1;
    bounds.x *= -1;

    this.graph.scrollRectToVisible(bounds);
  }

  //TODO
  private saveGeometry(): { id: string, geometry: mxgraph.mxGeometry }[] {
    let cells = this.graph.getModel().getChildCells(this.graph.getDefaultParent());
    let savedGeometry: { id: string, geometry: mxgraph.mxGeometry }[] = [];
    for (let cell of cells) {
      savedGeometry.push({ id: cell.id, geometry: cell.geometry });
    }
    return savedGeometry;
  }

  //TODO
  private restoreGeometry(savedGeometries: { id: string, geometry: mxgraph.mxGeometry }[]) {
    let cells = this.graph.getModel().getChildCells(this.graph.getDefaultParent());

    for (let cell of cells) {
      let geometry = savedGeometries.find((g: any) => {
        return g.id == cell.id
      });

      //keep width and height if number of properties changed
      let width = cell.geometry.width;
      let height = cell.geometry.height;
      cell.setGeometry(geometry.geometry);
      cell.geometry.width = width;
      cell.geometry.height = height;
    }
  }

  //Events
  handleMXGraphClickEvent(event: any) {
    let cell: myMxCell = event.getProperty("cell");

    if (!isNullOrUndefined(cell)) {
      var parent = cell.getParent();

      if (cell.value == "add") {
        for (let c of this.configurableClasses) {
          if (c.id == parent.id) {
            this.dialogFactory.addPropertyDialogGeneric(this.allPropertyDefinitions, c.properties).then((result: { propertyItems: PropertyItem[], key: string }) => {
              if (!isNullOrUndefined(result)) {

                if (result.key == "new_property") {
                  console.log("add property")
                } else {
                  this.classDefinitionService.getClassPropertyFromPropertyDefinitionById(this.marketplace, result.propertyItems.map(propertyItems => propertyItems.id)).toPromise().then((ret: ClassProperty<any>[]) => {
                    c.properties.push(...ret);
                    this.updateModel();
                    this.redrawContent();
                  });
                }
              }

            });
            break;
          }
        }
      }

      if (cell.value == "remove") {
        for (let c of this.configurableClasses) {
          if (c.id == parent.id) {
            this.dialogFactory.removePropertyDialogGeneric(c.properties).then((props: PropertyItem[]) => {
              let rest = c.properties.filter((p: PropertyItem) => {
                return !(props.indexOf(p) >= 0);
              });

              c.properties = rest;
              this.updateModel();
              this.redrawContent();
            });
            break;
          }
        }
      }

      if (cell.value == "add_class_same_level") {
        let addedClass = new ClassDefinition();
        addedClass.properties = [];


        addedClass.classArchetype = (cell.getParent() as myMxCell).classArchetype;
        addedClass.name = ClassArchetype.getClassArchetypeLabel(addedClass.classArchetype);

        let cret = this.insertClassIntoGraph(addedClass, new mx.mxGeometry(0, 0, 80, 30), true);
        cret.id = this.objectIdService.getNewObjectId();
        addedClass.id = cret.id;

        this.configurableClasses.push(addedClass);

        let precursor = this.graph.getIncomingEdges(cell.getParent())[0].source;

        let addedRelationship = new Relationship();
        addedRelationship.relationshipType = RelationshipType.INHERITANCE;
        addedRelationship.source = precursor.id
        addedRelationship.target = addedClass.id;
        let rret = this.insertRelationshipIntoGraph(addedRelationship, new mx.mxPoint(0, 0), true);
        rret.id = this.objectIdService.getNewObjectId();
        addedRelationship.id = rret.id;
        this.relationships.push(addedRelationship);



        this.updateModel();
        this.redrawContent();
      }

      if (cell.value == "add_class_new_level") {
        let addedClass = new ClassDefinition();
        addedClass.properties = [];

        let parentClassArchetype = (cell.getParent() as myMxCell).classArchetype
        if (parentClassArchetype == ClassArchetype.ENUM_HEAD || parentClassArchetype == ClassArchetype.ENUM_ENTRY) {
          addedClass.classArchetype = ClassArchetype.ENUM_ENTRY;
        } else if (parentClassArchetype.endsWith("_HEAD")) {
          addedClass.classArchetype = ClassArchetype[parentClassArchetype.substr(0, parentClassArchetype.length-5)];
        } else {
          addedClass.classArchetype = (cell.getParent() as myMxCell).classArchetype;
        }

        addedClass.name = ClassArchetype.getClassArchetypeLabel(addedClass.classArchetype);
        let cret = this.insertClassIntoGraph(addedClass, new mx.mxGeometry(0, 0, 80, 30), true);
        cret.id = this.objectIdService.getNewObjectId();
        addedClass.id = cret.id;
        this.configurableClasses.push(addedClass);

        let addedRelationship = new Relationship();
        addedRelationship.relationshipType = RelationshipType.INHERITANCE;
        addedRelationship.source = cell.getParent().id
        addedRelationship.target = cret.id;
        let rret = this.insertRelationshipIntoGraph(addedRelationship, new mx.mxPoint(0, 0), true);
        rret.id = this.objectIdService.getNewObjectId();
        addedRelationship.id = rret.id;
        this.relationships.push(addedRelationship);

        this.updateModel();
        this.redrawContent();
      }

      if (cell.value == "add_association") {
        console.log("add association");
        let addedClass = new ClassDefinition();
        addedClass.properties = [];
        let valueProperty = new ClassProperty();
        valueProperty.name = "value";
        valueProperty.id = "value";
        addedClass.properties.push(valueProperty);
        addedClass.classArchetype = ClassArchetype.ENUM_HEAD;
        addedClass.name = "Enter Value";

        let cret = this.insertClassIntoGraph(addedClass, new mx.mxGeometry(0, 0, 80, 30), true);
        cret.id = this.objectIdService.getNewObjectId();
        addedClass.id = cret.id;
        this.configurableClasses.push(addedClass);

        let addedRelationship = new Association();
        addedRelationship.relationshipType = RelationshipType.ASSOCIATION;
        addedRelationship.source = cell.getParent().id
        addedRelationship.target = cret.id;
        addedRelationship.sourceCardinality = 'ONE';
        addedRelationship.targetCardinality = 'ONE';
        addedRelationship.id = this.objectIdService.getNewObjectId();


        let rret = this.insertRelationshipIntoGraph(addedRelationship, new mx.mxPoint(0, 0), true);
        rret.id = addedRelationship.id;
        this.relationships.push(addedRelationship);

        this.updateModel();
        this.redrawContent();

      }

      this.modelUpdated = true;
    }
  }

  handleMousedownEvent(event: any, paletteItempaletteEntry: any, item: any, graph: mxgraph.mxGraph) {
    const outer = this;
    let positionEvent: MouseEvent;

    var onDragstart = function (evt) {
      evt.dataTransfer.setData('text', item.id);
      evt.dataTransfer.effect = "move"
      evt.dataTransfer.effectAllowed = "move";
    }

    var onDragOver = function (evt) {
      positionEvent = evt;
    }

    var onDragend = function (evt) {
      evt.dataTransfer.getData('text');
      try {
        addObjectToGraph(evt, item, graph);
      } finally {
        graph.getModel().endUpdate();
        removeEventListeners(outer);
      }

      function addObjectToGraph(dragEndEvent: MouseEvent, paletteItem: any, graph: mxgraph.mxGraph) {

        const coords: mxgraph.mxPoint = graph.getPointForEvent(positionEvent, false);
        graph.getModel().beginUpdate();

        if (paletteItem.type == 'class') {
          let addedClass = new ClassDefinition();
          addedClass.name = paletteItem.label;
          addedClass.properties = [];
          addedClass.classArchetype = paletteItem.archetype;


          let cell = outer.insertClassIntoGraph(addedClass, new mx.mxGeometry(coords.x, coords.y, 80, 30), true);
          //TODO set unique objectId on creation of cell/class
          cell.id = outer.objectIdService.getNewObjectId();
          addedClass.id = cell.id;
          outer.configurableClasses.push(addedClass);

        } else {
          let r = new Relationship();
          r.relationshipType = paletteItem.id;
          let cell = outer.insertRelationshipIntoGraph(r, coords, true);

          cell.id = outer.objectIdService.getNewObjectId();
          r.id = cell.id;
          outer.relationships.push(r);

        }
      }
    }

    var onMouseUp = function (evt) {
      removeEventListeners(outer);
    }

    event.srcElement.addEventListener("dragend", onDragend);
    event.srcElement.addEventListener("mouseup", onMouseUp);
    event.srcElement.addEventListener("dragstart", onDragstart);
    this.graphContainer.nativeElement.addEventListener("dragover", onDragOver);

    function removeEventListeners(outerScope: any) {
      event.srcElement.removeEventListener("dragend", onDragend);
      event.srcElement.removeEventListener("mouseup", onMouseUp);
      event.srcElement.removeEventListener("dragstart", onDragstart);
      outerScope.graphContainer.nativeElement.removeEventListener("dragover", onDragOver);

    }
  }

  //Functions for Views/Viewing
  zoomInEvent() {
    this.graph.zoomIn()
  }

  zoomOutEvent() {
    this.graph.zoomOut();
  }

  zoomResetEvent() {
    this.graph.zoomActual();
    this.resetViewport();
  }

  resetViewport() {
    var outer = this;
    this.graph.scrollCellToVisible((function getLeftMostCell() {
      return outer.graph.getModel().getChildCells(outer.graph.getDefaultParent()).find((c: myMxCell) => { return c.root });
    })(), false);

    const translate = this.graph.view.getTranslate()
    this.graph.view.setTranslate(translate.x + 10, translate.y + 10);
  }



  //Menu functions
  consumeMenuOptionClickedEvent(event: any) {
    this.eventResponseAction = null;
    switch (event.id) {
      case 'editor_save': {
        if (isNullOrUndefined(this.currentConfigurator)) {
          this.eventResponseAction = "saveAsClicked";
        } else {
          if (!isNullOrUndefined(event.configurator)) {
            this.currentConfigurator = event.configurator;
          }
          this.saveGraph();
        }
        break;
      } case 'editor_new': {
        this.newGraph();
        break
      } case 'editor_open': {
        this.openGraph(event.configurator);
        break;
      } case 'cancelled': {
        break;
      }
    }
  }


  saveGraph() {

    this.updateModel();
    let relSaveSuccess: boolean;
    let classSaveSuccess: boolean;
    let deletedClassSaveSuccess: boolean;
    let deletedRelSaveSuccess: boolean;
    let configuratorSaveSuccess: boolean;

    Promise.all([
      this.relationshipService.addAndUpdateRelationships(this.marketplace, this.relationships).toPromise().then((result: any) => {
        relSaveSuccess = !isNullOrUndefined(result);
      }),

      this.classDefinitionService.addOrUpdateClassDefintions(this.marketplace, this.configurableClasses).toPromise().then((result: any) => {
        classSaveSuccess = !isNullOrUndefined(result);

      }),
      this.classDefinitionService.deleteClassDefinitions(this.marketplace, this.deletedClassIds).toPromise().then((result: any) => {
        this.deletedClassIds = [];
        deletedClassSaveSuccess = true;
      }),

      this.relationshipService.deleteRelationships(this.marketplace, this.deletedRelationshipIds).toPromise().then((result: any) => {
        this.deletedRelationshipIds = [];
        deletedRelSaveSuccess = true;
      }),

    ]).then(() => {

      this.configuratorService.saveConfigurator(this.marketplace, this.currentConfigurator).toPromise().then((result: any) => {
        configuratorSaveSuccess = !isNullOrUndefined(result);

      }).then(() => {
        let snackBarMessage: string;
        if (relSaveSuccess && classSaveSuccess && deletedClassSaveSuccess && deletedRelSaveSuccess && configuratorSaveSuccess) {
          snackBarMessage = "save successful!";
        } else {
          snackBarMessage = "save failed!";
        }

        let snackbarRef = this.snackBar.open(snackBarMessage, "dismiss", {
          duration: 5000
        });
        snackbarRef.onAction().toPromise().then(() => {
          this.snackBar.dismiss();
        });
      });
    });
  }

  newGraph() {
    this.configurableClasses = [];
    this.relationships = [];
    this.currentConfigurator = undefined;
    this.showServerContent(true);
  }

  openGraph(configurator: Configurator) {
    this.currentConfigurator = configurator;

    Promise.all([
      // grab classDefinitionss from server
      this.classDefinitionService.getClassDefinitionsById(this.marketplace, configurator.classDefinitionIds).toPromise().then((classDefinitions: ClassDefinition[]) => {
        if (!isNullOrUndefined(classDefinitions)) {
          this.configurableClasses = classDefinitions;
        } else {
          this.configurableClasses = [];
        }
      }),

      //grab relationships from Server
      this.relationshipService.getRelationshipsById(this.marketplace, configurator.relationshipIds).toPromise().then((relationships: Relationship[]) => {
        if (!isNullOrUndefined(relationships)) {
          this.relationships = relationships;
        } else {
          this.relationships = [];
        }
      })
    ]).then(() => {
      //draw graph
      this.showServerContent(false);
    });

  }

  updateModel() {
    //store current connections in relationships
    let allCells = this.graph.getModel().getChildren(this.graph.getDefaultParent());

    for (let cd of this.configurableClasses) {

      let cell: myMxCell = allCells.find((c: mxgraph.mxCell) => {
        return c.id == cd.id;
      }) as myMxCell;

      if (!isNullOrUndefined(cell)) {
        if ((cell.cellType == 'class')) {
          cd.root = cell.root;
          cd.name = cell.value;
        }
      }
    }

    for (let r of this.relationships) {
      let cell: myMxCell = allCells.find((c: mxgraph.mxCell) => {
        return c.id == r.id;
      }) as myMxCell;

      if (!isNullOrUndefined(cell.source)) {
        r.source = cell.source.id;
      }
      if (!isNullOrUndefined(cell.target)) {
        r.target = cell.target.id;
      }

      if (cell.cellType == 'inheritance') {
        if (!isNullOrUndefined(cell.source)) {
          (<Inheritance>r).superClassId = cell.source.id;
        }
      } else if (cell.cellType == 'association') {
        (<Association>r).sourceCardinality = AssociationCardinality.getAssociationParameterFromLabel(cell.getChildAt(0).value);
        (<Association>r).targetCardinality = AssociationCardinality.getAssociationParameterFromLabel(cell.getChildAt(1).value);

      } else {
        console.error("invalid cellType")
        console.log(cell);
        console.log(this.relationships);
      }

    }

    //update the configurator save file
    if (!isNullOrUndefined(this.currentConfigurator)) {
      this.currentConfigurator.classDefinitionIds = this.configurableClasses.map(c => c.id);
      this.currentConfigurator.relationshipIds = this.relationships.map(r => r.id);
    }
  }

  navigateBack() {
    window.history.back();
  }

  showSidebar() {
    this.rightSidebarContainer.nativeElement.style.background = 'rgba(214, 239, 249, 0.9)';
    this.rightSidebarVisible = true;
    this.rightSidebarContainer.nativeElement.style.borderLeft = "solid 1px rgb(160, 160, 160)";
  }


  //DEBUG 

  doShitWithGraph(graph: mxgraph.mxGraph) {
    console.log(graph);
  }
  printModelToConsole() {
    console.log(this.configurableClasses);
    console.log(this.relationships);
    console.log(this.currentConfigurator);
  }

  //form editor not accessible from the graph editor - just for debug puposes anymore
  showWorkInProgressInfo: boolean = false;
  createClassInstanceClicked(cells: myMxCell[]) {
    console.log("create class instance clicked");
    console.log(cells);

    let allCells: myMxCell[] = this.graph.getModel().getChildCells(this.graph.getDefaultParent()) as myMxCell[];
    if (isNullOrUndefined(cells) || cells.length == 0) {
      //get all cells in graph
      //find first root cell
      cells = allCells.filter((c: myMxCell) => {
        return c.root;
      });
    }

    if (allCells.filter((c: myMxCell) => { return c.newlyAdded }).length > 0) {
      console.log("you have to save first");

      //TODO
      this.showWorkInProgressInfo = true;
      let outer = this;
      setTimeout(function () {
        outer.showWorkInProgressInfo = false;
      }, 5000);
    } else {
      this.dataTransportService.data = cells;

      let params: string[] = ['test8', 'test7', 'test9'];
      this.router.navigate([`main/configurator/instance-editor/${this.marketplace.id}`], { queryParams: params });
    }
  }
}
