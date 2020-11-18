import { mxgraph } from 'mxgraph';
import { Component, OnInit, AfterContentInit, Input, ViewChild, ElementRef, HostListener } from '@angular/core';
import { DialogFactoryDirective } from '../../_shared/dialogs/_dialog-factory/dialog-factory.component';
import { Router, ActivatedRoute } from '@angular/router';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { ClassDefinition, ClassArchetype } from 'app/main/content/_model/configurator/class';
import { Relationship, RelationshipType, AssociationCardinality } from 'app/main/content/_model/configurator/relationship';
import { ClassConfiguration } from 'app/main/content/_model/configurator/configurations';
import { EditorPopupMenu } from './popup-menu';
import { TopMenuResponse } from './top-menu-bar/top-menu-bar.component';
import { MyMxCell, MyMxCellType } from '../myMxCell';
import { CConstants } from './utils-and-constants';
import { ClassProperty, PropertyType } from 'app/main/content/_model/configurator/property/property';
import { isNullOrUndefined } from 'util';
import { OptionsOverlayContentData } from './options-overlay/options-overlay-control/options-overlay-control.component';

declare var require: any;
const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

@Component({
  selector: "app-class-configurator",
  templateUrl: './class-configurator.component.html',
  styleUrls: ['./class-configurator.component.scss'],
  providers: [DialogFactoryDirective]
})
export class ClassConfiguratorComponent implements OnInit, AfterContentInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private objectIdService: ObjectIdService,
    private dialogFactory: DialogFactoryDirective
  ) { }

  @ViewChild('graphContainer', { static: true }) graphContainer: ElementRef;
  @ViewChild('rightSidebarContainer', { static: true })
  rightSidebarContainer: ElementRef;

  classDefinitions: ClassDefinition[];
  deletedClassIds: string[];
  relationships: Relationship[];
  deletedRelationshipIds: string[];
  classConfiguration: ClassConfiguration;

  modelUpdated: boolean;
  currentSelectedCell: MyMxCell;

  popupMenu: EditorPopupMenu;
  layout: any;
  graph: mxgraph.mxGraph;

  rootCell: MyMxCell;
  rootCellSet: boolean;
  eventResponse: TopMenuResponse;

  relationshipType: RelationshipType;

  rightSidebarVisible: boolean;
  quickEditMode: boolean;
  clickToDeleteMode: boolean;
  confirmDelete: boolean;
  deleteRelationships: boolean;
  displayOverlay: boolean;

  overlayType: 'CLASS' | 'RELATIONSHIP';
  overlayContent: OptionsOverlayContentData;
  overlayEvent: PointerEvent;

  @Input() tenantId: string;
  @Input() redirectUrl: string;

  /**
   * ******INITIALIZATION******
   */
  ngOnInit() {
    this.classDefinitions = [];
    this.deletedClassIds = [];
    this.relationships = [];
    this.deletedRelationshipIds = [];
    this.rightSidebarVisible = true;
    this.eventResponse = new TopMenuResponse();
    this.relationshipType = RelationshipType.INHERITANCE;
    this.confirmDelete = true;
    this.deleteRelationships = true;
    this.clickToDeleteMode = false;
    this.quickEditMode = false;
  }

  ngAfterContentInit() {
    if (!mx.mxClient.isBrowserSupported()) {
      mx.mxUtils.error('Browser is not supported!', 200, false);
      return;
    }

    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);
    this.graph.isCellSelectable = function (cell) {
      const state = this.view.getState(cell);
      const style = state != null ? state.style : this.getCellStyle(cell);
      return (
        this.isCellsSelectable() &&
        !this.isCellLocked(cell) &&
        style['selectable'] !== 0
      );
    };

    this.graph.getCursorForCell = (cell: MyMxCell) => {
      if (
        cell.cellType === MyMxCellType.FLAT_PROPERTY ||
        cell.cellType === MyMxCellType.ADD_PROPERTY_ICON ||
        cell.cellType === MyMxCellType.REMOVE_ICON ||
        cell.cellType === MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON ||
        cell.cellType === MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON ||
        cell.cellType === MyMxCellType.ADD_ASSOCIATION_ICON ||
        cell.cellType === MyMxCellType.OPTIONS_ICON ||
        (this.clickToDeleteMode && cell.cellType === MyMxCellType.CLASS) ||
        (this.clickToDeleteMode && MyMxCellType.isRelationship(cell.cellType))
      ) {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      }
    };

    const modelGetStyle = this.graph.model.getStyle;
    this.graph.model.getStyle = function (cell) {
      if (cell === null) {
        return null;
      }
      let style = modelGetStyle.apply(this, arguments);
      if (this.isCollapsed(cell)) {
        style = style + ';shape=rectangle';
      }
      return style;
    };

    mx.mxEvent.disableContextMenu(this.graphContainer.nativeElement);
    // tslint:disable-next-line: no-unused-expression
    new mx.mxRubberband(this.graph);

    this.graph.setPanning(true);
    this.graph.popupMenuHandler = this.createPopupMenu(this.graph);
    this.graph.tooltipHandler = new mx.mxTooltipHandler(this.graph, 100);

    /**
     * ******EVENT LISTENERS******
     */
    this.graph.addListener(
      mx.mxEvent.CLICK,
      (sender: mxgraph.mxGraph, evt: mxgraph.mxEventObject) => {
        const mouseEvent = evt.getProperty('event');
        this.clickToDeleteMode && mouseEvent.button === 0
          ? this.handleClickToDeleteEvent(evt)
          : mouseEvent.button === 0 && this.graph.enabled
            ? this.handleMXGraphLeftClickEvent(evt)
            : '';
      }
    );

    this.graph.addListener(
      mx.mxEvent.FOLD_CELLS,
      (sender: mxgraph.mxGraph, evt: mxgraph.mxEventObject) => {
        const cells: MyMxCell[] = evt.getProperty('cells');
        const cell = cells.pop();
        this.handleMXGraphFoldEvent(cell);
      }
    );

    this.graph.addListener(
      mx.mxEvent.DOUBLE_CLICK,
      (sender: mxgraph.mxGraph, evt: mxgraph.mxEventObject) => {
        this.handleMXGraphDoubleClickEvent(evt);
      }
    );

    this.openPreviousClassConfiguration();
  }

  private createPopupMenu(graph: mxgraph.mxGraph) {
    this.popupMenu = new EditorPopupMenu(this);
    return this.popupMenu.createPopupMenuHandler(graph);
  }

  /**
   * ******CONTENT-RELATED FUNCTIONS******
   */
  private loadServerContent() {
    this.parseGraphContent();
    if (isNullOrUndefined(this.layout)) {
      this.setLayout();
    }
    this.executeLayout();
    this.modelUpdated = true;
  }

  private clearEditor() {
    this.rootCellSet = false;
    this.rootCell = undefined;
    this.graph.getModel().beginUpdate();
    try {
      this.graph.getModel().clear();
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private parseGraphContent() {
    this.parseIncomingClasses();
    this.parseIncomingRelationships();
    this.rootCellSet = false;
  }

  /**
   * ******CLASSES******
   */
  private parseIncomingClasses() {
    try {
      this.graph.getModel().beginUpdate();
      for (const c of this.classDefinitions) {
        this.insertClassIntoGraph(c);
      }
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private insertClassIntoGraph(
    classDefinition: ClassDefinition,
    geometry?: mxgraph.mxGeometry,
    replaceCell?: MyMxCell
  ) {
    let cell: MyMxCell;
    const style = CConstants.mxStyles.classNormal;
    if (isNullOrUndefined(geometry)) {
      geometry = new mx.mxGeometry(0, 0, 110, 45);
    }

    if (!isNullOrUndefined(replaceCell)) {
      cell = replaceCell;
      const childCells = this.graph.removeCellsFromParent(
        this.graph.getChildCells(cell)
      );
      this.graph.removeCells(childCells, false);
      this.graph.setCellStyle(style, [cell]);
    } else {
      cell = new mx.mxCell(classDefinition.name, geometry, style) as MyMxCell;
    }

    cell.root = classDefinition.root;
    cell.writeProtected = classDefinition.writeProtected;

    if (cell.root) {
      if (!this.rootCellSet) {
        this.rootCell = cell;
        cell.root = true;
        this.rootCellSet = true;
      } else {
        cell.root = false;
        console.error(
          'Root cell already set - only one root cell per graph allowed'
        );
      }
    }

    cell.setCollapsed(false);
    cell.cellType = MyMxCellType.CLASS;
    cell.classArchetype = classDefinition.classArchetype;
    cell.value = classDefinition.name;
    cell.setVertex(true);
    cell.setConnectable(true);

    if (!isNullOrUndefined(classDefinition.imagePath)) {
      const overlay = new mx.mxCellOverlay(
        new mx.mxImage(classDefinition.imagePath, 30, 30),
        'Overlay',
        mx.mxConstants.ALIGN_RIGHT,
        mx.mxConstants.ALIGN_TOP
      );
      this.graph.addCellOverlay(cell, overlay);
    }
    cell.id = !isNullOrUndefined(classDefinition.id)
      ? classDefinition.id
      : this.objectIdService.getNewObjectId();

    cell.geometry.alternateBounds = new mx.mxRectangle(0, 0, 110, 50);
    cell.geometry.setRect(
      cell.geometry.x,
      cell.geometry.y,
      cell.geometry.width,
      classDefinition.properties.length * 20 + 80
    );

    // create properties
    let yLocation = 5;
    yLocation = this.addPropertiesToCell(
      cell,
      classDefinition.properties,
      yLocation
    );

    if (cell.classArchetype !== ClassArchetype.ROOT) {
      // next icon
      const nextIcon: MyMxCell = this.graph.insertVertex(
        cell,
        'add_class_same_level_icon',
        'Partnerklasse hinzufügen',
        85,
        yLocation + 50,
        20,
        20,
        CConstants.mxStyles.addClassSameLevelIcon
      ) as MyMxCell;
      nextIcon.setConnectable(false);
      nextIcon.cellType = MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON;

      // down icon
      const downIcon: MyMxCell = this.graph.insertVertex(
        cell,
        'add_class_next_level_icon',
        'Unterklasse hinzufügen',
        65,
        yLocation + 50,
        20,
        20,
        CConstants.mxStyles.addClassNewLevelIcon
      ) as MyMxCell;
      downIcon.setConnectable(false);
      downIcon.cellType = MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON;
    }

    // options icon
    const optionsIcon: MyMxCell = this.graph.insertVertex(
      cell,
      'options',
      'Optionen',
      5,
      yLocation + 50,
      20,
      20,
      CConstants.mxStyles.optionsIcon
    ) as MyMxCell;
    optionsIcon.setConnectable(false);
    optionsIcon.cellType = MyMxCellType.OPTIONS_ICON;

    return isNullOrUndefined(replaceCell) ? this.graph.addCell(cell) : cell;
  }

  /**
   * ******PROPERTIES******
   */
  private addPropertiesToCell(
    cell: MyMxCell,
    properties: ClassProperty<any>[],
    yLocation: number
  ): number {
    for (const p of properties) {
      let propertyCell: MyMxCell;
      if (p.type !== PropertyType.TREE) {
        propertyCell = this.graph.insertVertex(
          cell,
          p.id,
          p.name,
          5,
          yLocation + 45,
          100,
          20,
          CConstants.mxStyles.property
        ) as MyMxCell;
        propertyCell.cellType = MyMxCellType.FLAT_PROPERTY;
      } else {
        propertyCell = this.graph.insertVertex(
          cell,
          p.id,
          p.name,
          5,
          yLocation + 45,
          100,
          20,
          CConstants.mxStyles.propertyTree
        ) as MyMxCell;
        propertyCell.cellType = MyMxCellType.TREE_PROPERTY;
      }

      propertyCell.setConnectable(false);
      yLocation += 20;
    }
    return yLocation;
  }

  /**
   * ******RELATIONSHIPS******
   */

  private parseIncomingRelationships() {
    try {
      this.graph.getModel().beginUpdate();
      for (let r of this.relationships) {
        r = this.convertAggregation(r);
        this.insertRelationshipIntoGraph(r, new mx.mxPoint(0, 0));
      }
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private insertRelationshipIntoGraph(
    r: Relationship,
    coords: mxgraph.mxPoint
  ) {
    const parent = this.graph.getDefaultParent();
    const source: MyMxCell = this.graph
      .getModel()
      .getCell(r.source) as MyMxCell;
    const target: MyMxCell = this.graph
      .getModel()
      .getCell(r.target) as MyMxCell;

    let style: string;
    let type: MyMxCellType;

    if (r.relationshipType === RelationshipType.INHERITANCE) {
      style = CConstants.mxStyles.inheritance;
      type = MyMxCellType.INHERITANCE;
    } else if (r.relationshipType === RelationshipType.ASSOCIATION) {
      style = CConstants.mxStyles.association;
      type = MyMxCellType.ASSOCIATION;
    } else {
      console.error('Invalid Relationshiptype');
    }

    const geometry = new mx.mxGeometry(coords.x, coords.y, 0, 0);
    const cell = new mx.mxCell(undefined, geometry, style) as MyMxCell;

    cell.id = r.id;
    cell.geometry.relative = true;
    cell.edge = true;
    cell.cellType = type;
    cell.writeProtected = target.writeProtected;

    return this.graph.addEdge(cell, parent, source, target);
  }

  private updateRelationshipInGraph(relationship: Relationship) {
    let style: string;
    let type: MyMxCellType;

    if (relationship.relationshipType === RelationshipType.INHERITANCE) {
      style = CConstants.mxStyles.inheritance;
      type = MyMxCellType.INHERITANCE;
    } else if (relationship.relationshipType === RelationshipType.ASSOCIATION) {
      style = CConstants.mxStyles.association;
      type = MyMxCellType.ASSOCIATION;
    } else {
      console.error('Invalid Relationshiptype');
    }

    this.graph
      .getModel()
      .getCell(relationship.id)
      .setStyle(style);
    (this.graph
      .getModel()
      .getCell(relationship.id) as MyMxCell).cellType = type;
  }

  /**
   * ******DELETE******
   */
  private deleteCells(cells: MyMxCell[]) {
    // if (cells.length === 1 && cells[0].writeProtected) { return; }
    if (this.confirmDelete) {
      this.dialogFactory
        .confirmationDialog('Löschen Bestätigen', 'Wirklich löschen?')
        .then((ret: boolean) => {
          ret ? this.performDelete(cells) : '';
        });
    } else {
      this.performDelete(cells);
    }
  }

  private performDelete(cells: MyMxCell[]) {
    // cells = cells.filter((c: MyMxCell) => !c.writeProtected);
    const removedCells = this.graph.removeCells(
      cells,
      this.deleteRelationships
    ) as MyMxCell[];

    !isNullOrUndefined(removedCells) ? this.deleteFromModel(removedCells) : '';
  }

  private deleteFromModel(removedCells: MyMxCell[]) {
    for (const cell of removedCells) {
      if (cell.cellType === MyMxCellType.CLASS) {
        this.classDefinitions = this.classDefinitions.filter(
          c => c.id !== cell.id
        );
        this.deletedClassIds.push(cell.id);
      } else if (MyMxCellType.isRelationship(cell.cellType)) {
        this.relationships = this.relationships.filter(r => r.id !== cell.id);
        this.deletedRelationshipIds.push(cell.id);
      }
    }
  }

  /**
   * ******LAYOUT AND DRAWING******
   */
  private setLayout() {
    this.layout = new mx.mxCompactTreeLayout(this.graph, false, false);
    this.layout.levelDistance = 50;
    this.layout.alignRanks = true;
    this.layout.minEdgeJetty = 50;
    this.layout.prefHozEdgeSep = 5;
    this.layout.resetEdges = true;
    this.layout.edgeRouting = true;
  }

  private executeLayout() {
    this.layout.execute(this.graph.getDefaultParent(), this.rootCell);
  }

  /**
   * ******EVENT HANDLING******
   */
  handleMXGraphLeftClickEvent(event: mxgraph.mxEventObject) {
    const cell: MyMxCell = event.getProperty('cell');
    this.currentSelectedCell = cell;

    if (isNullOrUndefined(cell)) {
      return;
    }

    const parent = cell.getParent();

    if (cell.cellType === MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON) {
      const ret = this.addClassWithRelationship(
        cell,
        this.graph.getIncomingEdges(parent)[0].source.id
      );
      const addedClass = ret.class;
      const addedRelationship = ret.relationship;

      this.insertClassIntoGraph(
        addedClass,
        new mx.mxGeometry(parent.geometry.x + 130, parent.geometry.y, 110, 45)
      );
      this.classDefinitions.push(addedClass);

      this.insertRelationshipIntoGraph(addedRelationship, new mx.mxPoint(0, 0));
      this.relationships.push(addedRelationship);

      if (!this.quickEditMode) {
        this.executeLayout();
      }
    }

    if (cell.cellType === MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON) {
      const ret = this.addClassWithRelationship(cell, parent.id);
      const addedClass = ret.class;
      const addedRelationship = ret.relationship;

      this.insertClassIntoGraph(
        addedClass,
        new mx.mxGeometry(
          parent.geometry.x,
          parent.geometry.y + parent.geometry.height + 20,
          110,
          45
        )
      );
      this.classDefinitions.push(addedClass);

      this.insertRelationshipIntoGraph(addedRelationship, new mx.mxPoint(0, 0));
      this.relationships.push(addedRelationship);

      if (!this.quickEditMode) {
        this.executeLayout();
      }
    }

    if (cell.cellType === MyMxCellType.OPTIONS_ICON) {
      this.openOverlay(<MyMxCell>cell.getParent(), event);
    }
  }

  addClassWithRelationship(iconCell: MyMxCell, sourceId: string
  ): { class: ClassDefinition; relationship: Relationship } {
    const addedClass = new ClassDefinition();
    addedClass.configurationId = this.classConfiguration.id;

    const parentClassArchetype = (iconCell.getParent() as MyMxCell).classArchetype;

    addedClass.id = this.objectIdService.getNewObjectId();

    addedClass.classArchetype = parentClassArchetype;
    addedClass.name =
      'Neue Klasse\n(' +
      ClassArchetype.getClassArchetypeLabel(addedClass.classArchetype) +
      ')';
    addedClass.tenantId = this.tenantId;
    addedClass.properties = [];

    const addedRelationship = new Relationship();
    addedRelationship.relationshipType = this.relationshipType;

    if (addedRelationship.relationshipType === RelationshipType.ASSOCIATION) {
      addedRelationship.sourceCardinality = AssociationCardinality.ONE;
      addedRelationship.targetCardinality = AssociationCardinality.ONE;
    }
    addedRelationship.source = sourceId;
    addedRelationship.target = addedClass.id;

    addedRelationship.id = this.objectIdService.getNewObjectId();

    return { class: addedClass, relationship: addedRelationship };
  }

  handleMXGraphDoubleClickEvent(event: mxgraph.mxEventObject) {
    this.openOverlay(event.getProperty('cell'), event);
  }

  handleMXGraphFoldEvent(cell: MyMxCell) {
    const edges: MyMxCell[] = this.graph.getOutgoingEdges(cell) as MyMxCell[];

    if (isNullOrUndefined(edges)) {
      return;
    }

    for (const edge of edges) {
      if (isNullOrUndefined(edge) || isNullOrUndefined(edge.target)) {
        continue;
      }
      if (cell.isCollapsed()) {
        this.setAllCellsInvisibleRec(cell);
      }
      if (edge.target.isCollapsed() && !cell.isCollapsed()) {
        this.setNextCellVisible(cell);
      }
    }
    this.executeLayout();
    this.modelUpdated = true;
  }

  handleClickToDeleteEvent(event: mxgraph.mxEventObject) {
    const cell = event.getProperty('cell') as MyMxCell;

    if (!isNullOrUndefined(cell)) {
      if (
        cell.cellType === MyMxCellType.CLASS ||
        MyMxCellType.isRelationship(cell.cellType)
      ) {
        this.deleteCells([cell]);
      }
    }
  }

  clickToDeleteModeToggled() {
    this.graph.setEnabled(!this.clickToDeleteMode);
  }

  /**
   * ******OPTIONS OVERLAY******
   */
  private openOverlay(cell: MyMxCell, event: mxgraph.mxEventObject) {
    if (
      !isNullOrUndefined(cell) &&
      (cell.cellType === MyMxCellType.CLASS ||
        MyMxCellType.isRelationship(cell.cellType))
    ) {
      this.overlayEvent = event.getProperty('event');

      this.overlayContent = new OptionsOverlayContentData();
      this.overlayContent.allClassDefinitions = this.classDefinitions;
      this.overlayContent.tenantId = this.tenantId;

      if (cell.cellType === MyMxCellType.CLASS) {
        this.overlayType = 'CLASS';
        this.overlayContent.classDefinition = this.classDefinitions.find(
          c => c.id === cell.id
        );
        this.overlayContent.allRelationships = this.relationships;
      } else if (MyMxCellType.isRelationship(cell.cellType)) {
        this.overlayContent.relationship = this.relationships.find(
          r => r.id === cell.id
        );
        this.overlayType = 'RELATIONSHIP';
      }

      this.graph.setEnabled(false);
      this.displayOverlay = true;
    }
  }

  handleOverlayClosedEvent(event: OptionsOverlayContentData) {
    this.graph.setEnabled(true);
    this.displayOverlay = false;

    // tslint:disable-next-line: no-unused-expression
    !isNullOrUndefined(event)
      ? this.handleModelChanges(event, this.overlayType)
      : '';

    this.overlayContent = undefined;
    this.overlayType = undefined;
    this.overlayEvent = undefined;
  }

  handleModelChanges(
    overlayData: OptionsOverlayContentData,
    overlayType: 'CLASS' | 'RELATIONSHIP'
  ) {
    let redrawCell: MyMxCell;

    if (overlayType === 'CLASS') {
      redrawCell = this.handleClassDefinitionModelChanges(
        overlayData.classDefinition
      );
    } else if (overlayType === 'RELATIONSHIP') {
      redrawCell = this.handleRelationshipModelChanges(
        overlayData.relationship
      );
    } else {
      console.error('invalid overlayType: ' + overlayType);
    }
    this.executeLayout();
  }

  private handleClassDefinitionModelChanges(classDefinition: ClassDefinition) {
    const i = this.classDefinitions.findIndex(c => c.id === classDefinition.id);
    const existingClassDefinition = this.classDefinitions[i];
    const cell = this.graph.getModel().getCell(classDefinition.id) as MyMxCell;

    try {
      this.graph.getModel().beginUpdate();
      this.insertClassIntoGraph(classDefinition, undefined, cell);
    } finally {
      this.graph.getModel().endUpdate();
    }

    this.classDefinitions[i] = classDefinition;
    if (
      !this.quickEditMode &&
      classDefinition.properties.length !==
      existingClassDefinition.properties.length
    ) {
      return cell;
    }
  }

  private handleRelationshipModelChanges(relationship: Relationship) {
    const i = this.relationships.findIndex(r => r.id === relationship.id);
    const cell = this.graph.getModel().getCell(relationship.id) as MyMxCell;

    try {
      this.graph.getModel().beginUpdate();
      this.updateRelationshipInGraph(relationship);
    } finally {
      this.graph.getModel().endUpdate();
    }
    this.relationships[i] = relationship;

    if (!this.quickEditMode) {
      return cell;
    }
  }

  /**
   * ******COLLAPSING******
   */
  private setAllCellsInvisibleRec(cell: MyMxCell) {
    const edges: MyMxCell[] = this.graph.getOutgoingEdges(cell) as MyMxCell[];
    for (const edge of edges) {
      if (!edge.target.isCollapsed()) {
        this.graph.swapBounds(edge.target, true);
        this.graph.getModel().setCollapsed(edge.target, true);
      }
      this.graph.getModel().setVisible(edge.target, false);
      this.setAllCellsInvisibleRec(edge.target as MyMxCell);
    }
  }

  private setNextCellVisible(cell: MyMxCell) {
    const edges: MyMxCell[] = this.graph.getOutgoingEdges(cell) as MyMxCell[];
    for (const edge of edges) {
      this.graph.getModel().setVisible(edge.target, true);
    }
  }

  /**
   * ******ZOOMING******
   */
  zoomInEvent() {
    this.graph.zoomIn();
  }

  zoomOutEvent() {
    this.graph.zoomOut();
  }

  zoomResetEvent() {
    this.graph.zoomActual();
    this.resetViewport();
  }

  resetViewport() {
    const rootCell = this.graph
      .getModel()
      .getChildCells(this.graph.getDefaultParent())
      .find((c: MyMxCell) => c.root);

    if (!isNullOrUndefined(rootCell)) {
      this.graph.scrollCellToVisible(rootCell, true);
      const translate = this.graph.view.getTranslate();
      const windowHeight = window.innerHeight;
      this.graph.view.setTranslate(
        translate.x,
        translate.y - windowHeight / 2 + (rootCell.geometry.height + 25)
      );
    }
  }

  /**
   * ******MENU FUNCTIONS******
   */
  async consumeMenuOptionClickedEvent(event: any) {
    this.eventResponse = new TopMenuResponse();
    switch (event.id) {
      case 'editor_save': {
        this.updateModel();
        this.createSaveEvent(event);
        break;
      }
      case 'editor_save_return': {
        this.openGraph(
          event.payload.classConfiguration,
          event.payload.classDefinitions,
          event.payload.relationships
        );
        break;
      }
      case 'editor_save_as': {
        console.error('not implemented');
        break;
      }
      case 'editor_new': {
        this.openGraph(
          event.payload.classConfiguration,
          event.payload.classDefinitions,
          event.payload.relationships
        );
        break;
      }
      case 'editor_open': {
        this.openGraph(
          event.payload.classConfiguration,
          event.payload.classDefinitions,
          event.payload.relationships
        );
        break;
      }
      case 'editor_delete': {
        if (
          !isNullOrUndefined(event.payload.idsToDelete) &&
          !isNullOrUndefined(this.classConfiguration) &&
          event.payload.idsToDelete.find(
            (id: string) => id === this.classConfiguration.id
          )
        ) {
          this.openGraph(undefined, [], []);
        }
        break;
      }
      case 'editor_create_instance': {
        this.showInstanceForm();
        break;
      }
      case 'editor_meta_edit': {
        this.classConfiguration.name = event.payload.name;
        this.classConfiguration.description = event.payload.description;
        break;
      }
      case 'cancelled': {
        break;
      }
    }
    return;
  }

  private openPreviousClassConfiguration() {
    let classConfigurationId: string;
    this.route.queryParams.subscribe(params => {
      classConfigurationId = params['ccId'];
    });
    if (!isNullOrUndefined(classConfigurationId)) {
      this.createOpenClassConfigurationByIdEvent(classConfigurationId);
    }
  }

  private createOpenClassConfigurationByIdEvent(id: string) {
    this.eventResponse = new TopMenuResponse();
    this.eventResponse.action = 'open';
    this.eventResponse.classConfigurationId = id;
    this.eventResponse.tenantId = this.tenantId;

  }

  private createSaveEvent(event: any) {
    this.eventResponse.action = 'save';
    this.eventResponse.classConfiguration = this.classConfiguration;
    this.eventResponse.followingAction = event.followingAction;
    this.eventResponse.classDefintions = this.classDefinitions;
    this.eventResponse.relationships = this.relationships;
    this.eventResponse.deletedClassDefinitions = this.deletedClassIds;
    this.eventResponse.deletedRelationships = this.deletedRelationshipIds;
    this.eventResponse.tenantId = this.tenantId;
    this.eventResponse.redirectUrl = this.redirectUrl;
  }

  private openGraph(
    classConfiguration: ClassConfiguration,
    classDefinitions: ClassDefinition[],
    relationships: Relationship[]
  ) {
    this.classConfiguration = classConfiguration;
    this.relationships = relationships;
    this.classDefinitions = classDefinitions;
    this.deletedClassIds = [];
    this.deletedRelationshipIds = [];
    this.clearEditor();

    if (!isNullOrUndefined(classConfiguration)) {
      this.loadServerContent();
    }
  }

  private updateModel() {
    // store current connections in relationships
    const allCells = this.graph
      .getModel()
      .getChildren(this.graph.getDefaultParent());
    for (const cd of this.classDefinitions) {
      const cell: MyMxCell = allCells.find(
        (c: mxgraph.mxCell) => c.id === cd.id
      ) as MyMxCell;

      if (!isNullOrUndefined(cell) && cell.cellType === MyMxCellType.CLASS) {
        cd.root = cell.root;
        cd.name = cell.value;
      }
    }

    for (const r of this.relationships) {
      const cell: MyMxCell = allCells.find(
        (c: mxgraph.mxCell) => c.id === r.id
      ) as MyMxCell;

      if (!isNullOrUndefined(cell.source)) {
        r.source = cell.source.id;
      }
      if (!isNullOrUndefined(cell.target)) {
        r.target = cell.target.id;
      }
    }

    // update the configurator save file
    if (!isNullOrUndefined(this.classConfiguration)) {
      this.classConfiguration.classDefinitionIds = this.classDefinitions.map(
        c => c.id
      );
      this.classConfiguration.relationshipIds = this.relationships.map(
        r => r.id
      );
    }
  }

  showSidebar() {
    this.rightSidebarVisible = true;
    this.rightSidebarContainer.nativeElement.style.borderLeft =
      'solid 2px black';
    this.rightSidebarContainer.nativeElement.style.height = 'auto';
    this.rightSidebarContainer.nativeElement.style.width = '300px';
  }

  hideSidebar() {
    this.rightSidebarVisible = false;
    this.rightSidebarContainer.nativeElement.style.borderLeft = 'none';
    this.rightSidebarContainer.nativeElement.style.height = '35px';
    this.rightSidebarContainer.nativeElement.style.width = '35px';
  }

  /**
   * ******INSTANTIATION******
   */
  showInstanceForm() {
    if (
      isNullOrUndefined(this.currentSelectedCell) ||
      this.currentSelectedCell.cellType !== MyMxCellType.CLASS
    ) {
      this.currentSelectedCell = this.rootCell;
    }
    // this.router.navigate(
    //   [`main/instance-editor/${this.globalInfo.marketplace.id}`],
    //   {
    //     queryParams: {
    //       0: this.currentSelectedCell.id,
    //       returnTo: 'classConfigurator'
    //     }
    //   }
    // );
  }

  showExportDialog() {
    const rootCell = this.graph.getSelectionCell() as MyMxCell;
    if (!isNullOrUndefined(rootCell)) {
      this.dialogFactory.openPreviewExportDialog([rootCell.id]).then(() => { });
    }
  }

  /**
   * ******KEY LISTENER/HANDLER******
   */
  @HostListener('document:keypress', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.key === 'Delete') {
      const cells = this.graph.getSelectionCells() as MyMxCell[];
      this.deleteCells(cells);
    }
  }

  /**
   * ******DEBUGGING******
   */
  private convertAggregation(r: Relationship) {
    if (r.relationshipType === RelationshipType.AGGREGATION) {
      r.relationshipType = RelationshipType.ASSOCIATION;
      r.targetCardinality = AssociationCardinality.ONE;
      r.sourceCardinality = AssociationCardinality.ONE;
    }
    return r;
  }

  showZoomLevel() {
    const scale = this.graph.view.getScale();
    console.log(this.graph.view.getScale());
    this.graph.zoomActual();
    console.log(this.graph.view.getScale());
    this.graph.view.setScale(scale);
  }

  doShitWithGraph(graph: mxgraph.mxGraph) {
    console.log(graph);
  }
  printModelToConsole() {
    console.log(this.classDefinitions);
    console.log(this.relationships);
    console.log(this.classConfiguration);
  }
}
