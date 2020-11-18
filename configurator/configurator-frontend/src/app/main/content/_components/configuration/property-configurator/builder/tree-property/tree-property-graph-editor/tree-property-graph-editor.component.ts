import { Component, Input, ElementRef, ViewChild, Output, EventEmitter, HostListener, AfterContentInit } from '@angular/core';
import { mxgraph } from 'mxgraph';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { MyMxCell, MyMxCellType } from '../../../../myMxCell';
import { TreePropertyDefinition, TreePropertyEntry, TreePropertyRelationship } from 'app/main/content/_model/configurator/property/tree-property';
import { CConstants } from '../../../../class-configurator/utils-and-constants';
import { isNullOrUndefined } from 'util';
import { TreePropertyOptionsOverlayContentData } from './options-overlay/options-overlay-content/options-overlay-content.component';

declare var require: any;

const ENTRY_CELL_WIDTH = 110;
const ENTRY_CELL_HEIGHT = 70;

const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

@Component({
  selector: "app-tree-property-graph-editor",
  templateUrl: './tree-property-graph-editor.component.html',
  styleUrls: ['./tree-property-graph-editor.component.scss'],
})
export class TreePropertyGraphEditorComponent implements AfterContentInit {
  constructor(private objectIdService: ObjectIdService
  ) { }

  @Input() treePropertyDefinition: TreePropertyDefinition;
  @Output() result: EventEmitter<{ type: string; payload: TreePropertyDefinition; }> = new EventEmitter();
  @Output() management: EventEmitter<String> = new EventEmitter();
  @ViewChild('graphContainer', { static: true }) graphContainer: ElementRef;

  graph: mxgraph.mxGraph;
  rootCell: MyMxCell;
  layout: any;

  overlayContent: TreePropertyOptionsOverlayContentData = undefined;
  overlayEvent = undefined;
  displayOverlay = false;

  /**
   * ******INITIALIZATION******
   */
  ngAfterContentInit() {
    if (!mx.mxClient.isBrowserSupported()) {
      mx.mxUtils.error('Browser is not supported!', 200, false);
      return;
    }

    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);
    this.graph.getCursorForCell = (cell: MyMxCell) => {
      if (cell.cellType === MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON || cell.cellType === MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON) {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      }
    };
    mx.mxEvent.disableContextMenu(this.graphContainer.nativeElement);
    // tslint:disable-next-line: no-unused-expression
    new mx.mxRubberband(this.graph);
    this.graph.setPanning(true);
    this.graph.tooltipHandler = new mx.mxTooltipHandler(this.graph, 100);

    /**
     * ******EVENT LISTENERS******
     */

    this.graph.addListener(mx.mxEvent.CLICK, (sender: mxgraph.mxGraph, evt: mxgraph.mxEventObject) => {
      const mouseEvent = evt.getProperty('event');
      if (mouseEvent.button === 0) {
        this.handleMXGraphLeftClickEvent(evt);
      } else if (mouseEvent.button === 2) {
        // Handle Right Click
      }
    });
    this.createGraph();
    this.setLayout();
    this.executeLayout();
  }

  createGraph() {
    this.graph.getModel().beginUpdate();
    this.createRootCell();
    this.createEntryCells(this.treePropertyDefinition.entries);
    this.createRelationshipCells(this.treePropertyDefinition.relationships);
    this.graph.getModel().endUpdate();
  }

  private createRootCell() {
    const rootCell = this.graph.insertVertex(
      this.graph.getDefaultParent(), this.treePropertyDefinition.id, this.treePropertyDefinition.name,
      0, 0, ENTRY_CELL_WIDTH, ENTRY_CELL_HEIGHT, CConstants.mxStyles.classTree
    ) as MyMxCell;

    rootCell.root = true;
    rootCell.cellType = MyMxCellType.TREE_HEAD;
    this.rootCell = rootCell;

    const nextIcon: MyMxCell = this.graph.insertVertex(
      rootCell, 'add_class_next_level_icon', 'Eintrag hinzufügen',
      85, 45, 20, 20, CConstants.mxStyles.addClassNewLevelIcon
    ) as MyMxCell;
    nextIcon.setConnectable(false);
    nextIcon.cellType = MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON;
  }

  private createEntryCells(treePropertyEntries: TreePropertyEntry[]) {
    for (const entry of treePropertyEntries) {
      this.createEntryCell(entry);
    }
  }

  private createEntryCell(treePropertyEntry?: TreePropertyEntry, position?: { x: number; y: number }): MyMxCell {
    if (isNullOrUndefined(treePropertyEntry)) {
      treePropertyEntry = this.createNewTreePropertyEntry();
    }
    if (isNullOrUndefined(position)) {
      position = { x: 0, y: 0 };
    }

    const cell = this.graph.insertVertex(
      this.graph.getDefaultParent(), treePropertyEntry.id, treePropertyEntry.value,
      position.x, position.y, ENTRY_CELL_WIDTH, ENTRY_CELL_HEIGHT, CConstants.mxStyles.classTree
    ) as MyMxCell;
    cell.root = false;
    cell.cellType = MyMxCellType.TREE_ENTRY;

    const nextIcon: MyMxCell = this.graph.insertVertex(cell, 'add_class_next_level_icon', 'Eintrag hinzufügen',
      85, 45, 20, 20, CConstants.mxStyles.addClassNewLevelIcon
    ) as MyMxCell;
    nextIcon.setConnectable(false);
    nextIcon.cellType = MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON;

    const sameIcon: MyMxCell = this.graph.insertVertex(cell, 'add_class_same_level_icon', 'Eintrag hinzufügen',
      65, 45, 20, 20, CConstants.mxStyles.addClassSameLevelIcon
    ) as MyMxCell;
    sameIcon.setConnectable(false);
    sameIcon.cellType = MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON;

    const optionsIcon: MyMxCell = this.graph.insertVertex(cell, 'options', 'options',
      5, 45, 20, 20, CConstants.mxStyles.optionsIcon
    ) as MyMxCell;
    optionsIcon.setConnectable(false);
    optionsIcon.cellType = MyMxCellType.OPTIONS_ICON;

    return cell;
  }

  private createNewTreePropertyEntry() {
    const treePropertyEntry = new TreePropertyEntry();
    treePropertyEntry.id = this.objectIdService.getNewObjectId();
    treePropertyEntry.selectable = true;
    treePropertyEntry.value = 'neuer Eintrag';
    this.treePropertyDefinition.entries.push(treePropertyEntry);
    return treePropertyEntry;
  }

  private createRelationship(sourceId: string, targetId: string) {
    const relationship = new TreePropertyRelationship();
    relationship.id = this.objectIdService.getNewObjectId();
    relationship.sourceId = sourceId;
    relationship.targetId = targetId;
    this.treePropertyDefinition.relationships.push(relationship);
    return relationship;
  }

  private createRelationshipCells(treePropertyRelationships: TreePropertyRelationship[]) {
    for (const relationship of treePropertyRelationships) {
      this.createRelationshipCellById(relationship);
    }
  }

  private createRelationshipCellById(treePropertyRelationships: TreePropertyRelationship): MyMxCell {
    const source: MyMxCell = this.graph.getModel()
      .getCell(treePropertyRelationships.sourceId) as MyMxCell;
    const target: MyMxCell = this.graph.getModel()
      .getCell(treePropertyRelationships.targetId) as MyMxCell;

    return this.createRelationshipCell(treePropertyRelationships.id, source, target);
  }

  private createRelationshipCell(id: string, source: MyMxCell, target: MyMxCell): MyMxCell {
    const cell = this.graph.insertEdge(
      this.graph.getDefaultParent(), id, '', source, target, CConstants.mxStyles.genericConnection
    ) as MyMxCell;
    cell.cellType = MyMxCellType.TREE_CONNECTOR;
    return cell;
  }

  private handleMXGraphLeftClickEvent(event: mxgraph.mxEventObject) {
    const eventCell = event.getProperty('cell') as MyMxCell;
    if (isNullOrUndefined(eventCell) || this.displayOverlay) {
      if (this.displayOverlay) {
        this.handleOverlayClosed();
      }
      return;
    }

    if (eventCell.cellType === MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON) {
      const newCell = this.createEntryCell();
      const relationship = this.createRelationship(eventCell.getParent().id, newCell.id);
      this.createRelationshipCell(relationship.id, eventCell.getParent() as MyMxCell, newCell);
      this.executeLayout();

    } else if (eventCell.cellType === MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON) {
      const newCell = this.createEntryCell();
      const parentCell = this.graph.getIncomingEdges(eventCell.getParent())[0].source as MyMxCell;
      const relationship = this.createRelationship(parentCell.id, newCell.id);
      this.createRelationshipCell(relationship.id, parentCell, newCell);
      this.executeLayout();

    } else if (eventCell.cellType === MyMxCellType.OPTIONS_ICON) {
      this.openOverlay(eventCell.getParent() as MyMxCell, event.getProperty('event'));
    }
  }

  onSaveClick() {
    this.updateModel();
    this.result.emit({ type: 'save', payload: this.treePropertyDefinition });
  }

  updateModel() {
    const vertices = this.graph
      .getModel()
      .getChildVertices(this.graph.getDefaultParent());
    const edges = this.graph
      .getModel()
      .getChildEdges(this.graph.getDefaultParent());

    // update head
    this.treePropertyDefinition.name = this.rootCell.value;

    // update entries
    const newTreePropertyEntries: TreePropertyEntry[] = [];
    for (const vertice of vertices) {
      if (vertice.id === this.rootCell.id) { continue; }

      const treePropertyEntry = this.treePropertyDefinition.entries.find(e => e.id === vertice.id);
      treePropertyEntry.value = vertice.value;
      newTreePropertyEntries.push(treePropertyEntry);
    }
    this.treePropertyDefinition.entries = newTreePropertyEntries;

    const newTreePropertyRelationships: TreePropertyRelationship[] = [];
    for (const edge of edges) {
      const relationship = this.treePropertyDefinition.relationships.find(r => r.id === edge.id);
      relationship.sourceId = edge.source.id;
      relationship.targetId = edge.target.id;
      newTreePropertyRelationships.push(relationship);
    }
    this.treePropertyDefinition.relationships = newTreePropertyRelationships;
  }

  onBackClick() {
    this.result.emit({ type: 'back', payload: this.treePropertyDefinition });
  }

  onSaveAndBackClick() {
    this.updateModel();
    this.result.emit({ type: 'saveAndBack', payload: this.treePropertyDefinition });
  }

  private setLayout() {
    if (isNullOrUndefined(this.rootCell.edges) || this.rootCell.edges.length <= 0) {
      return;
    }
    this.layout = new mx.mxCompactTreeLayout(this.graph, false, false);
    this.layout.levelDistance = 50;
    this.layout.alignRanks = true;
    this.layout.minEdgeJetty = 50;
    this.layout.prefHozEdgeSep = 5;
    this.layout.resetEdges = false;
    this.layout.edgeRouting = true;
  }

  private executeLayout() {
    isNullOrUndefined(this.layout) ? this.setLayout()
      : this.layout.execute(this.graph.getDefaultParent(), this.rootCell);
  }

  /**
   * ******KEY LISTENER/HANDLER******
   */
  @HostListener('document:keypress', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.key === 'Delete') {
      const cells = this.graph.getSelectionCells() as MyMxCell[];
      this.performDelete(cells);
    }
  }

  private performDelete(cells: MyMxCell[]) {
    cells = cells.filter((c: MyMxCell) => !c.writeProtected);
    this.graph.removeCells(cells, false);
  }

  private openOverlay(cell: MyMxCell, event: mxgraph.mxEventObject) {
    this.overlayEvent = event;
    this.overlayContent = new TreePropertyOptionsOverlayContentData();
    this.overlayContent.treePropertyEntry =
      this.treePropertyDefinition.entries.find(e => e.id === cell.id);

    this.management.emit('disableScroll');
    this.graph.setEnabled(false);
    this.displayOverlay = true;
  }

  handleOverlayClosed(event?: TreePropertyEntry) {
    if (!isNullOrUndefined(event)) {
      const i = this.treePropertyDefinition.entries.findIndex(
        (e) => e.id === event.id
      );
      this.treePropertyDefinition.entries[i] = event;
    }
    this.overlayContent = undefined;
    this.overlayEvent = undefined;
    this.displayOverlay = false;
    this.management.emit('enableScroll');
    this.graph.setEnabled(true);
  }
}
