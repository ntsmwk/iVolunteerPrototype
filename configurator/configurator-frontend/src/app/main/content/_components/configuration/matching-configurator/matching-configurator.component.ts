import { mxgraph } from 'mxgraph';
import { Component, OnInit, AfterContentInit, ViewChild, ElementRef, HostListener } from '@angular/core';
import { DialogFactoryDirective } from '../../_shared/dialogs/_dialog-factory/dialog-factory.component';
import { MatchingEntityDataService } from 'app/main/content/_service/configuration/matching-collector-configuration.service';
import { MatchingConfigurationService } from 'app/main/content/_service/configuration/matching-configuration.service';
import { MatchingOperatorRelationshipService } from 'app/main/content/_service/configuration/matching-operator-relationship.service';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { MatchingConfiguration, MatchingEntityMappingConfiguration } from 'app/main/content/_model/configurator/configurations';
import { CConstants } from '../class-configurator/utils-and-constants';
import { MatchingOperatorRelationship, MatchingEntityType, MatchingEntityMappings, MatchingEntity, MatchingDataRequestDTO } from 'app/main/content/_model/matching';
import { MyMxCell, MyMxCellType } from '../myMxCell';
import { MatchingConfiguratorPopupMenu } from './popup-menu';
import { PropertyType } from 'app/main/content/_model/configurator/property/property';
import { isNullOrUndefined } from 'util';
import { AddClassDefinitionDialogData } from './_dialogs/add-class-definition-dialog/add-class-definition-dialog.component';
import { NewMatchingDialogData } from './_dialogs/new-dialog/new-dialog.component';
import { ActivatedRoute, Router } from '@angular/router';
import { ResponseService } from 'app/main/content/_service/response.service';

const HEADER_WIDTH = 400;
const HEADER_HEIGHT = 50;
const HEADER_Y = 20;

const SPACE_Y = 20;
const SPACE_X = 20;

const CLASSDEFINITION_HEAD_HEIGHT = 40;
const CLASSDEFINITION_WIDTH = 200;
const CLASSDEFINTIION_OFFSET_X = 100;
const CLASSDEFINTIION_SPACE_Y = 10;

const PROPERTY_HEIGHT = 20;
const PROPERTY_SPACE_Y = 5;
const PROPERTY_SPACE_X = 5;

const BUTTON_WIDTH = 40;
const Button_HEIGHT = 40;

declare var require: any;
const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

@Component({
  selector: "app-matching-configurator",
  templateUrl: './matching-configurator.component.html',
  styleUrls: ['./matching-configurator.component.scss'],
  providers: [DialogFactoryDirective]
})
export class MatchingConfiguratorComponent implements OnInit, AfterContentInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private matchingCollectorConfigurationService: MatchingEntityDataService,
    private matchingConfigurationService: MatchingConfigurationService,
    private matchingOperatorRelationshipService: MatchingOperatorRelationshipService,
    private objectIdService: ObjectIdService,
    private dialogFactory: DialogFactoryDirective,
    private responseService: ResponseService,
  ) { }

  eventResponseAction: string;

  @ViewChild('graphContainer', { static: true }) private graphContainer: ElementRef;

  graph: mxgraph.mxGraph;
  data: MatchingDataRequestDTO;

  matchingOperatorPalettes = CConstants.matchingOperatorPalettes;
  matchingConnectorPalettes = CConstants.matchingConnectorPalettes;
  deleteOperationPalette = CConstants.deleteOperationPalette;

  displayOverlay: boolean;
  overlayRelationship: MatchingOperatorRelationship;
  overlayEvent: PointerEvent;

  // palette bar
  confirmDelete: boolean;
  includeConnectors: boolean;

  tenantId: string;
  redirectUrl: string;

  async ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (isNullOrUndefined(params['tenantId']) || isNullOrUndefined(params['redirect'])) {
        this.router.navigate(['main/invalid-parameters']);
      } else {
        this.tenantId = params['tenantId'];
        this.redirectUrl = params['redirect'];
      }
    });

    this.confirmDelete = true;
    this.includeConnectors = true;
  }

  async loadClassesAndRelationships(matchingConfiguration: MatchingConfiguration) {
    this.matchingCollectorConfigurationService
      .getMatchingData(matchingConfiguration)
      .toPromise().then((data: MatchingDataRequestDTO) => {
        this.data = data;
        this.redrawContent();
      });
  }

  ngAfterContentInit() {
    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);
    this.graph.isCellSelectable = function (cell) {
      const state = this.view.getState(cell);
      const style = state != null ? state.style : this.getCellStyle(cell);
      return (this.isCellsSelectable() && !this.isCellLocked(cell) && style['selectable'] !== 0);
    };

    this.graph.getCursorForCell = (cell: MyMxCell) => {
      if (isNullOrUndefined(cell.cellType)) {
        return 'default';
      }
      if (cell.cellType.startsWith('ADD_CLASS_BUTTON')) {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      }
    };

    this.graph.getEdgeValidationError = (edge: MyMxCell, source: MyMxCell, target: MyMxCell) => {
      if (!isNullOrUndefined(source) && !isNullOrUndefined(source.edges) && source.cellType === MyMxCellType.MATCHING_OPERATOR &&
        !isNullOrUndefined(edge.target) && edge.target.id === target.id) {
        if (source.edges.length >= 2) {
          return '';
        }
        for (const e of source.edges) {
          if (!isNullOrUndefined(e.source) && e.source.id === source.id) {
            return '';
          }
        }

      } else if (!isNullOrUndefined(target) && !isNullOrUndefined(target.edges) && target.cellType === MyMxCellType.MATCHING_OPERATOR
        && !isNullOrUndefined(edge.source) && edge.source.id === source.id) {
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

      this.graph.popupMenuHandler = this.createPopupMenu(this.graph);

      this.graph.setPanning(true);
      this.graph.useScrollbarsForPanning = true;

      this.graph.addListener(mx.mxEvent.CLICK, (sender: any, evt: mxgraph.mxEventObject) => {
        this.handleClickEvent(evt);
      });
      this.graph.addListener(mx.mxEvent.DOUBLE_CLICK, (sender: any, evt: mxgraph.mxEventObject) => {
        this.handleDoubleClickEvent(evt);
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

  redrawContent() {
    this.clearEditor();
    this.insertClassDefinitionsLeft();
    this.insertClassDefinitionsRight();
    this.insertMatchingOperatorsAndRelationships();
  }

  private insertClassDefinitionsLeft() {
    const title = this.graph.insertVertex(
      this.graph.getDefaultParent(), 'left_header', this.data.matchingConfiguration.leftSideName,
      SPACE_X, HEADER_Y, HEADER_WIDTH, HEADER_HEIGHT,
      CConstants.mxStyles.matchingRowHeader
    );
    title.setConnectable(false);

    let y = title.geometry.y + title.geometry.height + CLASSDEFINTIION_SPACE_Y;

    const cell = this.insertClassDefinitionsIntoGraph(
      this.data.matchingConfiguration.leftAddedClassDefinitionPaths,
      this.data.leftMappingConfigurations.mappings,
      new mx.mxGeometry(SPACE_X + CLASSDEFINTIION_OFFSET_X, y, CLASSDEFINITION_WIDTH, CLASSDEFINITION_HEAD_HEIGHT), 'l'
    ) as MyMxCell;

    if (!isNullOrUndefined(cell)) {
      y = cell.geometry.y + cell.geometry.height + CLASSDEFINTIION_SPACE_Y;
    }

    const addButton = this.graph.insertVertex(
      this.graph.getDefaultParent(), 'left_add', '+',
      SPACE_X + CLASSDEFINTIION_OFFSET_X + (CLASSDEFINITION_WIDTH / 2) - (BUTTON_WIDTH / 2), y, BUTTON_WIDTH, Button_HEIGHT,
      CConstants.mxStyles.matchingAddButton
    ) as MyMxCell;

    addButton.setConnectable(false);
    addButton.cellType = MyMxCellType.ADD_CLASS_BUTTON;
  }

  private insertClassDefinitionsRight() {
    const x = this.graphContainer.nativeElement.offsetWidth;
    const xHeader = x - HEADER_WIDTH - SPACE_X;
    const xClassDefinition = x - CLASSDEFINITION_WIDTH - CLASSDEFINTIION_OFFSET_X - SPACE_X;
    let y = HEADER_Y;

    const title = this.graph.insertVertex(
      this.graph.getDefaultParent(), 'right_header', this.data.matchingConfiguration.rightSideName,
      xHeader, y, HEADER_WIDTH, HEADER_HEIGHT, CConstants.mxStyles.matchingRowHeader
    );
    title.setConnectable(false);

    y = title.geometry.y + title.geometry.height + SPACE_Y;

    const cell = this.insertClassDefinitionsIntoGraph(
      this.data.matchingConfiguration.rightAddedClassDefinitionPaths, this.data.rightMappingConfigurations.mappings,
      new mx.mxGeometry(xClassDefinition, y, CLASSDEFINITION_WIDTH, CLASSDEFINITION_HEAD_HEIGHT), 'r'
    ) as MyMxCell;

    if (!isNullOrUndefined(cell)) {
      y = cell.geometry.y + cell.geometry.height + SPACE_Y;
    }

    const addButton = this.graph.insertVertex(
      this.graph.getDefaultParent(), 'right_add', '+',
      xClassDefinition + (CLASSDEFINITION_WIDTH / 2) - (BUTTON_WIDTH / 2), y, BUTTON_WIDTH, Button_HEIGHT,
      CConstants.mxStyles.matchingAddButton
    ) as MyMxCell;

    addButton.setConnectable(false);
    addButton.cellType = MyMxCellType.ADD_CLASS_BUTTON;
  }

  insertClassDefinitionsIntoGraph(ids: string[], mappings: MatchingEntityMappings, geometry: mxgraph.mxGeometry, pathPrefix: 'r' | 'l'): MyMxCell {
    let cell: MyMxCell;

    for (const id of ids) {
      const mapping = mappings.entities.find(e => e.path === id);
      cell = this.insertClassDefinition(id, mapping, geometry, pathPrefix);
      geometry = new mx.mxGeometry(cell.geometry.x, cell.geometry.y + cell.geometry.height + CLASSDEFINTIION_SPACE_Y, cell.geometry.width, cell.geometry.height);
    }
    return cell;
  }

  insertClassDefinition(id: string, matchingEntity: MatchingEntity, geometry: mxgraph.mxGeometry, pathPrefix: 'r' | 'l'): MyMxCell {
    let cell = new mx.mxCell(matchingEntity.classDefinition.name, geometry, CConstants.mxStyles.matchingClassNormal) as MyMxCell;
    cell.setVertex(true);
    cell.cellType = MyMxCellType.CLASS;

    let idPath = id;
    if (!isNullOrUndefined(pathPrefix)) {
      idPath = pathPrefix + matchingEntity.pathDelimiter + idPath;
    }
    cell.setId(idPath);

    cell = this.addPropertiesToCell(cell, matchingEntity,
      PROPERTY_SPACE_X, CLASSDEFINITION_HEAD_HEIGHT + PROPERTY_SPACE_Y,
      pathPrefix);

    const overlay = this.constructRemoveOverlay();
    this.graph.addCellOverlay(cell, overlay);

    const numberOfProperties =
      isNullOrUndefined(matchingEntity.classDefinition.properties.length) || matchingEntity.classDefinition.properties.length === 0
        ? 0 : matchingEntity.classDefinition.properties.length;
    const rectHeight = numberOfProperties === 0
      ? CLASSDEFINITION_HEAD_HEIGHT
      : CLASSDEFINITION_HEAD_HEIGHT + PROPERTY_SPACE_Y + (numberOfProperties * PROPERTY_HEIGHT) + PROPERTY_SPACE_Y;

    cell.geometry.setRect(cell.geometry.x, cell.geometry.y, cell.geometry.width, rectHeight);

    return this.graph.addCell(cell) as MyMxCell;
  }

  private constructRemoveOverlay() {
    const overlay = new mx.mxCellOverlay(
      new mx.mxImage('/assets/icons/class_editor/times-solid_white.png', 14, 20),
      'Overlay', mx.mxConstants.ALIGN_RIGHT, mx.mxConstants.ALIGN_TOP, new mx.mxPoint(-9, 10)
    );
    overlay.tooltip = 'entfernen';
    overlay.cursor = 'pointer';

    overlay.addListener(mx.mxEvent.CLICK, (sender: any, evt: mxgraph.mxEventObject) => {
      this.handleRemoveOverlayClickEvent(evt);
    });
    return overlay;
  }

  private addPropertiesToCell(cell: MyMxCell, entry: MatchingEntity, startX: number, startY: number, pathPrefix?: 'r' | 'l') {
    const classDefinition = entry.classDefinition;
    let lastPropertyGeometry = new mx.mxGeometry(40, 40);

    if (!isNullOrUndefined(classDefinition.properties)) {
      for (const p of classDefinition.properties) {
        let idPath = entry.path + entry.pathDelimiter + p.id;

        if (!isNullOrUndefined(pathPrefix)) {
          idPath = pathPrefix + entry.pathDelimiter + idPath;
        }

        const propertyEntry: MyMxCell = this.graph.insertVertex(
          cell, idPath, p.name,
          startX, startY + lastPropertyGeometry.height, 190, 20,
          CConstants.mxStyles.matchingProperty
        ) as MyMxCell;

        if (p.type === PropertyType.TREE) {
          propertyEntry.cellType = MyMxCellType.TREE_PROPERTY;
          propertyEntry.setStyle(CConstants.mxStyles.propertyTree);

        } else {
          propertyEntry.cellType = MyMxCellType.FLAT_PROPERTY;
        }
        propertyEntry.setConnectable(true);

        // propertyEntry.propertyId = p.id;
        lastPropertyGeometry = propertyEntry.geometry;
        startX = lastPropertyGeometry.x;
        startY = lastPropertyGeometry.y;
      }
    }
    return cell;
  }

  private insertMatchingOperatorsAndRelationships() {
    for (const entry of this.data.relationships) {
      const operatorCell = this.insertMatchingOperator(entry);

      let leftCell: MyMxCell;
      if (!isNullOrUndefined(entry.leftMatchingEntityPath)) {
        const leftCellId = 'l' + this.data.pathDelimiter + entry.leftMatchingEntityPath;
        leftCell = this.graph.getModel().getCell(leftCellId) as MyMxCell;
      }

      let rightCell: MyMxCell;
      if (!isNullOrUndefined(entry.rightMatchingEntityPath)) {
        const rightCellId = 'r' + this.data.pathDelimiter + entry.rightMatchingEntityPath;
        rightCell = this.graph.getModel().getCell(rightCellId) as MyMxCell;
      }

      this.insertRelationship(leftCell, operatorCell);
      this.insertRelationship(operatorCell, rightCell);
    }
  }

  private insertMatchingOperator(relationship: MatchingOperatorRelationship) {
    const cell = this.graph.insertVertex(
      this.graph.getDefaultParent(),
      relationship.id, null, relationship.coordX, relationship.coordY, 50, 50,
      `shape=image;image=${this.getPathForMatchingOperatorType(relationship.matchingOperatorType)};` + CConstants.mxStyles.matchingOperator
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
      this.graph.getDefaultParent(), null, null, sourceCell, targetCell, CConstants.mxStyles.matchingConnector
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

  /*
   * .........Top Menu Bar Options..........
   */

  consumeMenuOptionClickedEvent(event: any) {
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
        this.performNew(event.payload);
        break;
    }
  }

  private async performSave() {
    this.updateModel();
    const ret = <MatchingConfiguration>await this.matchingConfigurationService.saveMatchingConfiguration(this.data.matchingConfiguration).toPromise();
    this.data.matchingConfiguration = ret;
    await this.matchingOperatorRelationshipService.saveMatchingOperatorRelationships(this.data.relationships, this.data.matchingConfiguration.id).toPromise();
    await this.responseService.sendMatchingConfiguratorResponse(this.redirectUrl, ret.id, null, 'save').toPromise();
    this.redrawContent();
  }

  private updateModel() {
    const cells = this.graph.getChildCells(this.graph.getDefaultParent());
    const matchingOperatorCells = cells.filter((cell: MyMxCell) => cell.cellType === MyMxCellType.MATCHING_OPERATOR);

    const updatedRelationships: MatchingOperatorRelationship[] = [];

    for (const operatorCell of matchingOperatorCells) {
      if (isNullOrUndefined(operatorCell.edges)) {
        continue;
      }

      const relationship = this.data.relationships.find(
        r => r.id === operatorCell.id
      );

      relationship.matchingOperatorType = (operatorCell as MyMxCell).matchingOperatorType;
      relationship.coordX = operatorCell.geometry.x;
      relationship.coordY = operatorCell.geometry.y;

      let leftSet = false;
      let rightSet = false;

      for (const edge of operatorCell.edges) {
        if (isNullOrUndefined(edge.source) || isNullOrUndefined(edge.target)) {
          continue;
        }

        if (edge.target.id === operatorCell.id) {
          relationship.leftMatchingEntityPath = edge.source.id.substr(2);
          relationship.leftMatchingEntityType =
            (edge.source as MyMxCell).cellType === MyMxCellType.FLAT_PROPERTY
              ? MatchingEntityType.PROPERTY
              : MatchingEntityType.CLASS;

          leftSet = true;
        } else if (edge.source.id === operatorCell.id) {
          relationship.rightMatchingEntityPath = edge.target.id.substr(2);
          relationship.rightMatchingEntityType =
            (edge.target as MyMxCell).cellType === MyMxCellType.FLAT_PROPERTY
              ? MatchingEntityType.PROPERTY
              : MatchingEntityType.CLASS;

          rightSet = true;
          relationship.matchingConfigurationId = this.data.matchingConfiguration.id;
        }

        if (leftSet && rightSet) {
          break;
        }
      }
      updatedRelationships.push(relationship);
    }
    this.data.relationships = updatedRelationships;
  }

  performOpen(matchingConfiguration: MatchingConfiguration) {
    this.loadClassesAndRelationships(matchingConfiguration);
  }

  performNew(dialogData: NewMatchingDialogData) {
    const { rightClassConfiguration, rightIsUser, leftClassConfiguration, leftIsUser, label } = dialogData;
    const matchingConfiguration = new MatchingConfiguration({
      rightSideId: rightClassConfiguration.id, rightSideName: rightClassConfiguration.name, rightIsUser,
      leftSideId: leftClassConfiguration.id, leftSideName: leftClassConfiguration.name, leftIsUser,
      name: label, tenantId: this.tenantId
    });

    this.matchingConfigurationService.saveMatchingConfiguration(matchingConfiguration)
      .toPromise().then((ret: MatchingConfiguration) => {
        matchingConfiguration.id = ret.id;
        this.loadClassesAndRelationships(matchingConfiguration);
      });
  }

  /**
   * ...........Mouse Events..............
   */
  handleMousedownEvent(event: any, item: any, graph: mxgraph.mxGraph) {
    let positionEvent: MouseEvent;

    const onDragstart = (evt: any) => {
      evt.dataTransfer.setData('text', item.id);
      evt.dataTransfer.effect = 'move';
    };
    const onDragOver = (evt: any) => {
      positionEvent = evt;
      evt.dataTransfer.dropEffect = 'none';
    };
    const onDragend = (evt: any) => {
      const addObjectToGraph = (paletteItem: any) => {
        const coords: mxgraph.mxPoint = graph.getPointForEvent(positionEvent, false);

        graph.getModel().beginUpdate();
        if (paletteItem.type === 'matchingOperator') {
          const cell = this.constructOperator(coords, paletteItem);
          const relationship = new MatchingOperatorRelationship();
          relationship.id = cell.id;
          relationship.coordX = cell.geometry.x;
          relationship.coordY = cell.geometry.y;
          relationship.matchingOperatorType = cell.matchingOperatorType;

          this.data.relationships.push(relationship);

        } else if (paletteItem.type === 'connector') {
          graph.addCell(this.constructConnector(coords, -100, 100));
        }
      };

      evt.dataTransfer.getData('text');
      try {
        addObjectToGraph(item);
      } finally {
        graph.getModel().endUpdate();
        removeEventListeners();
      }
    };
    const onMouseUp = (evt: any) => {
      removeEventListeners();
    };

    event.srcElement.addEventListener('dragend', onDragend);
    event.srcElement.addEventListener('mouseup', onMouseUp);
    event.srcElement.addEventListener('dragstart', onDragstart);
    this.graphContainer.nativeElement.addEventListener('dragover', onDragOver);

    const removeEventListeners = () => {
      event.srcElement.removeEventListener('dragend', onDragend);
      event.srcElement.removeEventListener('mouseup', onMouseUp);
      event.srcElement.removeEventListener('dragstart', onDragstart);
      this.graphContainer.nativeElement.removeEventListener('dragover', onDragOver);
    };
  }

  private constructConnector(coords: mxgraph.mxPoint, sourceOffsetX: number, targetOffsetX: number) {
    const cell = new mx.mxCell(undefined, new mx.mxGeometry(coords.x, coords.y, 0, 0), CConstants.mxStyles.matchingConnector) as MyMxCell;
    cell.cellType = MyMxCellType.MATCHING_CONNECTOR;
    cell.setEdge(true);
    cell.setVertex(false);
    if (!isNullOrUndefined(sourceOffsetX)) {
      cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x + sourceOffsetX, coords.y), true);
    }
    if (!isNullOrUndefined(targetOffsetX)) {
      cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x + targetOffsetX, coords.y), false);
    }
    cell.geometry.relative = true;
    return cell;
  }

  private constructOperator(coords: mxgraph.mxPoint, paletteItem: any) {
    const cell = this.graph.insertVertex(this.graph.getDefaultParent(), null, null, coords.x, coords.y, 50, 50,
      `shape=image;image=${paletteItem.imgPath};` + CConstants.mxStyles.matchingOperator
    ) as MyMxCell;

    cell.cellType = MyMxCellType.MATCHING_OPERATOR;
    cell.matchingOperatorType = paletteItem.id;
    cell.id = this.objectIdService.getNewObjectId();

    if (this.includeConnectors) {
      const edge1 = this.constructConnector(coords, -100, undefined);
      cell.insertEdge(edge1, false);
      this.graph.addCell(edge1);

      const edge2 = this.constructConnector(coords, undefined, 150);
      cell.insertEdge(edge2, true);
      this.graph.addCell(edge2);
    }

    return cell;
  }

  handleDoubleClickEvent(event: mxgraph.mxEventObject) {
    const cell = event.properties.cell as MyMxCell;
    if (!isNullOrUndefined(cell) && cell.cellType === MyMxCellType.MATCHING_OPERATOR &&
      !this.displayOverlay && event.properties.event.button === 0
    ) {
      this.handleOverlayOpened(event, cell);
    }
  }

  handleClickEvent(event: mxgraph.mxEventObject) {
    const cell = event.properties.cell as MyMxCell;
    if (isNullOrUndefined(cell) || isNullOrUndefined(cell.cellType) || this.displayOverlay) {
      return;
    }

    if (event.properties.event.button === 0) {
      if (cell.cellType === MyMxCellType.ADD_CLASS_BUTTON) {

        let entityMappingConfiguration: MatchingEntityMappingConfiguration;
        let matchingConfiguration: MatchingConfiguration;
        const existingEntityPaths: string[] = [];

        if (cell.id === 'left_add') {
          entityMappingConfiguration = this.data.leftMappingConfigurations;
          matchingConfiguration = this.data.matchingConfiguration;
          existingEntityPaths.push(...this.data.matchingConfiguration.leftAddedClassDefinitionPaths);
        } else {
          entityMappingConfiguration = this.data.rightMappingConfigurations;
          matchingConfiguration = this.data.matchingConfiguration;
          existingEntityPaths.push(...this.data.matchingConfiguration.rightAddedClassDefinitionPaths);
        }

        this.dialogFactory.openAddClassDefinitionDialog(entityMappingConfiguration, existingEntityPaths).then((ret: AddClassDefinitionDialogData) => {
          if (!isNullOrUndefined(ret) && !isNullOrUndefined(ret.addedEntities)) {
            if (cell.id === 'left_add') {
              this.data.matchingConfiguration.leftAddedClassDefinitionPaths.push(...ret.addedEntities.map(e => e.path));
            } else {
              this.data.matchingConfiguration.rightAddedClassDefinitionPaths.push(...ret.addedEntities.map(e => e.path));
            }
            this.redrawContent();
          }
        });
      }
    }
  }

  private handleRemoveOverlayClickEvent(event: mxgraph.mxEventObject) {
    const cell: MyMxCell = event.properties['cell'];
    if (this.confirmDelete) {
      this.dialogFactory.confirmationDialog(
        'Löschen bestätigen',
        'Soll die Klasse wirklich entfernt werden?'
      ).then((ret: boolean) => {
        if (ret) {
          this.deleteClassDefinitionCell(cell);
        }
      });
    } else {
      this.deleteClassDefinitionCell(cell);
    }
  }

  /**
   * ...........Delete Mode..............
   */

  handleDeleteRelationship(cells: MyMxCell[]) {
    const operatorsToRemove: MyMxCell[] = cells.filter(c => c.cellType === MyMxCellType.MATCHING_OPERATOR);
    this.deleteOperators(operatorsToRemove);
    const connectorsToRemove: MyMxCell[] = cells.filter(c => c.cellType === MyMxCellType.MATCHING_CONNECTOR);
    this.deleteConnectors(connectorsToRemove);
  }

  private deleteOperators(cells: MyMxCell[]) {
    // const cellsToRemove: MyMxCell[] = cells.filter(c => c.cellType === MyMxCellType.MATCHING_OPERATOR);
    const cellsToRemove = cells;

    try {
      this.graph.getModel().beginUpdate();
      const removedCells = this.graph.removeCells(cellsToRemove, true);

      this.data.relationships = this.data.relationships.filter(
        r => cellsToRemove.findIndex(c => c.cellType === MyMxCellType.MATCHING_OPERATOR && r.id === c.id) < 0
      );
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private deleteConnectors(cells: MyMxCell[]) {
    const cellsToRemove = cells;

    try {
      this.graph.getModel().beginUpdate();
      const removeCells = this.graph.removeCells(cellsToRemove, false);
      // this.data.relationships = this.data.relationships.

    } finally {
      this.graph.getModel().endUpdate();
    }

  }

  private deleteClassDefinitionCell(deleteCell: MyMxCell) {
    this.updateModel();
    try {
      this.graph.getModel().beginUpdate();
      this.graph.removeCells([deleteCell], true);
      const actualId = deleteCell.id.substr(2);

      if (deleteCell.id.startsWith('l')) {
        this.data.matchingConfiguration.leftAddedClassDefinitionPaths = this.data.matchingConfiguration.leftAddedClassDefinitionPaths.filter(path => path !== actualId);
        const relationship = this.data.relationships.find(r => r.leftMatchingEntityPath.startsWith(actualId));

        if (!isNullOrUndefined(relationship)) {
          relationship.leftMatchingEntityPath = undefined;
          relationship.leftMatchingEntityType = undefined;
        }

      } else if (deleteCell.id.startsWith('r')) {
        this.data.matchingConfiguration.rightAddedClassDefinitionPaths = this.data.matchingConfiguration.rightAddedClassDefinitionPaths.filter(path => path !== actualId);
        const relationship = this.data.relationships.find(r => r.rightMatchingEntityPath.startsWith(actualId));

        if (!isNullOrUndefined(relationship)) {
          relationship.rightMatchingEntityPath = undefined;
          relationship.rightMatchingEntityType = undefined;
        }
      }
      this.redrawContent();

    } finally {
      this.graph.getModel().endUpdate();
    }
  }


  /**
   * ...........Overlay..............
   */

  handleOverlayOpened(event: mxgraph.mxEventObject, cell: MyMxCell) {
    this.overlayRelationship = this.data.relationships.find(r => r.id === cell.id);
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
      const index = this.data.relationships.findIndex(r => r.id === event.id);
      this.data.relationships[index] = event;

      try {
        this.graph.getModel().beginUpdate();

        let cell = this.graph.getModel().getCell(event.id) as MyMxCell;
        cell.matchingOperatorType = event.matchingOperatorType;
        this.graph.setCellStyle(
          `shape=image;image=${this.getPathForMatchingOperatorType(cell.matchingOperatorType)};` +
          CConstants.mxStyles.matchingOperator, [cell]
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
      if (this.confirmDelete) {
        this.dialogFactory.confirmationDialog(
          'Löschen bestätigen',
          'Soll das Objekt wirklich gelöscht werden?'
        ).then((ret: boolean) => {
          if (ret) {
            this.handleDeleteRelationship(cells);
          }
        });
      } else {
        this.handleDeleteRelationship(cells);
      }
    }
  }
}
