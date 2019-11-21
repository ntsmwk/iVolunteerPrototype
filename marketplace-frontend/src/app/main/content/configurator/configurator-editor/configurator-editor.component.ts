import { Component, OnInit, Input, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassDefinition, ClassArchetype } from 'app/main/content/_model/meta/Class';
import { mxgraph } from "mxgraph";
import { Relationship, RelationshipType, Association, AssociationCardinality, Inheritance } from 'app/main/content/_model/meta/Relationship';
import { isNullOrUndefined, isNull } from 'util';
import { DialogFactoryComponent } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { PropertyDefinition, PropertyItem, ClassProperty } from 'app/main/content/_model/meta/Property';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { fuseConfig } from 'app/fuse-config';
import { RelationshipService } from 'app/main/content/_service/meta/core/relationship/relationship.service';
import { EditorPopupMenu } from './popup-menu';
import { EventEmitter } from 'events';
import { MatSnackBar } from '@angular/material';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from '../../_service/meta/core/configurator/configurator.service';
import { resource } from 'selenium-webdriver/http';
import { ObjectUnsubscribedError } from 'rxjs';
import { DataTransportService } from '../../_service/data-transport/data-transport.service';

declare var require: any


const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

export class myMxCell extends mx.mxCell {
  cellType?: string;
  root?: boolean;
  property: boolean;
  propertyId?: string;
  newlyAdded: boolean;
}

const sidebarPalettes = [
  {
    id: 'building_blocks', label: 'Building Blocks',
    items: [
      { id: 'class', label: 'Class', imgPath: '/assets/mxgraph_resources/images/custom/class.svg', type: 'vertex', shape: undefined },
    ]
  },
  {
    id: 'relationships', label: 'Relationships',
    items: [
      { id: 'INHERITANCE', label: 'Inheritance', imgPath: '/assets/mxgraph_resources/images/custom/inheritance.svg', type: 'edge', shape: undefined },
      { id: 'ASSOCIATION', label: 'Association', imgPath: '/assets/mxgraph_resources/images/custom/association.svg', type: 'relation', shape: undefined },
    ]
  }
];



@Component({
  selector: 'app-configurator-editor',
  templateUrl: './configurator-editor.component.html',
  styleUrls: ['./configurator-editor.component.scss'],
  providers: [DialogFactoryComponent]

})
export class ConfiguratorEditorComponent implements OnInit, AfterViewInit {

  @Input() marketplace: Marketplace;

  configurableClasses: ClassDefinition[];
  deletedClassIds: string[]

  relationships: Relationship[];
  deletedRelationshipIds: string[];

  currentConfigurator: Configurator;

  isLoaded: boolean = false;
  allPropertyDefinitions: PropertyDefinition<any>[];
  sidebarPalettes = sidebarPalettes;

  popupMenu: EditorPopupMenu;

  eventResponseAction: string;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService,
    private propertyDefinitionService: PropertyDefinitionService,
    private relationshipService: RelationshipService,
    private dialogFactory: DialogFactoryComponent,
    private snackBar: MatSnackBar,
    private configuratorService: ConfiguratorService,
    private dataTransportService: DataTransportService) {

  }

  @ViewChild('graphContainer') graphContainer: ElementRef;
  @ViewChild('leftSidebarContainer') leftSidebarContainer: ElementRef;
  @ViewChild('rightSidebarContainer') rightSidebarContainer: ElementRef;

  toggleRightSidebar(show: boolean) {
    if (show) {
      this.rightSidebarContainer.nativeElement.style.display = 'block';
    } else {
      this.rightSidebarContainer.nativeElement.style.display = 'none';
    }
  }

  rightSidebarContext: string;
  currentClass: ClassDefinition;

  // propertyItems: { classId: string, propertyItems: PropertyItem[] }[];


  graph: mxgraph.mxGraph;
  standardTranslate: mxgraph.mxPoint;

  ngOnInit() {
    this.fetchPropertyDefinitions();
    this.configurableClasses = [];
    this.deletedClassIds = [];
    this.relationships = [];
    this.deletedRelationshipIds = [];

    console.log(this.configurableClasses);
    console.log(this.relationships);
  }

  ngAfterViewInit() {
    this.graphContainer.nativeElement.style.position = 'absolute';
    this.graphContainer.nativeElement.style.overflow = 'hidden';
    this.graphContainer.nativeElement.style.left = '200px';
    this.graphContainer.nativeElement.style.top = '25px';
    this.graphContainer.nativeElement.style.right = '0px';
    this.graphContainer.nativeElement.style.bottom = '0px';
    this.graphContainer.nativeElement.style.background = 'white';
    // this.graphContainer.nativeElement.style.margin = '5px';

    this.leftSidebarContainer.nativeElement.style.position = 'absolute';
    this.leftSidebarContainer.nativeElement.style.overflow = 'hidden';
    this.leftSidebarContainer.nativeElement.style.padding = '2px';
    this.leftSidebarContainer.nativeElement.style.left = '0px';
    this.leftSidebarContainer.nativeElement.style.top = '25px';
    this.leftSidebarContainer.nativeElement.style.width = '200px';
    this.leftSidebarContainer.nativeElement.style.bottom = '0px';
    this.leftSidebarContainer.nativeElement.style.background = 'lightgrey';


    this.rightSidebarContainer.nativeElement.style.position = 'absolute';
    this.rightSidebarContainer.nativeElement.style.overflow = 'hidden';
    this.rightSidebarContainer.nativeElement.style.padding = '2px';
    this.rightSidebarContainer.nativeElement.style.right = '0px';
    this.rightSidebarContainer.nativeElement.style.top = '25px';
    this.rightSidebarContainer.nativeElement.style.width = '200px';
    this.rightSidebarContainer.nativeElement.style.bottom = '0px';
    this.rightSidebarContainer.nativeElement.style.background = 'lightgrey';
    this.rightSidebarContainer.nativeElement.style.display = 'none';

    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);

    this.graph.isCellSelectable = function (cell) {
      var state = this.view.getState(cell);
      var style = (state != null) ? state.style : this.getCellStyle(cell);

      return this.isCellsSelectable() && !this.isCellLocked(cell) && style['selectable'] != 0;
    };

    this.graph.getCursorForCell = function (cell: myMxCell) {
      if (cell.cellType == 'property' || cell.cellType == 'add' || cell.cellType == 'remove') {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      }
    }

    //  this.container.nativeElement.style.background = 'url("editors/images/grid.gif")';

    // Checks if the browser is supported
    if (!mx.mxClient.isBrowserSupported()) {
      // Displays an error message if the browser is not supported.
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

      this.clearEditor();
    }
  }

  private createPopupMenu(graph) {
    this.popupMenu = new EditorPopupMenu(graph, this);
    return this.popupMenu.createPopupMenuHandler(graph);
  }

  showServerContent() {
    this.clearEditor();
    this.parseServerContent();
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

  private parseServerContent() {


    this.updateModel();


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
      console.log("???");
      console.log(this.configurableClasses.length);

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

  private insertClassIntoGraph(c: ClassDefinition, geometry: mxgraph.mxGeometry, createNew: boolean) {
    let cell = new myMxCell(c.name, geometry, 'shape=swimlane');
    cell.root = c.root;
    cell.setCollapsed(false);
    cell.cellType = 'class';
    cell.newlyAdded = createNew;
    cell.setVertex(true);
    cell.setConnectable(true);

    if (!isNullOrUndefined(c.id)) {
      cell.id = c.id;
    }

    let i = 5;
    let vfiller = this.graph.insertVertex(cell, "vfiller", null, 105, 45, 5, 130, "fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;")
    vfiller.setConnectable(false);
    cell.geometry.alternateBounds = new mx.mxRectangle(0, 0, 80, 30);
    console.log(c);
    cell.geometry.setRect(cell.geometry.x, cell.geometry.y, cell.geometry.width, c.properties.length * 20 + 25);


    if (!isNullOrUndefined(c.properties)) {

      for (let p of c.properties) {
        // cell.geometry.alternateBounds = new mx.mxRectangle(0, 0, 85, 30);

        let propertyEntry: myMxCell = this.graph.insertVertex(cell, p.id, p.name, 5, i + 45, 100, 20, "movable=0;resizable=0;editable=0;deletable=0;selectable=0;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left;html=1;overflow=hidden") as myMxCell;
        // propertyEntry.property = true;
        propertyEntry.cellType = 'property';
        propertyEntry.propertyId = p.id;
        propertyEntry.setConnectable(false);
        i = i + 20;
      }
    }

    let addIcon: myMxCell = this.graph.insertVertex(cell, "add", 'add', 5, i + 50, 20, 20, "shape=image;image=/assets/mxgraph_resources/images/add_green.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;") as myMxCell;
    addIcon.setConnectable(false);
    addIcon.cellType = 'add';

    if (c.properties.length > 0) {
      let removeIcon: myMxCell = this.graph.insertVertex(cell, "remove", 'remove', 25, i + 50, 20, 20, "shape=image;image=/assets/mxgraph_resources/images/remove_red.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;") as myMxCell;
      removeIcon.setConnectable(false);
      removeIcon.cellType = 'remove';
    }

    let hfiller = this.graph.insertVertex(cell, "hfiller", null, 0, i + 50 + 20, 85, 5, "fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;")
    hfiller.setConnectable(false);

    return this.graph.addCell(cell);
  }

  private insertRelationshipIntoGraph(r: Relationship, coords: mxgraph.mxPoint, createNew: boolean) {
    const parent = this.graph.getDefaultParent();

    var source = this.graph.getModel().getCell(r.source);
    var target = this.graph.getModel().getCell(r.target);

    var cell: myMxCell;
    if (r.relationshipType == RelationshipType.INHERITANCE) {
      cell = new myMxCell(undefined, new mx.mxGeometry(coords.x, coords.y, 0, 0), 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none;curved=1;html=1');
      cell.cellType = 'inheritance'

    } else if (r.relationshipType == RelationshipType.ASSOCIATION) {
      cell = new myMxCell('', new mx.mxGeometry(coords.x, coords.y, 0, 0), 'endArrow=none;html=1;curved=1');
      cell.cellType = 'association';

      var cell1 = new myMxCell(AssociationCardinality[(r as Association).sourceCardinality], new mx.mxGeometry(-0.8, 0, 0, 0), 'resizable=0;html=1;align=left;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;');
      cell1.geometry.relative = true;
      cell1.setConnectable(false);
      cell1.vertex = true;
      cell1.cellType = 'associationLabel';

      if (isNullOrUndefined(cell1.value)) {
        cell1.value = 'start';
      }
      cell.insert(cell1);

      var cell2 = new myMxCell(AssociationCardinality[(r as Association).targetCardinality], new mx.mxGeometry(0.8, 0, 0, 0), 'resizable=0;html=1;align=right;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;');
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

  redrawContent() {
    let bounds = this.graph.getView().getGraphBounds();
    let translate = this.graph.getView().getTranslate();

    let savedGeometry = this.saveGeometry();
    this.clearEditor();
    this.parseServerContent();
    this.restoreGeometry(savedGeometry);
    bounds.y *= -1;
    bounds.x *= -1;

    this.graph.scrollRectToVisible(bounds);
  }

  private saveGeometry(): { id: string, geometry: mxgraph.mxGeometry }[] {
    let cells = this.graph.getModel().getChildCells(this.graph.getDefaultParent());
    let savedGeometry: { id: string, geometry: mxgraph.mxGeometry }[] = [];
    for (let cell of cells) {
      savedGeometry.push({ id: cell.id, geometry: cell.geometry });
    }
    return savedGeometry;
  }

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
    var cell: mxgraph.mxCell = event.getProperty("cell");

    if (!isNullOrUndefined(cell)) {
      var parent = cell.getParent();

      if (cell.value == "add") {
        for (let c of this.configurableClasses) {
          if (c.id == parent.id) {
            this.dialogFactory.addPropertyDialogGeneric(this.allPropertyDefinitions, c.properties).then((props: PropertyItem[]) => {
              if (!isNullOrUndefined(props)) {
                this.classDefinitionService.getClassPropertyFromPropertyDefinitionById(this.marketplace, props.map(props => props.id)).toPromise().then((ret: ClassProperty<any>[]) => {
                  c.properties.push(...ret);
                  this.redrawContent();
                });
              }
            });
            break;
          }
        }
      }

      if (cell.value == "remove") {
        console.log("clicked remove");
        for (let c of this.configurableClasses) {
          if (c.id == parent.id) {
            this.dialogFactory.removePropertyDialogGeneric(c.properties).then((props: PropertyItem[]) => {
              let rest = c.properties.filter((p: PropertyItem) => {
                return !(props.indexOf(p) >= 0);
              });

              c.properties = rest;
              this.redrawContent();
            });
            break;
          }
        }
      }
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
        console.log("ending update");
        graph.getModel().endUpdate();
        removeEventListeners(outer);
      }

      function addObjectToGraph(dragEndEvent: MouseEvent, paletteItem: any, graph: mxgraph.mxGraph) {

        const coords: mxgraph.mxPoint = graph.getPointForEvent(positionEvent, false);
        graph.getModel().beginUpdate();
        if (paletteItem.id == 'class') {

          let addedClass = new ClassDefinition();
          addedClass.name = "Class";
          addedClass.properties = [];

          //TODO @Alex change to correct archetype
          addedClass.classArchetype = ClassArchetype.COMPETENCE;

          let cell = outer.insertClassIntoGraph(addedClass, new mx.mxGeometry(coords.x, coords.y, 80, 30), true);
          cell.id = "new" + cell.id;
          addedClass.id = cell.id;
          outer.configurableClasses.push(addedClass);

        } else {
          let r = new Relationship();
          r.relationshipType = paletteItem.id;
          let cell = outer.insertRelationshipIntoGraph(r, coords, true);

          cell.id = "new" + cell.id;
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

  doShitWithGraph(graph: mxgraph.mxGraph) {
    console.log(graph);
  }
  printModelToConsole() {
    console.log(this.configurableClasses);
    console.log(this.relationships);
    console.log(this.currentConfigurator);
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
        console.log(result);
        relSaveSuccess = !isNullOrUndefined(result);
      }),

      this.classDefinitionService.addOrUpdateClassDefintions(this.marketplace, this.configurableClasses).toPromise().then((result: any) => {
        console.log(result);
        classSaveSuccess = !isNullOrUndefined(result);

      }),
      this.classDefinitionService.deleteClassDefinitions(this.marketplace, this.deletedClassIds).toPromise().then((result: any) => {
        this.deletedClassIds = [];
        deletedClassSaveSuccess = true;
        console.log(result);
      }),

      this.relationshipService.deleteRelationships(this.marketplace, this.deletedRelationshipIds).toPromise().then((result: any) => {
        this.deletedRelationshipIds = [];
        deletedRelSaveSuccess = true;
      }),

    ]).then(() => {

      this.configuratorService.saveConfigurator(this.marketplace, this.currentConfigurator).toPromise().then((result: any) => {
        console.log(result);
        configuratorSaveSuccess = true;

      }).then(() => {
        let snackBarMessage: string;
        if (relSaveSuccess && classSaveSuccess && deletedClassSaveSuccess && deletedRelSaveSuccess) {
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
    this.clearEditor();
    this.configurableClasses = [];
    this.relationships = [];
    this.currentConfigurator = undefined;
  }

  openGraph(configurator: Configurator) {
    this.currentConfigurator = configurator;
    console.log(configurator);
    Promise.all([
      // grab classDefinitionss from server
      this.classDefinitionService.getClassDefinitionsById(this.marketplace, configurator.classDefinitionIds).toPromise().then((classDefinitions: ClassDefinition[]) => {
        if (!isNullOrUndefined(classDefinitions)) {
          this.configurableClasses = classDefinitions;
        } else {
          this.configurableClasses = [];
        }
        console.log(classDefinitions);
      }),

      //grab relationships from Server
      this.relationshipService.getRelationshipsById(this.marketplace, configurator.relationshipIds).toPromise().then((relationships: Relationship[]) => {
        if (!isNullOrUndefined(relationships)) {
          this.relationships = relationships;
        } else {
          this.relationships = [];
        }
        console.log(relationships);
      })
    ]).then(() => {
      //draw graph
      this.showServerContent();
    });

  }

  updateModel() {
    //store current connections in relationships

    for (let c of this.configurableClasses) {
      let cell: myMxCell = this.graph.getModel().getCell("" + c.id) as myMxCell;

      if (!isNullOrUndefined(cell)) {
        if ((cell.cellType == 'class')) {
          c.root = cell.root;
          c.name = cell.value;
        }
      }
    }
    for (let r of this.relationships) {
      let cell: myMxCell = this.graph.getModel().getCell("" + r.id) as myMxCell;

      if (!isNullOrUndefined(cell)) {
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
        }
      }
    }

    //update the configurator save file
    if (!isNullOrUndefined(this.currentConfigurator)) {
      this.currentConfigurator.classDefinitionIds = this.configurableClasses.map(c => c.id);
      this.currentConfigurator.relationshipIds = this.relationships.map(r => r.id);
    }
  }

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
      this.router.navigate([`main/configurator/instance-editor/${this.marketplace.id}`]);
    }
  }


  fetchPropertyDefinitions() {
    this.propertyDefinitionService.getAllPropertyDefinitons(this.marketplace).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
      if (!isNullOrUndefined(propertyDefinitions)) {
        this.allPropertyDefinitions = propertyDefinitions;
      }
    });
  }




  consumeMenuOptionClickedEvent(event: any) {
    console.log("MenuEventReceived");
    console.log(event);

    switch (event.id) {
      case 'editor_save': {
        if (isNullOrUndefined(this.currentConfigurator)) {
          this.eventResponseAction = "saveAsClicked"
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
      }
    }
  }


  /**
   * SIDEBAR
   */

  // propertiesAdded: boolean = false;

  // // addPropertyClicked(currentClass: ClassDefintion) {
  // //   if (isNullOrUndefined(this.allPropertyDefinitions)) {
  // //     this.propertyDefinitionService.getAllPropertyDefinitons(this.marketplace).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
  // //       if (!isNullOrUndefined(propertyDefinitions)) {
  // //         this.allPropertyDefinitions = propertyDefinitions;
  // //         this.displayAddPropertyDialog(currentClass);
  // //       }
  // //     });
  // //   } else {
  // //     this.displayAddPropertyDialog(currentClass);
  // //   }
  // // }

  // // private displayAddPropertyDialog(currentClass: ClassDefintion) {
  // //   this.dialogFactory.addPropertyDialogGeneric(this.allPropertyDefinitions, this.currentClass.properties).then((ret: PropertyItem[]) => {
  // //     if (!isNullOrUndefined(ret)) {

  // //       let foundItem = this.propertyItems.find((item) => {
  // //         return item.classId == currentClass.id
  // //       });

  // //       foundItem.propertyItems.push(...ret);
  // //       this.propertiesAdded = true;
  // //     }
  // //   });

  // // }

  // saveAddedProperties() {
  //   // this.classDefinitionService.savePropertiesLegacy(this.marketplace, this.currentClass.id, this.currentClass.properties).toPromise().then((ret: ClassDefintion) => {
  //   //   console.log("saveaddedPropertieslegacy")
  //   //   console.log(ret);
  //   //   this.propertiesAdded = false;

  //   //   if (isNullOrUndefined(ret)) {
  //   //     this.showWorkInProgressInfo = true;
  //   //     let outer = this;
  //   //     setTimeout(function () {
  //   //       outer.showWorkInProgressInfo = false;
  //   //     }, 5000);
  //   //   }

  //   // });
  // }

  // removePropertyClicked(currentClass: ClassDefinition, index: number) {
  //   currentClass.properties.splice(index, 1);
  // }

  navigateBack() {
    window.history.back();
  }

}
