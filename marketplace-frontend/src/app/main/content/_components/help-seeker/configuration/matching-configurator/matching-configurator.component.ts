import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterContentInit,
  Renderer2,
  HostListener
} from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { Marketplace } from "app/main/content/_model/marketplace";
import { mxgraph } from "mxgraph";
import { isNullOrUndefined } from "util";
import { PropertyType } from "app/main/content/_model/meta/property";
import { CConstants } from "../class-configurator/utils-and-constants";
import { CoreHelpSeekerService } from "../../../../_service/core-helpseeker.service";
import { CoreFlexProdService } from "../../../../_service/core-flexprod.service";
import { LoginService } from "../../../../_service/login.service";
import { User, UserRole } from "../../../../_model/user";
import { MatchingConfiguratorPopupMenu } from "./popup-menu";
import {
  MatchingOperatorRelationship,
  MatchingCollector,
  MatchingCollectorEntry,
  MatchingEntityType
} from "../../../../_model/matching";
import { MatchingConfigurationService } from "../../../../_service/configuration/matching-configuration.service";
import {
  ClassConfiguration,
  MatchingConfiguration,
  MatchingCollectorConfiguration
} from "../../../../_model/meta/configurations";
import { MatchingCollectorConfigurationService } from "../../../../_service/configuration/matching-collector-configuration.service";
import { ObjectIdService } from "../../../../_service/objectid.service.";
import { DialogFactoryDirective } from "../../../_shared/dialogs/_dialog-factory/dialog-factory.component";
import { MyMxCell, MyMxCellType } from "../myMxCell";
import { GlobalInfo } from "app/main/content/_model/global-info";

declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

// tslint:disable-next-line: class-name

@Component({
  selector: "app-matching-configurator",
  templateUrl: './matching-configurator.component.html',
  styleUrls: ['./matching-configurator.component.scss'],
  providers: [DialogFactoryDirective],
})
export class MatchingConfiguratorComponent implements OnInit, AfterContentInit {
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private matchingCollectorConfigurationService: MatchingCollectorConfigurationService,
    private loginService: LoginService,
    private flexProdService: CoreFlexProdService,
    private helpSeekerService: CoreHelpSeekerService,
    private matchingConfigurationService: MatchingConfigurationService,
    private objectIdService: ObjectIdService,
    private renderer: Renderer2,
    private dialogFactory: DialogFactoryDirective
  ) { }

  marketplace: Marketplace;

  eventResponseAction: string;

  @ViewChild('graphContainer', { static: true }) graphContainer: ElementRef;
  @ViewChild('paletteContainer', { static: true }) paletteContainer: ElementRef;
  @ViewChild('deleteOperationIcon', { static: true })
  deleteOperationContainer: ElementRef;

  graph: mxgraph.mxGraph;

  leftClassConfigurator: ClassConfiguration;
  rightClassConfiguration: ClassConfiguration;

  leftMatchingCollectorConfiguration: MatchingCollectorConfiguration;
  rightMatchingCollectorConfiguration: MatchingCollectorConfiguration;

  matchingOperatorPalettes = CConstants.matchingOperatorPalettes;
  matchingConnectorPalettes = CConstants.matchingConnectorPalettes;
  deleteOperationPalette = CConstants.deleteOperationPalette;

  matchingConfiguration: MatchingConfiguration;

  displayOverlay: boolean;
  overlayRelationship: MatchingOperatorRelationship;
  overlayEvent: PointerEvent;

  // Delete Mode
  confirmDelete: boolean;
  deleteMode: boolean;

  async ngOnInit() {
    this.confirmDelete = true;

    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
  }







  loadClassesAndRelationships(
    leftClassConfigurationId: string,
    rightClassConfigurationId: string
  ) {
    this.clearEditor();
    this.matchingConfiguration = undefined;

    Promise.all([
      this.matchingCollectorConfigurationService
        .getSavedMatchingCollectorConfiguration(
          this.marketplace,
          leftClassConfigurationId
        )
        .toPromise()
        .then((configuration: MatchingCollectorConfiguration) => {
          this.leftMatchingCollectorConfiguration = configuration;
          this.insertClassDefinitionsLeftFromCollector();
        }),
      this.matchingCollectorConfigurationService
        .getSavedMatchingCollectorConfiguration(
          this.marketplace,
          rightClassConfigurationId
        )
        .toPromise()
        .then((configuration: MatchingCollectorConfiguration) => {
          this.rightMatchingCollectorConfiguration = configuration;
          this.insertClassDefinitionsRightFromCollector();
        })
    ]).then(() => {
      this.matchingConfigurationService
        .getMatchingConfigurationByClassConfigurationIds(
          this.marketplace,
          leftClassConfigurationId,
          rightClassConfigurationId
        )
        .toPromise()
        .then((matchingConfiguration: MatchingConfiguration) => {
          if (!isNullOrUndefined(matchingConfiguration)) {
            this.matchingConfiguration = matchingConfiguration;
            this.insertMatchingOperatorsAndRelationships();
          } else {
            this.matchingConfiguration = new MatchingConfiguration();
            this.matchingConfiguration.rightClassConfigurationId = rightClassConfigurationId;
            this.matchingConfiguration.leftClassConfigurationId = leftClassConfigurationId;
            this.matchingConfiguration.relationships = [];
          }
        });
    });
  }

  ngAfterContentInit() {
    this.graphContainer.nativeElement.style.position = 'absolute';
    this.graphContainer.nativeElement.style.overflow = 'scroll';
    this.graphContainer.nativeElement.style.left = '0px';
    this.graphContainer.nativeElement.style.top = '60px';
    this.graphContainer.nativeElement.style.right = '0px';
    this.graphContainer.nativeElement.style.bottom = '0px';
    this.graphContainer.nativeElement.style.background = 'white';
    this.graphContainer.nativeElement.style.width = '100%';

    this.paletteContainer.nativeElement.style.position = 'absolute';
    this.paletteContainer.nativeElement.style.overflow = 'hidden';
    this.paletteContainer.nativeElement.style.padding = '2px';
    this.paletteContainer.nativeElement.style.right = '0px';
    this.paletteContainer.nativeElement.style.top = '30px';
    this.paletteContainer.nativeElement.style.left = '0px';
    this.paletteContainer.nativeElement.style.height = '30px';
    this.paletteContainer.nativeElement.style.background = 'white';
    this.paletteContainer.nativeElement.style.font =
      'Arial, Helvetica, sans-serif';

    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);

    const outer = this;

    this.graph.isCellSelectable = function(cell) {
      const state = this.view.getState(cell);
      const style = state != null ? state.style : this.getCellStyle(cell);

      return (
        this.isCellsSelectable() &&
        !this.isCellLocked(cell) &&
        style['selectable'] !== 0
      );
    };

    this.graph.getCursorForCell = function(cell: MyMxCell) {
      if (
        cell.cellType === MyMxCellType.MATCHING_OPERATOR &&
        outer.deleteMode
      ) {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      } else if (outer.deleteMode) {
        return 'default';
      }
    };

    const modelGetStyle = this.graph.model.getStyle;
    this.graph.model.getStyle = function(cell) {
      if (cell != null) {
        let style = modelGetStyle.apply(this, arguments);

        if (this.isCollapsed(cell)) {
          style = style + ';shape=rectangle';
        }
        return style;
      }
      return null;
    };

    this.graph.getEdgeValidationError = function(
      edge: MyMxCell,
      source: MyMxCell,
      target: MyMxCell
    ) {
      if (
        !isNullOrUndefined(source) &&
        !isNullOrUndefined(source.edges) &&
        source.cellType === MyMxCellType.MATCHING_OPERATOR &&
        !isNullOrUndefined(edge.target) &&
        edge.target.id === target.id
      ) {
        if (source.edges.length >= 2) {
          return '';
        }

        for (const e of source.edges) {
          if (!isNullOrUndefined(e.source) && e.source.id === source.id) {
            return '';
          }
        }
      } else if (
        !isNullOrUndefined(target) &&
        !isNullOrUndefined(target.edges) &&
        target.cellType === MyMxCellType.MATCHING_OPERATOR &&
        !isNullOrUndefined(edge.source) &&
        edge.source.id === source.id
      ) {
        if (target.edges.length >= 2) {
          return '';
        }

        if (source.cellType === MyMxCellType.MATCHING_OPERATOR) {
          return '';
        }

        for (const e of target.edges) {
          if (!isNullOrUndefined(e.target) && e.target.id === target.id) {
            return '';
          }
        }
      }
    };

    if (!mx.mxClient.isBrowserSupported()) {
      mx.mxUtils.error('Browser is not supported!', 200, false);
    } else {
      // Disables the built-in context menu
      mx.mxEvent.disableContextMenu(this.graphContainer.nativeElement);

      // Enables rubberband selection
      // tslint:disable-next-line: no-unused-expression
      // new mx.mxRubberband(this.graph);

      this.graph.popupMenuHandler = this.createPopupMenu(this.graph);

      this.graph.setPanning(true);
      this.graph.useScrollbarsForPanning = true;

      this.graph.addListener(mx.mxEvent.CLICK, function(sender, evt) {
        // Handle Click
        outer.handleClickEvent(evt);
      });

      this.graph.addListener(mx.mxEvent.DOUBLE_CLICK, function(sender, evt) {
        // Handle Double Click
        outer.handleDoubleClickEvent(evt);
      });

      this.graph
        .getSelectionModel()
        .addListener(mx.mxEvent.CHANGE, function(sender, evt) {
          // Handle Select
        });
    }
  }

  private createPopupMenu(graph) {
    const popupMenu = new MatchingConfiguratorPopupMenu(graph, this);
    return popupMenu.createPopupMenuHandler(graph);
  }

  clearEditor() {
    this.graph.getModel().beginUpdate();
    try {
      this.graph.getModel().clear();
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private insertClassDefinitionsLeftFromCollector() {
    const title = this.graph.insertVertex(
      this.graph.getDefaultParent(),
      "left_header",
      "Werkunternehmen",
      20,
      20,
      400,
      50,
      CConstants.mxStyles.matchingRowHeader
    );
    title.setConnectable(false);

    let y = title.geometry.y + title.geometry.height + 20;

    for (const c of this.leftMatchingCollectorConfiguration.collectors) {
      const cell = this.insertClassDefinitionCollectorIntoGraph(
        c,
        new mx.mxGeometry(120, y, 200, 0)
      );
      y = cell.geometry.y + cell.geometry.height + 20;
    }
  }

  private insertClassDefinitionsRightFromCollector() {
    const x = this.graphContainer.nativeElement.offsetWidth - 220;
    let y = 20;

    const title = this.graph.insertVertex(
      this.graph.getDefaultParent(),
      "right_header",
      "Werkbesteller",
      x - 200,
      y,
      400,
      50,
      CConstants.mxStyles.matchingRowHeader
    );
    title.setConnectable(false);

    y = title.geometry.y + title.geometry.height + 20;

    for (const c of this.rightMatchingCollectorConfiguration.collectors) {
      const cell = this.insertClassDefinitionCollectorIntoGraph(
        c,
        new mx.mxGeometry(x - 100, y, 200, 0)
      );
      y = cell.geometry.y + cell.geometry.height + 20;
    }
  }

  private insertClassDefinitionCollectorIntoGraph(
    collector: MatchingCollector,
    geometry: mxgraph.mxGeometry
  ): MyMxCell {
    // create class cell
    let cell: MyMxCell;
    if (collector.classDefinition.classArchetype.startsWith('ENUM')) {
      cell = new mx.mxCell(
        collector.classDefinition.name,
        geometry,
        CConstants.mxStyles.classEnum
      ) as MyMxCell;
    } else if (collector.classDefinition.collector) {
      cell = new mx.mxCell(
        collector.classDefinition.name,
        geometry,
        CConstants.mxStyles.matchingClassFlexprodCollector
      ) as MyMxCell;
    } else {
      cell = new mx.mxCell(
        collector.classDefinition.name,
        geometry,
        CConstants.mxStyles.matchingClassNormal
      ) as MyMxCell;
    }
    cell.setCollapsed(false);
    cell.classArchetype = collector.classDefinition.classArchetype;
    // cell.newlyAdded = false;
    cell.value = collector.classDefinition.name;
    cell.setVertex(true);
    cell.setConnectable(true);

    if (!isNullOrUndefined(collector.classDefinition.id)) {
      cell.id = collector.path;
    }

    cell.geometry.alternateBounds = new mx.mxRectangle(0, 0, 80, 30);
    cell.geometry.setRect(
      cell.geometry.x,
      cell.geometry.y,
      cell.geometry.width,
      20
    );

    // console.log("C: " + collector.path);

    let addPropertiesReturn = this.addPropertiesToCell(cell, collector, 5, 45);
    cell = addPropertiesReturn.cell;

    for (const entry of collector.collectorEntries) {
      // console.log("E: " + entry.path);
      const boundaryHeight =
        entry.classDefinition.name.split(/\r?\n/).length * 25;
      const boundary = this.graph.insertVertex(
        cell,
        entry.path,
        entry.classDefinition.name,
        0,
        addPropertiesReturn.lastPropertyGeometry.y +
        addPropertiesReturn.lastPropertyGeometry.height +
        2,
        200,
        boundaryHeight,
        CConstants.mxStyles.matchingClassSeparator
      );

      boundary.setConnectable(true);
      addPropertiesReturn = this.addPropertiesToCell(
        cell,
        entry,
        boundary.geometry.x + 5,
        boundary.geometry.y + boundary.geometry.height + 5
      );
    }

    cell.geometry.setRect(
      cell.geometry.x,
      cell.geometry.y,
      cell.geometry.width,
      cell.geometry.height + 5
    );
    return this.graph.addCell(cell) as MyMxCell;
  }

  private addPropertiesToCell(
    cell: MyMxCell,
    entry: MatchingCollectorEntry | MatchingCollector,
    startX: number,
    startY: number
  ) {
    const classDefinition = entry.classDefinition;

    let lastPropertyGeometry = new mx.mxGeometry(40, 40);

    if (!isNullOrUndefined(classDefinition.properties)) {
      for (const p of classDefinition.properties) {
        // console.log("P: " + entry.path + entry.pathDelimiter + p.id);
        const propertyEntry: MyMxCell = this.graph.insertVertex(
          cell,
          entry.path + entry.pathDelimiter + p.id,
          p.name,
          startX,
          startY + lastPropertyGeometry.height,
          190,
          20,
          CConstants.mxStyles.matchingProperty
        ) as MyMxCell;

        if (p.type === PropertyType.ENUM) {
          propertyEntry.cellType = MyMxCellType.ENUM_PROPERTY;
          propertyEntry.setStyle(CConstants.mxStyles.propertyEnum);
        } else {
          propertyEntry.cellType = MyMxCellType.PROPERTY;
        }
        propertyEntry.setConnectable(true);

        propertyEntry.propertyId = p.id;
        lastPropertyGeometry = propertyEntry.geometry;
        startX = lastPropertyGeometry.x;
        startY = lastPropertyGeometry.y;
      }
    }

    return { cell: cell, lastPropertyGeometry: lastPropertyGeometry };
  }

  private insertMatchingOperatorsAndRelationships() {
    for (const entry of this.matchingConfiguration.relationships) {
      const operatorCell = this.insertMatchingOperator(entry);

      let leftCell: MyMxCell;
      if (!isNullOrUndefined(entry.leftMatchingEntityPath)) {
        leftCell = this.graph
          .getModel()
          .getCell(entry.leftMatchingEntityPath) as MyMxCell;
      }
      let rightCell: MyMxCell;
      if (!isNullOrUndefined(entry.rightMatchingEntityPath)) {
        rightCell = this.graph
          .getModel()
          .getCell(entry.rightMatchingEntityPath) as MyMxCell;
      }

      this.insertRelationship(leftCell, operatorCell);
      this.insertRelationship(operatorCell, rightCell);
    }
  }

  private insertMatchingOperator(relationship: MatchingOperatorRelationship) {
    const cell = this.graph.insertVertex(
      this.graph.getDefaultParent(),
      relationship.id,
      null,
      relationship.coordX,
      relationship.coordY,
      50,
      50,
      `shape=image;image=${this.getPathForMatchingOperatorType(
        relationship.matchingOperatorType
      )};` + CConstants.mxStyles.matchingOperator
    ) as MyMxCell;

    cell.cellType = MyMxCellType.MATCHING_OPERATOR;
    cell.matchingOperatorType = relationship.matchingOperatorType;

    return cell as MyMxCell;
  }

  private insertRelationship(sourceCell: MyMxCell, targetCell: MyMxCell) {
    if (isNullOrUndefined(sourceCell) || isNullOrUndefined(targetCell)) {
      return;
    }

    const edge = this.graph.insertEdge(
      this.graph.getDefaultParent(),
      null,
      null,
      sourceCell,
      targetCell,
      CConstants.mxStyles.matchingConnector
    ) as MyMxCell;

    edge.cellType = MyMxCellType.MATCHING_CONNECTOR;

    return edge;
  }

  private getPathForMatchingOperatorType(matchingOperatorType: String) {
    const paletteItem = CConstants.matchingOperatorPalettes.find(
      ret => ret.id === matchingOperatorType
    );

    return paletteItem.imgPath;
  }

  // Functions for Views/Viewing
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
    const outer = this;
    this.graph.scrollCellToVisible(
      (function getLeftMostCell() {
        return outer.graph
          .getModel()
          .getChildCells(outer.graph.getDefaultParent())
          .find((c: MyMxCell) => c.root);
      })(),
      false
    );

    const translate = this.graph.view.getTranslate();
    this.graph.view.setTranslate(translate.x + 10, translate.y + 10);
  }

  navigateBack() {
    window.history.back();
  }

  /*
   * .........Top Menu Bar Options..........
   */

  consumeMenuOptionClickedEvent(event: any) {
    this.deleteMode = false;

    this.deleteOperationContainer.nativeElement.style.background = 'none';
    this.graph.setEnabled(true);

    this.displayOverlay = false;
    this.overlayEvent = undefined;
    this.overlayRelationship = undefined;

    switch (event.id) {
      case 'editor_save':
        this.performSave();
        break;
      case 'editor_open':
        this.performOpen(event.payload);
        break;
      case 'editor_new':
        this.performNew(
          event.payload.leftClassConfiguration,
          event.payload.rightClassConfiguration,
          event.payload.label
        );
        break;
    }
  }

  private performSave() {
    const cells = this.graph.getChildCells(this.graph.getDefaultParent());
    const matchingOperatorCells = cells.filter(
      (cell: MyMxCell) => cell.cellType === MyMxCellType.MATCHING_OPERATOR
    );

    const updatedRelationships: MatchingOperatorRelationship[] = [];

    for (const operatorCell of matchingOperatorCells) {
      const relationship = this.matchingConfiguration.relationships.find(
        r => r.id === operatorCell.id
      );

      relationship.matchingOperatorType = (operatorCell as MyMxCell).matchingOperatorType;
      relationship.coordX = operatorCell.geometry.x;
      relationship.coordY = operatorCell.geometry.y;

      let leftSet = false;
      let rightSet = false;

      for (const edge of operatorCell.edges) {
        if (
          !isNullOrUndefined(edge.source) &&
          !isNullOrUndefined(edge.target)
        ) {
          if (edge.target.id === operatorCell.id) {
            relationship.leftMatchingEntityPath = edge.source.id;
            relationship.leftMatchingEntityType =
              (edge.source as MyMxCell).cellType === MyMxCellType.PROPERTY
                ? MatchingEntityType.PROPERTY
                : MatchingEntityType.CLASS;
            leftSet = true;
          } else if (edge.source.id === operatorCell.id) {
            relationship.rightMatchingEntityPath = edge.target.id;
            relationship.rightMatchingEntityType =
              (edge.target as MyMxCell).cellType === MyMxCellType.PROPERTY
                ? MatchingEntityType.PROPERTY
                : MatchingEntityType.CLASS;
            rightSet = true;
          }
        }

        if (leftSet && rightSet) {
          break;
        }
      }

      updatedRelationships.push(relationship);
    }

    this.matchingConfiguration.relationships = updatedRelationships;
    this.matchingConfigurationService
      .saveMatchingConfiguration(this.marketplace, this.matchingConfiguration)
      .toPromise()
      .then((ret: MatchingConfiguration) => {
        // not doing anything currently
      });
  }

  performOpen(matchingConfiguration: MatchingConfiguration) {
    this.loadClassesAndRelationships(
      matchingConfiguration.leftClassConfigurationId,
      matchingConfiguration.rightClassConfigurationId
    );
  }

  performNew(
    leftClassConfiguration: ClassConfiguration,
    rightClassConfiguration: ClassConfiguration,
    name?: string
  ) {
    const matchingConfiguration = new MatchingConfiguration();
    matchingConfiguration.rightClassConfigurationId =
      rightClassConfiguration.id;
    matchingConfiguration.leftClassConfigurationId = leftClassConfiguration.id;
    matchingConfiguration.name = name;
    matchingConfiguration.relationships = [];

    this.matchingConfigurationService
      .saveMatchingConfiguration(this.marketplace, matchingConfiguration)
      .toPromise()
      .then((ret: MatchingConfiguration) => {
        // not doing anything further currently
      });

    this.loadClassesAndRelationships(
      leftClassConfiguration.id,
      rightClassConfiguration.id
    );
  }

  /**
   * ...........Mouse Events..............
   */

  handleMousedownEvent(event: any, item: any, graph: mxgraph.mxGraph) {
    const outer = this;
    let positionEvent: MouseEvent;

    const onDragstart = function(evt) {
      evt.dataTransfer.setData("text", item.id);
      evt.dataTransfer.effect = "move";
      evt.dataTransfer.effectAllowed = "move";
    };

    const onDragOver = function(evt) {
      positionEvent = evt;
    };

    const onDragend = function(evt) {
      evt.dataTransfer.getData("text");
      try {
        addObjectToGraph(evt, item);
      } finally {
        graph.getModel().endUpdate();
        removeEventListeners(outer);
      }

      function addObjectToGraph(dragEndEvent: MouseEvent, paletteItem: any) {
        const coords: mxgraph.mxPoint = graph.getPointForEvent(
          positionEvent,
          false
        );
        graph.getModel().beginUpdate();
        if (paletteItem.type === 'matchingOperator') {
          const cell = graph.insertVertex(
            graph.getDefaultParent(),
            null,
            null,
            coords.x,
            coords.y,
            50,
            50,
            `shape=image;image=${paletteItem.imgPath};` +
            CConstants.mxStyles.matchingOperator
          ) as MyMxCell;

          cell.cellType = MyMxCellType.MATCHING_OPERATOR;
          cell.matchingOperatorType = paletteItem.id;
          cell.id = outer.objectIdService.getNewObjectId();

          const relationship = new MatchingOperatorRelationship();
          relationship.id = cell.id;
          relationship.coordX = cell.geometry.x;
          relationship.coordY = cell.geometry.y;
          relationship.matchingOperatorType = cell.matchingOperatorType;

          outer.matchingConfiguration.relationships.push(relationship);
        } else if (paletteItem.type === 'connector') {
          const cell = new mx.mxCell(
            undefined,
            new mx.mxGeometry(coords.x, coords.y, 0, 0),
            CConstants.mxStyles.matchingConnector
          ) as MyMxCell;
          cell.cellType = MyMxCellType.MATCHING_CONNECTOR;
          cell.setEdge(true);
          cell.setVertex(false);
          cell.geometry.setTerminalPoint(
            new mx.mxPoint(coords.x - 100, coords.y - 20),
            true
          );
          cell.geometry.setTerminalPoint(
            new mx.mxPoint(coords.x + 100, coords.y),
            false
          );
          cell.geometry.relative = true;

          graph.addCell(cell);
        }
      }
    };

    const onMouseUp = function(evt) {
      removeEventListeners(outer);
    };

    event.srcElement.addEventListener('dragend', onDragend);
    event.srcElement.addEventListener('mouseup', onMouseUp);
    event.srcElement.addEventListener('dragstart', onDragstart);
    this.graphContainer.nativeElement.addEventListener('dragover', onDragOver);

    function removeEventListeners(outerScope: any) {
      event.srcElement.removeEventListener('dragend', onDragend);
      event.srcElement.removeEventListener('mouseup', onMouseUp);
      event.srcElement.removeEventListener('dragstart', onDragstart);
      outerScope.graphContainer.nativeElement.removeEventListener(
        'dragover',
        onDragOver
      );
    }
  }

  handleDoubleClickEvent(event: mxgraph.mxEventObject) {
    const cell = event.properties.cell as MyMxCell;
    if (
      !isNullOrUndefined(cell) &&
      cell.cellType === MyMxCellType.MATCHING_OPERATOR &&
      !this.displayOverlay &&
      event.properties.event.button === 0 &&
      !this.deleteMode
    ) {
      this.handleOverlayOpened(event, cell);
    }
  }

  handleClickEvent(event: mxgraph.mxEventObject) {
    const cell = event.properties.cell as MyMxCell;

    if (
      !isNullOrUndefined(cell) &&
      cell.cellType === MyMxCellType.MATCHING_OPERATOR &&
      !this.displayOverlay &&
      event.properties.event.button === 0 &&
      this.deleteMode
    ) {
      if (this.confirmDelete) {
        this.dialogFactory
          .confirmationDialog(
            'Löschen bestätigen',
            'Soll der Operator wirklich gelöscht werden?'
          )
          .then((ret: boolean) => {
            if (ret) {
              this.deleteOperators([cell]);
            }
          });
      } else {
        this.deleteOperators([cell]);
      }
    }
  }

  /**
   * ...........Delete Mode..............
   */

  deleteOperators(cells: MyMxCell[]) {
    const cellsToRemove: MyMxCell[] = cells.filter(
      c => c.cellType === MyMxCellType.MATCHING_OPERATOR
    );

    try {
      this.graph.getModel().beginUpdate();
      this.graph.removeCells(cellsToRemove, true);
      this.matchingConfiguration.relationships = this.matchingConfiguration.relationships.filter(
        r => cellsToRemove.findIndex(c => r.id === c.id) < 0
      );
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  handleDeleteClickedEvent(
    event: MouseEvent,
    item: any,
    graph: mxgraph.mxGraph
  ) {
    this.deleteMode = !this.deleteMode;

    if (this.deleteMode) {
      this.renderer.setStyle(event.target, 'background', 'skyblue');
      this.graph.setEnabled(false);
    } else {
      this.renderer.setStyle(event.target, 'background', 'none');
      this.graph.setEnabled(true);
    }
  }

  /**
   * ...........Overlay..............
   */

  handleOverlayOpened(event: mxgraph.mxEventObject, cell: MyMxCell) {
    this.overlayRelationship = this.matchingConfiguration.relationships.find(
      r => r.id === cell.id
    );
    this.overlayEvent = event.properties.event;
    this.displayOverlay = true;

    this.graphContainer.nativeElement.style.overflow = 'hidden';
  }

  handleOverlayClosedEvent(event: MatchingOperatorRelationship) {
    this.displayOverlay = false;
    this.overlayRelationship = undefined;
    this.overlayEvent = undefined;
    this.graphContainer.nativeElement.style.overflow = 'scroll';

    if (!isNullOrUndefined(event)) {
      const index = this.matchingConfiguration.relationships.findIndex(
        r => r.id === event.id
      );
      this.matchingConfiguration.relationships[index] = event;

      try {
        this.graph.getModel().beginUpdate();

        let cell = this.graph.getModel().getCell(event.id) as MyMxCell;

        cell.matchingOperatorType = event.matchingOperatorType;
        this.graph.setCellStyle(
          `shape=image;image=${this.getPathForMatchingOperatorType(
            cell.matchingOperatorType
          )};` + CConstants.mxStyles.matchingOperator,
          [cell]
        );
        cell = this.graph.getModel().getCell(event.id) as MyMxCell;
      } finally {
        this.graph.getModel().endUpdate();
      }
    }
  }

  /**
   * ...........Key Handler..............
   */

  @HostListener('document:keypress', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.key === 'Delete') {
      const cells = this.graph.getSelectionCells() as MyMxCell[];
      this.deleteOperators(cells);
    }
  }
}
