import {
  Component,
  OnInit,
  Input,
  ElementRef,
  ViewChild,
  Output,
  EventEmitter,
  HostListener,
  AfterContentInit,
} from "@angular/core";
import { mxgraph } from "mxgraph";
import { Router } from "@angular/router";
import { ObjectIdService } from "app/main/content/_service/objectid.service.";
import { Marketplace } from "app/main/content/_model/marketplace";

import { MyMxCell, MyMxCellType } from "../../../../myMxCell";
import {
  EnumDefinition,
  EnumEntry,
  EnumRelationship,
} from "app/main/content/_model/meta/enum";
import { CConstants } from "../../../../class-configurator/utils-and-constants";
import { isNullOrUndefined } from "util";
import { EnumDefinitionService } from "app/main/content/_service/meta/core/enum/enum-configuration.service";
import { of } from "rxjs";
import { EnumOptionsOverlayContentData } from "./options-overlay/options-overlay-content/options-overlay-content.component";
import { User } from "app/main/content/_model/user";

declare var require: any;

const ENTRY_CELL_WIDTH = 110;
const ENTRY_CELL_HEIGHT = 70;

const mx: typeof mxgraph = require("mxgraph")({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

@Component({
  selector: "app-enum-graph-editor",
  templateUrl: "./enum-graph-editor.component.html",
  styleUrls: ["./enum-graph-editor.component.scss"],
  // providers: [DialogFactoryDirective]
})
export class EnumGraphEditorComponent implements AfterContentInit {
  constructor(
    private objectIdService: ObjectIdService,
    private enumDefinitionService: EnumDefinitionService
  ) {}

  @Input() marketplace: Marketplace;
  @Input() tenantAdmin: User;
  @Input() enumDefinition: EnumDefinition;
  @Output() result: EventEmitter<{
    type: string;
    payload: EnumDefinition;
  }> = new EventEmitter();
  @Output() management: EventEmitter<String> = new EventEmitter();

  @ViewChild("enumGraphContainer", { static: true }) graphContainer: ElementRef;
  graph: mxgraph.mxGraph;
  rootCell: MyMxCell;
  layout: any;

  overlayContent: EnumOptionsOverlayContentData = undefined;
  overlayEvent = undefined;
  displayOverlay = false;

  /**
   * ******INITIALIZATION******
   */
  ngAfterContentInit() {
    this.graphContainer.nativeElement.style.overflow = "hidden";
    this.graphContainer.nativeElement.style.height = "65vh";
    this.graphContainer.nativeElement.style.width = "100%";
    this.graphContainer.nativeElement.style.background = "white";

    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);
    this.graph.isCellSelectable = function (cell) {
      const state = this.view.getState(cell);
      const style = state != null ? state.style : this.getCellStyle(cell);

      return (
        this.isCellsSelectable() &&
        !this.isCellLocked(cell) &&
        style["selectable"] !== 0
      );
    };

    const outer = this;
    this.graph.getCursorForCell = function (cell: MyMxCell) {
      // todo cursor
      if (
        cell.cellType === MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON ||
        cell.cellType === MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON
      ) {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      }
    };

    if (!mx.mxClient.isBrowserSupported()) {
      mx.mxUtils.error("Browser is not supported!", 200, false);
    } else {
      // Disables the built-in context menu
      mx.mxEvent.disableContextMenu(this.graphContainer.nativeElement);

      // Enables rubberband selection
      // tslint:disable-next-line: no-unused-expression
      new mx.mxRubberband(this.graph);
      this.graph.setPanning(true);
      this.graph.tooltipHandler = new mx.mxTooltipHandler(this.graph, 100);

      /**
       * ******EVENT LISTENERS******
       */

      this.graph.addListener(mx.mxEvent.CLICK, function (
        sender: mxgraph.mxGraph,
        evt: mxgraph.mxEventObject
      ) {
        // Todo click event
        const mouseEvent = evt.getProperty("event");
        if (mouseEvent.button === 0) {
          outer.handleMXGraphLeftClickEvent(evt);
        } else if (mouseEvent.button === 2) {
          // Handle Right Click
        }
      });

      this.graph.addListener(mx.mxEvent.DOUBLE_CLICK, function (
        sender: mxgraph.mxGraph,
        evt: mxgraph.mxEventObject
      ) {
        // TODO double click event
      });
      this.createGraph();
      this.setLayout();
      this.executeLayout();
    }
  }

  createGraph() {
    this.graph.getModel().beginUpdate();
    this.createRootCell();
    this.createEntryCells(this.enumDefinition.enumEntries);
    this.createRelationshipCells(this.enumDefinition.enumRelationships);
    this.graph.getModel().endUpdate();
  }

  private createRootCell() {
    const rootCell = this.graph.insertVertex(
      this.graph.getDefaultParent(),
      this.enumDefinition.id,
      this.enumDefinition.name,
      0,
      0,
      ENTRY_CELL_WIDTH,
      ENTRY_CELL_HEIGHT,
      CConstants.mxStyles.classEnum
    ) as MyMxCell;
    rootCell.root = true;
    rootCell.cellType = MyMxCellType.ENUM_HEAD;
    this.rootCell = rootCell;

    const nextIcon: MyMxCell = this.graph.insertVertex(
      rootCell,
      "add_class_next_level_icon",
      "Eintrag hinzufügen",
      85,
      45,
      20,
      20,
      CConstants.mxStyles.addClassNewLevelIcon
    ) as MyMxCell;
    nextIcon.setConnectable(false);
    nextIcon.cellType = MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON;
  }

  private createEntryCells(enumEntries: EnumEntry[]) {
    for (const entry of enumEntries) {
      const cell = this.createEntryCell(entry);
    }
  }

  private createEntryCell(
    enumEntry?: EnumEntry,
    position?: { x: number; y: number }
  ): MyMxCell {
    if (isNullOrUndefined(enumEntry)) {
      enumEntry = this.createNewEnumEntry();
    }
    if (isNullOrUndefined(position)) {
      position = { x: 0, y: 0 };
    }

    const cell = this.graph.insertVertex(
      this.graph.getDefaultParent(),
      enumEntry.id,
      enumEntry.value,
      position.x,
      position.y,
      ENTRY_CELL_WIDTH,
      ENTRY_CELL_HEIGHT,
      CConstants.mxStyles.classEnum
    ) as MyMxCell;
    cell.root = false;
    cell.cellType = MyMxCellType.ENUM_ENTRY;

    const nextIcon: MyMxCell = this.graph.insertVertex(
      cell,
      "add_class_next_level_icon",
      "Eintrag hinzufügen",
      85,
      45,
      20,
      20,
      CConstants.mxStyles.addClassNewLevelIcon
    ) as MyMxCell;
    nextIcon.setConnectable(false);
    nextIcon.cellType = MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON;

    const sameIcon: MyMxCell = this.graph.insertVertex(
      cell,
      "add_class_same_level_icon",
      "Eintrag hinzufügen",
      65,
      45,
      20,
      20,
      CConstants.mxStyles.addClassSameLevelIcon
    ) as MyMxCell;
    sameIcon.setConnectable(false);
    sameIcon.cellType = MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON;

    const optionsIcon: MyMxCell = this.graph.insertVertex(
      cell,
      "options",
      "options",
      5,
      45,
      20,
      20,
      CConstants.mxStyles.optionsIcon
    ) as MyMxCell;
    optionsIcon.setConnectable(false);
    optionsIcon.cellType = MyMxCellType.OPTIONS_ICON;

    return cell;
  }

  private createNewEnumEntry() {
    const enumEntry = new EnumEntry();
    enumEntry.id = this.objectIdService.getNewObjectId();
    enumEntry.selectable = true;
    enumEntry.value = "neuer Eintrag";
    this.enumDefinition.enumEntries.push(enumEntry);
    return enumEntry;
  }

  private createRelationship(sourceId: string, targetId: string) {
    const relationship = new EnumRelationship();
    relationship.id = this.objectIdService.getNewObjectId();
    relationship.sourceEnumEntryId = sourceId;
    relationship.targetEnumEntryId = targetId;
    this.enumDefinition.enumRelationships.push(relationship);
    return relationship;
  }

  private createRelationshipCells(enumRelationships: EnumRelationship[]) {
    for (const relationship of enumRelationships) {
      const cell = this.createRelationshipCellById(relationship);
    }
  }

  private createRelationshipCellById(
    enumRelationship: EnumRelationship
  ): MyMxCell {
    const source: MyMxCell = this.graph
      .getModel()
      .getCell(enumRelationship.sourceEnumEntryId) as MyMxCell;
    const target: MyMxCell = this.graph
      .getModel()
      .getCell(enumRelationship.targetEnumEntryId) as MyMxCell;

    return this.createRelationshipCell(enumRelationship.id, source, target);
  }

  private createRelationshipCell(
    id: string,
    source: MyMxCell,
    target: MyMxCell
  ): MyMxCell {
    const cell = this.graph.insertEdge(
      this.graph.getDefaultParent(),
      id,
      "",
      source,
      target,
      CConstants.mxStyles.genericConnection
    ) as MyMxCell;
    cell.cellType = MyMxCellType.ENUM_CONNECTOR;
    return cell;
  }

  private handleMXGraphLeftClickEvent(event: mxgraph.mxEventObject) {
    const eventCell = event.getProperty("cell") as MyMxCell;
    if (isNullOrUndefined(eventCell) || this.displayOverlay) {
      if (this.displayOverlay) {
        this.handleOverlayClosed();
      }
      return;
    }

    if (eventCell.cellType === MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON) {
      const newCell = this.createEntryCell();
      const relationship = this.createRelationship(
        eventCell.getParent().id,
        newCell.id
      );
      this.createRelationshipCell(
        relationship.id,
        eventCell.getParent() as MyMxCell,
        newCell
      );
      this.executeLayout();
    } else if (eventCell.cellType === MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON) {
      const newCell = this.createEntryCell();
      const parentCell = this.graph.getIncomingEdges(eventCell.getParent())[0]
        .source as MyMxCell;
      const relationship = this.createRelationship(parentCell.id, newCell.id);
      this.createRelationshipCell(relationship.id, parentCell, newCell);
      this.executeLayout();
    } else if (eventCell.cellType === MyMxCellType.OPTIONS_ICON) {
      this.openOverlay(
        eventCell.getParent() as MyMxCell,
        event.getProperty("event")
      );
    }
  }

  onSaveClick() {
    this.updateModel();
    this.result.emit({ type: "save", payload: this.enumDefinition });
  }

  updateModel() {
    const vertices = this.graph
      .getModel()
      .getChildVertices(this.graph.getDefaultParent());
    const edges = this.graph
      .getModel()
      .getChildEdges(this.graph.getDefaultParent());

    // update head
    this.enumDefinition.name = this.rootCell.value;

    // update entries
    const newEnumEntries: EnumEntry[] = [];
    for (const vertice of vertices) {
      if (vertice.id === this.rootCell.id) {
        continue;
      }
      const enumEntry = this.enumDefinition.enumEntries.find(
        (e) => e.id === vertice.id
      );
      enumEntry.value = vertice.value;
      newEnumEntries.push(enumEntry);
    }
    this.enumDefinition.enumEntries = newEnumEntries;

    const newEnumRelationships: EnumRelationship[] = [];
    for (const edge of edges) {
      const relationship = this.enumDefinition.enumRelationships.find(
        (r) => r.id === edge.id
      );
      relationship.sourceEnumEntryId = edge.source.id;
      relationship.targetEnumEntryId = edge.target.id;
      newEnumRelationships.push(relationship);
    }
    this.enumDefinition.enumRelationships = newEnumRelationships;
  }

  onBackClick() {
    this.result.emit({ type: "back", payload: undefined });
  }

  onSaveAndBackClick() {
    this.updateModel();
    this.result.emit({ type: "saveAndBack", payload: this.enumDefinition });
  }

  private setLayout() {
    if (
      !isNullOrUndefined(this.rootCell.edges) &&
      this.rootCell.edges.length > 0
    ) {
      this.layout = new mx.mxCompactTreeLayout(this.graph, false, false);
      this.layout.levelDistance = 50;
      this.layout.alignRanks = true;
      this.layout.minEdgeJetty = 50;
      this.layout.prefHozEdgeSep = 5;
      this.layout.resetEdges = false;
      this.layout.edgeRouting = true;
    }
  }

  private executeLayout() {
    if (isNullOrUndefined(this.layout)) {
      this.setLayout();
    }
    if (!isNullOrUndefined(this.layout)) {
      this.layout.execute(this.graph.getDefaultParent(), this.rootCell);
    }
  }

  /**
   * ******KEY LISTENER/HANDLER******
   */
  @HostListener("document:keypress", ["$event"])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.key === "Delete") {
      const cells = this.graph.getSelectionCells() as MyMxCell[];
      this.performDelete(cells);
    }
  }

  private performDelete(cells: MyMxCell[]) {
    cells = cells.filter((c: MyMxCell) => !c.writeProtected);
    const removedCells = this.graph.removeCells(cells, false) as MyMxCell[];
  }

  private openOverlay(cell: MyMxCell, event: mxgraph.mxEventObject) {
    this.overlayEvent = event;
    this.overlayContent = new EnumOptionsOverlayContentData();
    this.overlayContent.enumEntry = this.enumDefinition.enumEntries.find(
      (e) => e.id === cell.id
    );
    this.management.emit("disableScroll");
    this.graph.setPanning(false);
    this.graph.setEnabled(false);
    this.graph.setTooltips(false);
    this.displayOverlay = true;
  }

  handleOverlayClosed(event?: EnumEntry) {
    if (!isNullOrUndefined(event)) {
      const i = this.enumDefinition.enumEntries.findIndex(
        (e) => e.id === event.id
      );
      this.enumDefinition.enumEntries[i] = event;
    }
    this.overlayContent = undefined;
    this.overlayEvent = undefined;
    this.displayOverlay = false;
    this.management.emit("enableScroll");
    this.graph.setPanning(true);
    this.graph.setEnabled(true);
    this.graph.setTooltips(true);
  }
}
