import { Component, OnInit, Input, AfterViewInit, ViewChild, ElementRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ClassDefinitionService } from '../../../_service/meta/core/class/class-definition.service';
import { ClassDefintion } from '../../../_model/meta/Class';
import { mxgraph } from "mxgraph";
import { Relationship, RelationshipType, Association, AssociationParameter } from 'app/main/content/_model/meta/Relationship';
import { isNullOrUndefined } from 'util';
import { DialogFactoryComponent } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { PropertyService } from 'app/main/content/_service/property.service';
import { Property, PropertyDefinition, ClassProperty } from 'app/main/content/_model/meta/Property';
import { removeAllListeners } from 'cluster';



const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

export class myMxCell extends mx.mxCell {
  cellType: string;
}


@Component({
  selector: 'app-classes-editor',
  templateUrl: './classes-editor.component.html',
  styleUrls: ['./classes-editor.component.scss'],
  providers: [DialogFactoryComponent]

})
export class ClassesEditorComponent implements OnInit, AfterViewInit {

  @Input() marketplace: Marketplace;
  @Input() configurableClasses: ClassDefintion[];
  @Input() relationships: Relationship[];

  isLoaded: boolean = false;

  // dataSource = new MatTableDataSource<ConfigurableClass>();
  // displayedColumns = ['id'];

  properties: Property<any>[];



  constructor(private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService,
    private propertyService: PropertyService,
    private dialogFactory: DialogFactoryComponent) {

  }


  sidebarPalettes = [
    {
      id: 'building_blocks', label: 'Building Blocks',
      items: [
        { id: 'class', label: 'Class', imgPath: 'images/rectangle.gif', type: 'vertex', shape: undefined },
      ]
    },
    {
      id: 'relationships', label: 'Relationships',
      items: [
        { id: 'inheritance', label: 'Inheritance', imgPath: undefined, type: 'edge', shape: undefined },
        { id: 'association', label: 'Association', imgPath: undefined, type: 'relation', shape: undefined },
      ]
    }
  ];

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
  currentClass: ClassDefintion;


  // toolbar: mxgraph.mxToolbar;
  graph: mxgraph.mxGraph;
  standardTranslate: mxgraph.mxPoint;

  ngOnInit() {
    // this.dataSource.data = this.configurableClasses;

    console.log(this.configurableClasses);
    console.log(this.relationships);
  }

  ngAfterViewInit() {
    this.graphContainer.nativeElement.style.position = 'absolute';
    this.graphContainer.nativeElement.style.overflow = 'hidden';
    this.graphContainer.nativeElement.style.left = '200px';
    this.graphContainer.nativeElement.style.top = '0px';
    this.graphContainer.nativeElement.style.right = '0px';
    this.graphContainer.nativeElement.style.bottom = '0px';
    this.graphContainer.nativeElement.style.background = 'rgb(230,230,230)';
    // this.graphContainer.nativeElement.style.margin = '5px';

    this.leftSidebarContainer.nativeElement.style.position = 'absolute';
    this.leftSidebarContainer.nativeElement.style.overflow = 'hidden';
    this.leftSidebarContainer.nativeElement.style.padding = '2px';
    this.leftSidebarContainer.nativeElement.style.left = '0px';
    this.leftSidebarContainer.nativeElement.style.top = '0px';
    this.leftSidebarContainer.nativeElement.style.width = '200px';
    this.leftSidebarContainer.nativeElement.style.bottom = '0px';
    this.leftSidebarContainer.nativeElement.style.background = 'lightgrey';


    this.rightSidebarContainer.nativeElement.style.position = 'absolute';
    this.rightSidebarContainer.nativeElement.style.overflow = 'hidden';
    this.rightSidebarContainer.nativeElement.style.padding = '2px';
    this.rightSidebarContainer.nativeElement.style.right = '0px';
    this.rightSidebarContainer.nativeElement.style.top = '0px';
    this.rightSidebarContainer.nativeElement.style.width = '200px';
    this.rightSidebarContainer.nativeElement.style.bottom = '0px';
    this.rightSidebarContainer.nativeElement.style.background = 'lightgrey';
    this.rightSidebarContainer.nativeElement.style.display = 'none';

    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);


    // this.toolbar = new mx.mxToolbar(this.toolbarContainer.nativeElement);



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

      const outer = this; //preserve outer scope
      this.graph.popupMenuHandler = new mx.mxPopupMenuHandler(this.graph, function (menu, cell, evt) {
        return outer.createPopupMenu(this.graph, menu, cell, evt);
      });


      // this.addHelloWorldToGraphTest(); //Remove
      this.parseServerContent();

      let layout = new mx.mxHierarchicalLayout(this.graph, mx.mxConstants.DIRECTION_EAST);


      layout.execute(this.graph.getDefaultParent());
      this.resetViewport();


      // console.log(this.graph.getModel());
      // const encoder = new mx.mxCodec('');
      // var node = encoder.encode(this.graph.getModel());
      // console.log(mx.mxUtils.getPrettyXml(node));
    }




  }

  private parseServerContent() {
    this.parseIncomingClasses();
    this.parseIncomingRelationships();
  }

  private parseIncomingClasses() {

    let drawOverlay = true;

    const parent = this.graph.getDefaultParent();

    this.graph.getModel().beginUpdate();
    try {
      for (let c of this.configurableClasses) {
        let cell = this.graph.insertVertex(parent, c.id, c.name, 20, 20, 80, 30);
        if (drawOverlay) {
          var overlay = new mx.mxCellOverlay(new mx.mxImage("/images/dude3.png", 40, 40), "Overlay", mx.mxConstants.ALIGN_LEFT, mx.mxConstants.ALIGN_TOP);
          this.graph.addCellOverlay(cell, overlay);
          drawOverlay = false;
        }
      }
    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  private parseIncomingRelationships() {
    const parent = this.graph.getDefaultParent();

    this.graph.getModel().beginUpdate();
    try {
      for (let r of this.relationships) {

        var source = this.graph.getModel().getCell(r.classId2);
        var target = this.graph.getModel().getCell(r.classId1);

        if (r.relationshipType == RelationshipType.INHERITANCE) {
          var cell = new myMxCell(undefined, new mx.mxGeometry(0, 0, 100, 100), 'curved=1;endArrow=classic;html=1;');
          cell.geometry.setTerminalPoint(new mx.mxPoint(), true);
          cell.geometry.setTerminalPoint(new mx.mxPoint(), false);

          cell.geometry.relative = true;
          cell.edge = true;
          cell.cellType = 'inheritance'
          this.graph.addEdge(cell, parent, source, target);


        } else if (r.relationshipType == RelationshipType.ASSOCIATION) {
          var edge = new myMxCell('', new mx.mxGeometry(0, 0, 0, 0), 'endArrow=none;html=1;curved=1');
          edge.geometry.setTerminalPoint(new mx.mxPoint(), true);
          edge.geometry.setTerminalPoint(new mx.mxPoint(), false);
          edge.geometry.relative = true;
          edge.edge = true;
          edge.cellType = 'association';


          var cell1 = new myMxCell(AssociationParameter[(r as Association).param1], new mx.mxGeometry(-0.8, 0, 0, 0), 'resizable=0;html=1;align=left;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;');
          cell1.geometry.relative = true;
          cell1.setConnectable(false);
          cell1.vertex = true;
          cell1.cellType = 'associationLabel'

          edge.insert(cell1);

          var cell2 = new myMxCell(AssociationParameter[(r as Association).param2], new mx.mxGeometry(0.8, 0, 0, 0), 'resizable=0;html=1;align=right;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;');
          cell2.geometry.relative = true;
          cell2.setConnectable(false);
          cell2.vertex = true;
          cell2.cellType = 'associationLabel'

          edge.insert(cell2);

          this.graph.addEdge(edge, parent, source, target);


        }



        // console.log(source);
        // console.log(target);



      }

    } finally {
      this.graph.getModel().endUpdate();
    }
  }

  createPopupMenu(graph: mxgraph.mxGraph, menu: mxgraph.mxPopupMenu, cell: myMxCell, evt) {
    let outer = this;

    if (cell != null) {
      if (cell.isEdge()) {

        if (cell.cellType == 'association') {
          var item1 = menu.addItem('Set Cardinality', null, null, null, null, true, false);

          console.log(cell);
          menu.addSeparator(null, true);
          //on error: define "	var td;" in mxgraph/build.js line 15755
          var nestedMenu1 = menu.addItem(`"start" Node (${cell.children[0].value})`, null, null, null, null, null, null);
          var nestedMenu2 = menu.addItem(`"end" Node (${cell.children[1].value})`, null, null, null, null, null, null);

          this.addCardinalitiesToSubmenu(graph, menu, nestedMenu1, cell, 0);
          this.addCardinalitiesToSubmenu(graph, menu, nestedMenu2, cell, 1);

          menu.addSeparator(null, false);

        }
      } else if (cell.isVertex()) {

        if (cell.cellType == 'associationLabel') {
          var item1 = menu.addItem('Set Cardinality', null, null, null, null, true, false);
          this.addCardinalitiesToSubmenu(graph, menu, undefined, cell, undefined);
          menu.addSeparator(null, false);

        } else {

          var showPropertiesItem = menu.addItem("Show Properties", null, function () {

            outer.toggleRightSidebar(true);
            outer.rightSidebarContext = 'properties';

            outer.currentClass = outer.configurableClasses.find((c: ClassDefintion) => {
              return c.id == cell.id;
            });
            console.log(cell.id);
            console.log(outer.currentClass);

          }, null, null, true, true);

          var createClassInstanceItem = menu.addItem('Create new Instance', null, function() {
            outer.createClassInstanceClicked(cell);
          },null, null, true, true);

        }
      }

      //Options present in every cell (vertexes as well as edges)
      var testItem = menu.addItem("Print cell to console", null, function () {
        if (cell.isVertex()) {
          console.log(cell);

          // graph.getModel().remove(cell);
        } else {
          console.log(cell);
        }
      }, null, null, true, true);

      var deleteItem = menu.addItem("Delete", null, function () {
        if (cell.isVertex()) {
          graph.getModel().beginUpdate();

          if (!isNullOrUndefined(cell.edges)) {
            for (let e of cell.edges) {
              if (!isNullOrUndefined(e.source) && e.source.id == cell.id) {
                e.source = null;
              }

              if (!isNullOrUndefined(e.target) && e.target.id == cell.id) {
                e.target = null;
              }
            }
          }
          cell.edges = [];
          graph.getModel().remove(cell);
          graph.getModel().endUpdate();
        } else {
          graph.getModel().remove(cell);
        }


        let i = outer.configurableClasses.findIndex((c: ClassDefintion) => {
          return c.id == cell.id;
        });

        if (i >= 0) {
          outer.configurableClasses.splice(i, 1);
        }


      }, null, null, true, true);

      var copyItem = menu.addItem("Duplicate", null, function () {

        // console.log(dupe.getGeometry());
        if (cell.isVertex()) {
          if (cell.cellType == 'associationLabel') {
            duplicateEdge(cell.getParent());
          } else {
            duplicateVertex(cell);
          }
        } else if (cell.isEdge()) {
          duplicateEdge(cell);


        }

        function duplicateEdge(cell: mxgraph.mxCell) {
          var dupe: myMxCell = graph.getModel().cloneCell(cell);
          if (!isNullOrUndefined(cell.geometry.points)) {
            dupe.getGeometry().points[0].x = dupe.getGeometry().sourcePoint.x = cell.getGeometry().points[0].x + 20;
            dupe.getGeometry().points[0].y = dupe.getGeometry().sourcePoint.y = cell.getGeometry().points[0].y + 20;
            dupe.getGeometry().points[1].x = dupe.getGeometry().targetPoint.x = cell.getGeometry().points[1].x + 20;
            dupe.getGeometry().points[1].y = dupe.getGeometry().targetPoint.y = cell.getGeometry().points[1].y + 20;
          } else {
            dupe.getGeometry().sourcePoint.x += 20;
            dupe.getGeometry().sourcePoint.y += 20;
            dupe.getGeometry().targetPoint.x += 20;
            dupe.getGeometry().targetPoint.y += 20;
          }

          graph.addCell(dupe);

        }

        function duplicateVertex(cell: mxgraph.mxCell) {
          var dupe: myMxCell = graph.getModel().cloneCell(cell);

          dupe.getGeometry().x += 20;
          dupe.getGeometry().y += 20;
          graph.addCell(dupe);

        }

      }, null, null, true, true);


    } else {
      console.log("clicked empty canvas space");
      console.log(event);
      console.log(graph);

    }
  }

  addCardinalitiesToSubmenu(graph: mxgraph.mxGraph, menu: mxgraph.mxPopupMenu, parent: HTMLTableRowElement, cell: mxgraph.mxCell, childId: number) {
    menu.addItem('0...*', null, function () {
      setCellValue(cell, childId, '0...*');
    }, parent, null, null, null);

    menu.addItem('0...1', null, function () {
      setCellValue(cell, childId, '0...1');


    }, parent, null, null, null);

    menu.addItem('1', null, function () {

      setCellValue(cell, childId, '1');

    }, parent, null, null, null);

    menu.addItem('1...*', null, function () {
      setCellValue(cell, childId, '1...*');


    }, parent, null, null, null);


    function setCellValue(cell: mxgraph.mxCell, childId: number, value: string) {


      if (!isNullOrUndefined(cell.children)) {

        graph.getModel().beginUpdate();

        graph.getModel().getChildren(cell)[childId].setValue(value);

        //workaround to get graph to update
        graph.getModel().remove(cell);
        graph.addCell(cell);

        graph.getModel().endUpdate();

      } else {

        graph.getModel().beginUpdate()

        cell.setValue(value);
        graph.getModel().remove(cell.getParent());
        graph.addCell(cell.getParent());

        graph.getModel().endUpdate();

        // console.log(cell);
        // console.log(cell.getParent());
      }

    }
  }

  mousedownEvent(event: any, paletteItempaletteEntry: any, item: any, graph: mxgraph.mxGraph) {
    const outer = this;

    
    console.log(event);
    console.log(item);

    let positionEvent: MouseEvent;

    var onDragstart = function (evt) {
      evt.dataTransfer.setData('text', item.id);
      evt.dataTransfer.effect = "move"
      evt.dataTransfer.effectAllowed = "move";


    }

    var onDragOver = function(evt) {
     
      positionEvent = evt;
    }


    var onDragend = function (evt) {


      
      evt.dataTransfer.getData('text');

      

      try {
        addObjectToGraph(evt, item, graph);
      } finally {
        console.log("ending update");
        graph.getModel().endUpdate();
        event.srcElement.removeEventListener('dragend', onDragend);
        event.srcElement.removeEventListener('mouseup', onMouseUp);
        event.srcElement.removeEventListener("dragstart", onDragstart);
        outer.graphContainer.nativeElement.removeEventListener("dragover", onDragOver);

      }

      function addObjectToGraph(dragEndEvent: MouseEvent, paletteItem: any, graph: mxgraph.mxGraph) {
         
        const coords = graph.getPointForEvent(positionEvent, false);

        

        // const coords = new mx.mxPoint(dragEndEvent.pageX, dragEndEvent.pageY);
        console.log(coords);
        console.log(paletteItem);
        console.log("-------");
        graph.getModel().beginUpdate();
        if (paletteItem.id == 'class') {
          console.log("adding class");
          let cell = graph.insertVertex(graph.getDefaultParent(), null, "Class", coords.x, coords.y, 80, 30);

          cell.id = "new" + cell.id;
          let addedClass = new ClassDefintion();
          addedClass.id = cell.id;
          addedClass.name = cell.value;
          addedClass.properties = [];

          outer.configurableClasses.push(addedClass);


        } else if (paletteItem.id == 'inheritance') {
          let cell = new myMxCell(undefined, new mx.mxGeometry(0, 0, 100, 100), 'curved=1;endArrow=classic;html=1;');
          cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x - 100, coords.y - 20), true);
          cell.geometry.setTerminalPoint(new mx.mxPoint(coords.x + 100, coords.y), false);

          cell.geometry.relative = true;
          cell.edge = true;
          cell.cellType = 'inheritance'

          graph.addCell(cell);
        } else if (paletteItem.id == 'association') {
          //UML RELATION
          var edge = new myMxCell('', new mx.mxGeometry(0, 0, 0, 0), 'endArrow=none;html=1;curved=1');
          edge.geometry.setTerminalPoint(new mx.mxPoint(coords.x - 100, coords.y - 20), true);
          edge.geometry.setTerminalPoint(new mx.mxPoint(coords.x + 100, coords.y), false);
          edge.geometry.relative = true;
          edge.edge = true;
          edge.cellType = 'association';

          var cell1 = new myMxCell('start', new mx.mxGeometry(-1, 0, 0, 0), 'resizable=0;html=1;align=left;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;');
          cell1.geometry.relative = true;
          cell1.setConnectable(false);
          cell1.vertex = true;
          cell1.cellType = 'associationLabel'

          edge.insert(cell1);

          var cell2 = new myMxCell('end', new mx.mxGeometry(1, 0, 0, 0), 'resizable=0;html=1;align=right;verticalAlign=bottom;labelBackgroundColor=#ffffff;fontSize=10;');
          cell2.geometry.relative = true;
          cell2.setConnectable(false);
          cell2.vertex = true;
          cell2.cellType = 'associationLabel'

          edge.insert(cell2);

          graph.addCell(edge);

        }
      }
    }

    var onMouseUp = function (evt) {
      event.srcElement.removeEventListener("dragend", onDragend);
      event.srcElement.removeEventListener("mouseup", onMouseUp);
      event.srcElement.removeEventListener("dragstart", onDragstart);
      outer.graphContainer.nativeElement.removeEventListener("dragover", onDragOver);
    }

    event.srcElement.addEventListener("dragend", onDragend);
    event.srcElement.addEventListener("mouseup", onMouseUp);
    event.srcElement.addEventListener("dragstart", onDragstart);
    this.graphContainer.nativeElement.addEventListener("dragover", onDragOver);

  }

  removeDragListeners(event: any) {

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
      let ret: mxgraph.mxCell;

      for (let c of outer.graph.getModel().getChildren(outer.graph.getDefaultParent())) {
        if (isNullOrUndefined(ret)) {
          ret = c;
        } else if (!isNullOrUndefined(c.geometry) && c.geometry.x < ret.geometry.x) {
          ret = c;
        }
      }
      return ret;

    })(), false);
    const translate = this.graph.view.getTranslate()
    this.graph.view.setTranslate(translate.x + 25, translate.y + 25);
  }




  doShitWithGraph(graph: mxgraph.mxGraph) {

    console.log(graph);

  }

  saveGraphClicked() {
   console.log(this.graph.getModel().cells);
   console.log(this.graph.getModel());


  }

  createClassInstanceClicked(cell: myMxCell) {
    console.log("create class instance clicked");
    console.log(cell);

    if (cell.id.startsWith('new')) {
      console.log("you have to save first");

      //TODO
      this.showWorkInProgressInfo = true;
        let outer = this;
        setTimeout(function() {
          outer.showWorkInProgressInfo = false;
        }, 5000);
    } else {
      this.router.navigate([`main/configurator/instance-editor/${this.marketplace.id}/${cell.id}`]);
    }
  }




  clickEvent(event: any) {
    console.log(event);
  }


  /**
   * SIDEBAR
   */

  propertiesAdded: boolean = false;

  addPropertyClicked(currentClass: ClassDefintion) {
    if (isNullOrUndefined(this.properties)) {
      this.propertyService.getProperties(this.marketplace).toPromise().then((properties: Property<any>[]) => {
        console.log("addPropertyCLicked")
        console.log(properties);
        if (!isNullOrUndefined(properties)) {
          this.properties = properties;
          this.displayAddPropertyDialog(currentClass);
        }
      });
    } else {
      this.displayAddPropertyDialog(currentClass);
    }

  }

  private displayAddPropertyDialog(currentClass: ClassDefintion) {
    //TODO
    this.dialogFactory.addPropertyDialogGeneric(this.properties, this.currentClass.properties).then((ret: Property<any>[]) => {
      if (!isNullOrUndefined(ret)) {
        currentClass.properties.push(...ret);
        this.propertiesAdded = true;
      }
    });

  }

  showWorkInProgressInfo: boolean = false;
  saveAddedProperties() {
    this.classDefinitionService.savePropertiesLegacy(this.marketplace, this.currentClass.id, this.currentClass.properties).toPromise().then((ret: ClassDefintion) => {
      console.log("saveaddedPropertieslegacy")
      console.log(ret); 
      this.propertiesAdded = false;

      if (isNullOrUndefined(ret)) {
        this.showWorkInProgressInfo = true;
        let outer = this;
        setTimeout(function() {
          outer.showWorkInProgressInfo = false;
        }, 5000);
      }

    });
  }

  removePropertyClicked(currentClass: ClassDefintion, index: number) {
    currentClass.properties.splice(index, 1);
  }


  navigateBack() {
    window.history.back();
  }



  addHelloWorldToGraphTest() {
    // Gets the default parent for inserting new cells. This
    // is normally the first child of the root (ie. layer 0).
    const parent = this.graph.getDefaultParent();

    // Adds cells to the model in a single step
    this.graph.getModel().beginUpdate();
    try {

      var v1 = this.graph.insertVertex(parent, null, 'Hello,', 20, 20, 80, 30);
      var v2 = this.graph.insertVertex(parent, null, 'World!', 200, 150, 80, 30);
      var e1 = this.graph.insertEdge(parent, null, '', v1, v2);

    }
    finally {
      // Updates the display
      this.graph.getModel().endUpdate();
    }
  }

}
