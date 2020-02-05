import { Component, OnInit, Input, ViewChild, ElementRef, AfterContentInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { ClassDefinition, ClassArchetype } from 'app/main/content/_model/meta/Class';
import { mxgraph } from 'mxgraph';
import { Relationship, RelationshipType, Association, AssociationCardinality, Inheritance } from 'app/main/content/_model/meta/Relationship';
import { isNullOrUndefined } from 'util';
import { DialogFactoryComponent } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { PropertyDefinition, PropertyItem, ClassProperty, PropertyType, EnumReference } from 'app/main/content/_model/meta/Property';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorService } from '../../_service/meta/core/configurator/configurator.service';
import { ObjectIdService } from '../../_service/objectid.service.';
import { CConstants, CUtils } from '../configurator-editor/utils-and-constants';
import { CoreHelpSeekerService } from '../../_service/core-helpseeker.service';
import { CoreFlexProdService } from '../../_service/core-flexprod.service';
import { LoginService } from '../../_service/login.service';
import { Participant, ParticipantRole } from '../../_model/participant';
import { myMxCell } from '../MyMxCell';
import { config } from 'rxjs';

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
  ) {

  }

  marketplace: Marketplace;

  eventResponseAction: string;

  @ViewChild('graphContainer', { static: true }) graphContainer: ElementRef;

  graph: mxgraph.mxGraph;

  producerConfigurator: Configurator;
  consumerConfigurator: Configurator;

  producerClassDefinitions: ClassDefinition;
  consumerClassDefinitions: ClassDefinition;

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

            this.classDefinitionService.getAllClassDefinitionsWithProperties(this.marketplace, 'slot1').toPromise().then((classDefintions: ClassDefinition[]) => {




            }),
            this.classDefinitionService.getAllClassDefinitionsWithProperties(this.marketplace, 'slot2').toPromise().then((classDefintions: ClassDefinition[]) => {

              
            })
          ]);
        });
      });


    });



    // console.log(this.configurableClasses);
    // console.log(this.relationships);
  }



  ngAfterContentInit() {
    this.graphContainer.nativeElement.style.position = 'absolute';
    this.graphContainer.nativeElement.style.overflow = 'hidden';
    this.graphContainer.nativeElement.style.left = '0px';
    this.graphContainer.nativeElement.style.top = '30px';
    this.graphContainer.nativeElement.style.right = '0px';
    this.graphContainer.nativeElement.style.bottom = '0px';
    this.graphContainer.nativeElement.style.background = 'white';

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
      new mx.mxRubberband(this.graph);

      this.graph.setPanning(true);

      // this.graph.popupMenuHandler = this.createPopupMenu(this.graph);

      const outer = this; // preserve outer scope
      this.graph.addListener(mx.mxEvent.CLICK, function (sender, evt) {
        // Handle Click
      });

      this.graph.getSelectionModel().addListener(mx.mxEvent.CHANGE, function (sender, evt) {
        // Handle Select
      });



    }
  }


  clearEditor() {
    this.graph.getModel().beginUpdate();
    try {
      this.graph.getModel().clear();
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private insertClassDefinitions() {

  }

  private insertClassDefinitionIntoGraph(classDefinition: ClassDefinition, geometry: mxgraph.mxGeometry) {
    // create class cell
    let cell: myMxCell;
    if (classDefinition.classArchetype.startsWith('ENUM')) {
      cell = new mx.mxCell(classDefinition.name, geometry, CConstants.mxStyles.classEnum) as myMxCell;
    } else {
      cell = new mx.mxCell(classDefinition.name, geometry, CConstants.mxStyles.classNormal) as myMxCell;
    }
    cell.root = classDefinition.root;
    cell.setCollapsed(false);
    cell.cellType = 'class';
    cell.classArchetype = classDefinition.classArchetype;
    cell.newlyAdded = false;
    cell.value = classDefinition.name;
    cell.setVertex(true);
    cell.setConnectable(true);

    // const overlay = new mx.mxCellOverlay(new mx.mxImage(classDefinition.imagePath, 30, 30), 'Overlay', mx.mxConstants.ALIGN_RIGHT, mx.mxConstants.ALIGN_TOP);
    // this.graph.addCellOverlay(cell, overlay);

    if (!isNullOrUndefined(classDefinition.id)) {
      cell.id = classDefinition.id;
    }

    // create vertical space before properties
    const vfiller = this.graph.insertVertex(cell, 'vfiller', null, 105, 45, 5, 130, CConstants.mxStyles.classVfiller);
    vfiller.setConnectable(false);
    cell.geometry.alternateBounds = new mx.mxRectangle(0, 0, 80, 30);
    cell.geometry.setRect(cell.geometry.x, cell.geometry.y, cell.geometry.width, classDefinition.properties.length * 20 + 25);

    // create properties TODO @Alex Refactor
    let i = 5;
    if (!isNullOrUndefined(classDefinition.properties)) {
      for (const p of classDefinition.properties) {
        const propertyEntry: myMxCell = this.graph.insertVertex(cell, p.id, p.name, 5, i + 45, 100, 20, CConstants.mxStyles.property) as myMxCell;

        if (p.type === PropertyType.ENUM) {
          propertyEntry.cellType = 'enum_property';
          propertyEntry.setStyle(CConstants.mxStyles.propertyEnum);

        } else {

          propertyEntry.cellType = 'property';
        }
        propertyEntry.setConnectable(false);

        propertyEntry.propertyId = p.id;
        i = i + 20;
      }
    }

    // create horizonal filler in front of properties
    const hfiller = this.graph.insertVertex(cell, 'hfiller', null, 0, i + 50 + 20, 85, 5, CConstants.mxStyles.classHfiller);
    hfiller.setConnectable(false);

    return this.graph.addCell(cell);
  }

  // private insertRelationshipIntoGraph(r: Relationship, coords: mxgraph.mxPoint, createNew: boolean) {

  //   const parent = this.graph.getDefaultParent();

  //   let source: myMxCell = this.graph.getModel().getCell(r.source) as myMxCell;
  //   let target: myMxCell = this.graph.getModel().getCell(r.target) as myMxCell;

  //   let cell: myMxCell;

  //   if (r.relationshipType === RelationshipType.INHERITANCE) {
  //     cell = new mx.mxCell(undefined, new mx.mxGeometry(coords.x, coords.y, 0, 0), CConstants.mxStyles.inheritance) as myMxCell;
  //     cell.cellType = 'inheritance';

  //     if (source.classArchetype.startsWith('ENUM_')) {
  //       cell.setStyle(CConstants.mxStyles.inheritanceEnum);
  //     }

  //   } else if (r.relationshipType === RelationshipType.ASSOCIATION) {
  //     cell = new mx.mxCell('', new mx.mxGeometry(coords.x, coords.y, 0, 0), CConstants.mxStyles.association) as myMxCell;
  //     cell.cellType = 'association';

  //     const cell1 = new mx.mxCell(AssociationCardinality[(r as Association).sourceCardinality], new mx.mxGeometry(-0.8, 0, 0, 0), CConstants.mxStyles.associationCell) as myMxCell;
  //     cell1.geometry.relative = true;
  //     cell1.setConnectable(false);
  //     cell1.vertex = true;
  //     cell1.cellType = 'associationLabel';
  //     cell1.setVisible(false);

  //     if (isNullOrUndefined(cell1.value)) {
  //       cell1.value = 'start';
  //     }
  //     cell.insert(cell1);

  //     const cell2 = new mx.mxCell(AssociationCardinality[(r as Association).targetCardinality], new mx.mxGeometry(0.8, 0, 0, 0), CConstants.mxStyles.associationCell) as myMxCell;
  //     cell2.geometry.relative = true;
  //     cell2.setConnectable(false);
  //     cell2.vertex = true;
  //     cell2.cellType = 'associationLabel';
  //     cell2.setVisible(false);

  //     if (isNullOrUndefined(cell2.value)) {
  //       cell2.value = 'end';
  //     }
  //     cell.insert(cell2);

  //   } else {
  //     console.error('invalid RelationshipType');
  //   }

  //   if (!createNew) {
  //     cell.id = r.id;
  //   } else {
  //     cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x - 100, coords.y - 20), true);
  //     cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x + 100, coords.y), false);

  //     source = undefined;
  //     target = undefined;
  //   }

  //   cell.newlyAdded = createNew;
  //   cell.geometry.relative = true;
  //   cell.edge = true;

  //   return this.graph.addEdge(cell, parent, source, target);
  // }

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



  // OLD STUFF - might still be needed later
  handleMousedownEvent(event: any, paletteItempaletteEntry: any, item: any, graph: mxgraph.mxGraph) {
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
        graph.getModel().endUpdate();

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




