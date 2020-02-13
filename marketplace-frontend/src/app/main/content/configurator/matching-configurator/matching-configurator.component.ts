import { Component, OnInit, ViewChild, ElementRef, AfterContentInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassDefinition, ClassArchetype } from 'app/main/content/_model/meta/Class';
import { mxgraph } from 'mxgraph';
import { isNullOrUndefined } from 'util';
import { DialogFactoryComponent } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { PropertyType } from 'app/main/content/_model/meta/Property';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from '../../_service/meta/core/configurator/configurator.service';
import { ObjectIdService } from '../../_service/objectid.service.';
import { CConstants } from '../configurator-editor/utils-and-constants';
import { CoreHelpSeekerService } from '../../_service/core-helpseeker.service';
import { CoreFlexProdService } from '../../_service/core-flexprod.service';
import { LoginService } from '../../_service/login.service';
import { Participant, ParticipantRole } from '../../_model/participant';
import { myMxCell } from '../MyMxCell';
import { MatchingConfiguratorClassDefinitionCollection, MatchingOperatorRelationshipStorage, MatchingOperatorRelationship } from '../../_model/matching';
import { MatchingConfiguratorPopupMenu } from './popup-menu';
import { MatchingOperatorRelationshipStorageService } from '../../_service/matchingoperator-relationship-storage.service';

declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

//tslint:disable-next-line: class-name

@Component({
  selector: 'app-matching-configurator',
  templateUrl: './matching-configurator.component.html',
  styleUrls: ['./matching-configurator.component.scss'],
  providers: [DialogFactoryComponent]

})
export class MatchingConfiguratorComponent implements OnInit, AfterContentInit {

  constructor(private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService,
    private dialogFactory: DialogFactoryComponent,
    private configuratorService: ConfiguratorService,
    private objectIdService: ObjectIdService,

    private loginService: LoginService,
    private flexProdService: CoreFlexProdService,
    private helpSeekerService: CoreHelpSeekerService,
    private matchingOperatorRelationshipService: MatchingOperatorRelationshipStorageService
  ) {

  }

  marketplace: Marketplace;

  eventResponseAction: string;

  @ViewChild('graphContainer', { static: true }) graphContainer: ElementRef;
  @ViewChild('paletteContainer', { static: true }) paletteContainer: ElementRef;

  graph: mxgraph.mxGraph;

  producerConfigurator: Configurator;
  consumerConfigurator: Configurator;

  producerClassDefinitions: ClassDefinition[];
  consumerClassDefinitions: ClassDefinition[];

  producerClassDefinitionCollections: MatchingConfiguratorClassDefinitionCollection[];
  consumerClassDefinitionCollections: MatchingConfiguratorClassDefinitionCollection[];

  matchingPalettes = CConstants.matchingPalettes;
  matchingConnectorPalettes = CConstants.matchingConnectorPalettes;

  matchingOperatorRelationshipStorage: MatchingOperatorRelationshipStorage;

  ngOnInit() {
    let service: CoreHelpSeekerService | CoreFlexProdService;
    // get marketplace
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
        if (role === 'FLEXPROD') {
          service = this.flexProdService;
        } else if (role === 'HELP_SEEKER') {
          service = this.helpSeekerService;
        }

        service.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
          if (!isNullOrUndefined(marketplace)) {
            this.marketplace = marketplace;
          }
        }).then(() => {
          Promise.all([
            this.classDefinitionService.getAllClassDefinitionsWithPropertiesCollection(this.marketplace, 'slot1').toPromise()
              .then((collections: MatchingConfiguratorClassDefinitionCollection[]) => {
                this.producerClassDefinitionCollections = collections;
                this.insertClassDefinitionsProducerFromCollection();
              }),
            this.classDefinitionService.getAllClassDefinitionsWithPropertiesCollection(this.marketplace, 'slot2').toPromise()
              .then((collections: MatchingConfiguratorClassDefinitionCollection[]) => {
                this.consumerClassDefinitionCollections = collections;
                this.insertClassDefinitionsConsumerFromCollection();
              })
          ]).then(() => {
            this.matchingOperatorRelationshipService.getMatchingOperatorRelationshipByConfiguratorIds(this.marketplace, 'slot1', 'slot2').toPromise()
              .then((storage: MatchingOperatorRelationshipStorage) => {
                this.matchingOperatorRelationshipStorage = storage;
                this.insertMatchingOperatorsAndRelationships();
              });
          });
        });
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
    this.paletteContainer.nativeElement.style.font = 'Arial, Helvetica, sans-serif';

    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);

    this.graph.isCellSelectable = function (cell) {
      const state = this.view.getState(cell);
      const style = (state != null) ? state.style : this.getCellStyle(cell);

      return this.isCellsSelectable() && !this.isCellLocked(cell) && style['selectable'] !== 0;
    };

    this.graph.getCursorForCell = function (cell: myMxCell) {
      if (cell.cellType === 'property' || cell.cellType === 'add' || cell.cellType === 'remove' ||
        cell.cellType === 'add_class_new_level' || cell.cellType === 'add_class_same_level' ||
        cell.cellType === 'add_association') {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      }
    };

    const modelGetStyle = this.graph.model.getStyle;
    this.graph.model.getStyle = function (cell) {
      if (cell != null) {
        let style = modelGetStyle.apply(this, arguments);

        if (this.isCollapsed(cell)) {
          style = style + ';shape=rectangle';
        }
        return style;
      }
      return null;
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

      const outer = this; // preserve outer scope

      this.graph.addListener(mx.mxEvent.CLICK, function (sender, evt) {
        // Handle Click


      });

      this.graph.getSelectionModel().addListener(mx.mxEvent.CHANGE, function (sender, evt) {
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

  private insertClassDefinitionsProducerFromCollection() {
    const title = this.graph.insertVertex(this.graph.getDefaultParent(), 'producer_header', 'Produzent', 20, 20, 400, 50, CConstants.mxStyles.matchingRowHeader);
    title.setConnectable(false);

    let y = title.geometry.y + title.geometry.height + 20;

    for (const c of this.producerClassDefinitionCollections) {
      const cell = this.insertClassDefinitionCollectionsIntoGraph(c, new mx.mxGeometry(120, y, 200, 0));
      y = cell.geometry.y + cell.geometry.height + 20;
    }
  }

  private insertClassDefinitionsConsumerFromCollection() {
    const x = this.graphContainer.nativeElement.offsetWidth - 220;
    let y = 20;

    const title = this.graph.insertVertex(this.graph.getDefaultParent(), 'consumer_header', 'Auftraggeber', x - 200, y, 400, 50, CConstants.mxStyles.matchingRowHeader);
    title.setConnectable(false);

    y = title.geometry.y + title.geometry.height + 20;


    for (const c of this.consumerClassDefinitionCollections) {
      const cell = this.insertClassDefinitionCollectionsIntoGraph(c, new mx.mxGeometry(x - 100, y, 200, 0));
      y = cell.geometry.y + cell.geometry.height + 20;
    }
  }

  private insertClassDefinitionCollectionsIntoGraph(collection: MatchingConfiguratorClassDefinitionCollection, geometry: mxgraph.mxGeometry): myMxCell {
    // create class cell
    let cell: myMxCell;
    if (collection.collector.classArchetype.startsWith('ENUM')) {
      cell = new mx.mxCell(collection.collector.name, geometry, CConstants.mxStyles.classEnum) as myMxCell;
    } else if (collection.collector.classArchetype === ClassArchetype.FLEXPROD_COLLECTOR) {
      cell = new mx.mxCell(collection.collector.name, geometry, CConstants.mxStyles.matchingClassFlexprodCollector) as myMxCell;
    } else {
      cell = new mx.mxCell(collection.collector.name, geometry, CConstants.mxStyles.matchingClassNormal) as myMxCell;
    }
    cell.setCollapsed(false);
    cell.classArchetype = collection.collector.classArchetype;
    cell.newlyAdded = false;
    cell.value = collection.collector.name;
    cell.setVertex(true);
    cell.setConnectable(true);

    // cell.setStyle(cell.getStyle() + 'foldable=0;movable=0;resizable=0;editable=0;deletable=0;');

    // const overlay = new mx.mxCellOverlay(new mx.mxImage(classDefinition.imagePath, 30, 30), 'Overlay', mx.mxConstants.ALIGN_RIGHT, mx.mxConstants.ALIGN_TOP);
    // this.graph.addCellOverlay(cell, overlay);

    if (!isNullOrUndefined(collection.collector.id)) {
      cell.id = collection.collector.id;
    }

    cell.geometry.alternateBounds = new mx.mxRectangle(0, 0, 80, 30);
    cell.geometry.setRect(cell.geometry.x, cell.geometry.y, cell.geometry.width, 20);

    let addPropertiesReturn = this.addPropertiesToCell(cell, collection.collector, 5, 45);
    cell = addPropertiesReturn.cell;

    for (const classDefinition of collection.classDefinitions) {
      const boundaryHeight = classDefinition.name.split(/\r?\n/).length * 25;

      const boundary = this.graph.insertVertex(
        cell, classDefinition.id, classDefinition.name, 0,
        addPropertiesReturn.lastPropertyGeometry.y + addPropertiesReturn.lastPropertyGeometry.height + 2,
        200, boundaryHeight, CConstants.mxStyles.matchingClassSeparator);

      boundary.setConnectable(true);
      addPropertiesReturn = this.addPropertiesToCell(cell, classDefinition, boundary.geometry.x + 5, boundary.geometry.y + boundary.geometry.height + 5);

    }

    cell.geometry.setRect(cell.geometry.x, cell.geometry.y, cell.geometry.width, cell.geometry.height + 5);
    return this.graph.addCell(cell) as myMxCell;
  }

  private addPropertiesToCell(cell: myMxCell, classDefinition: ClassDefinition, startX: number, startY: number) {
    let lastPropertyGeometry = new mx.mxGeometry(40, 40);

    if (!isNullOrUndefined(classDefinition.properties)) {
      for (const p of classDefinition.properties) {
        const propertyEntry: myMxCell = this.graph.insertVertex(
          cell, classDefinition.id + '_' + p.id, p.name, startX, startY + lastPropertyGeometry.height,
          190, 20, CConstants.mxStyles.matchingProperty) as myMxCell;

        if (p.type === PropertyType.ENUM) {
          propertyEntry.cellType = 'enum_property';
          propertyEntry.setStyle(CConstants.mxStyles.propertyEnum);
        } else {
          propertyEntry.cellType = 'property';
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
    for (const entry of this.matchingOperatorRelationshipStorage.relationships) {
      const operatorCell = this.insertMatchingOperator(entry.coordX, entry.coordY, entry.matchingOperatorType);

      let producerCell: myMxCell;
      if (!isNullOrUndefined(entry.producerId)) {
        producerCell = this.graph.getModel().getCell(entry.producerId) as myMxCell;
      }
      let consumerCell: myMxCell;
      if (!isNullOrUndefined(entry.consumerId)) {
        consumerCell = this.graph.getModel().getCell(entry.consumerId) as myMxCell;
      }

      this.insertRelationship(producerCell, operatorCell);
      this.insertRelationship(operatorCell, consumerCell);
    }

  }

  private insertMatchingOperator(coordX: number, coordY: number, matchingOperatorType: string) {
    const cell = this.graph.insertVertex(
      this.graph.getDefaultParent(), null, null, coordX, coordY, 50, 50,
      `shape=image;image=${this.getPathForMatchingOperatorType(matchingOperatorType)};` + CConstants.mxStyles.matchingOperator) as myMxCell;
    cell.cellType = 'matchingOperator';
    cell.operatorType = matchingOperatorType;

    return cell as myMxCell;
  }

  private insertRelationship(sourceCell: myMxCell, targetCell: myMxCell) {

    if (isNullOrUndefined(sourceCell) || isNullOrUndefined(targetCell)) {
      return;
    }

    const edge = this.graph.insertEdge(this.graph.getDefaultParent(), null, null, sourceCell, targetCell, CConstants.mxStyles.matchingConnector) as myMxCell;
    edge.cellType = 'matchingConnector';

    return edge;
  }

  private getPathForMatchingOperatorType(matchingOperatorType: String) {
    const paletteItem = CConstants.matchingPalettes.find((ret) => ret.id === matchingOperatorType);

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
    this.graph.scrollCellToVisible((function getLeftMostCell() {
      return outer.graph.getModel().getChildCells(outer.graph.getDefaultParent()).find((c: myMxCell) => c.root);
    })(), false);

    const translate = this.graph.view.getTranslate();
    this.graph.view.setTranslate(translate.x + 10, translate.y + 10);
  }

  navigateBack() {
    window.history.back();
  }

  consumeMenuOptionClickedEvent(event: any) {
    if (event.id === 'editor_save') {
      const cells = this.graph.getChildCells(this.graph.getDefaultParent());
      const matchingOperatorCells = cells.filter((cell: myMxCell) => cell.cellType === 'matchingOperator');

      const newRelationships: MatchingOperatorRelationship[] = [];

      for (const operatorCell of matchingOperatorCells) {
        const relationship = new MatchingOperatorRelationship();
        relationship.matchingOperatorType = (operatorCell as myMxCell).operatorType;
        relationship.coordX = operatorCell.geometry.x;
        relationship.coordY = operatorCell.geometry.y;

        let producerSet = false;
        let consumerSet = false;

        for (const edge of operatorCell.edges) {

          if (!isNullOrUndefined(edge.source) && !isNullOrUndefined(edge.target)) {
            if (edge.target.id === operatorCell.id) {
              relationship.producerId = edge.source.id;
              producerSet = true;
            } else if (edge.source.id === operatorCell.id) {
              relationship.consumerId = edge.target.id;
              consumerSet = true;
            }
          }

          if (producerSet && consumerSet) {
            break;
          }

        }

        newRelationships.push(relationship);
      }

      this.matchingOperatorRelationshipStorage.relationships = newRelationships;

      this.matchingOperatorRelationshipService.saveMatchingOperatorRelationshipStorage(this.marketplace, this.matchingOperatorRelationshipStorage).toPromise()
        .then((ret: MatchingOperatorRelationshipStorage) => {
          // do stuff
        });
    }
  }




  // OLD STUFF - might still be needed later
  handleMousedownEvent(event: any, item: any, graph: mxgraph.mxGraph) {
    const outer = this;
    let positionEvent: MouseEvent;

    const onDragstart = function (evt) {
      evt.dataTransfer.setData('text', item.id);
      evt.dataTransfer.effect = 'move';
      evt.dataTransfer.effectAllowed = 'move';
    };

    const onDragOver = function (evt) {
      positionEvent = evt;
    };

    const onDragend = function (evt) {
      evt.dataTransfer.getData('text');
      try {
        addObjectToGraph(evt, item);
      } finally {
        graph.getModel().endUpdate();
        removeEventListeners(outer);
      }

      function addObjectToGraph(dragEndEvent: MouseEvent, paletteItem: any) {

        const coords: mxgraph.mxPoint = graph.getPointForEvent(positionEvent, false);
        graph.getModel().beginUpdate();
        if (paletteItem.type === 'matchingOperator') {


          const cell = graph.insertVertex(graph.getDefaultParent(), null, null, coords.x, coords.y, 50, 50,
            `shape=image;image=${paletteItem.imgPath};` + CConstants.mxStyles.matchingOperator) as myMxCell;

          cell.cellType = 'matchingOperator';
          cell.operatorType = paletteItem.id;

        } else if (paletteItem.type === 'connector') {
          const cell = new mx.mxCell(undefined, new mx.mxGeometry(coords.x, coords.y, 0, 0), CConstants.mxStyles.matchingConnector) as myMxCell;
          cell.cellType = 'matchingConnector';
          cell.setEdge(true);
          cell.setVertex(false);
          cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x - 100, coords.y - 20), true);
          cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x + 100, coords.y), false);
          cell.geometry.relative = true;

          graph.addCell(cell);
        }
      }
    };

    const onMouseUp = function (evt) {
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
      outerScope.graphContainer.nativeElement.removeEventListener('dragover', onDragOver);

    }
  }
}




