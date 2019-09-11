import { Component, OnInit, Input, AfterViewInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
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
import { MatDialog } from '@angular/material';
import { OpenDialogComponent, OpenDialogData } from './open-dialog/open-dialog.component';



const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: 'mxgraph',
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
export class ClassesEditorComponent implements OnInit, AfterViewInit, OnDestroy {

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
    private dialogFactory: DialogFactoryComponent,
    public dialog: MatDialog,
   ) {

  }


  sidebarPalettes = [
    {
      id: 'building_blocks', label: 'Building Blocks',
      items: [
        { id: 'class', label: 'Class', imgPath: '/assets/mxgraph_resources/images/custom/class.svg', type: 'vertex', shape: undefined },
      ]
    },
    {
      id: 'relationships', label: 'Relationships',
      items: [
        { id: 'inheritance', label: 'Inheritance', imgPath: '/assets/mxgraph_resources/images/custom/inheritance.svg', type: 'edge', shape: undefined },
        // { id: 'association', label: 'Association', imgPath: '/assets/mxgraph_resources/images/custom/association.svg', type: 'relation', shape: undefined },
      ]
    },
    {
      id: 'operators', label: 'Operators',
      items: [

      ]
    }
  ];

  @ViewChild('graphContainer') graphContainer: ElementRef;
  @ViewChild('leftSidebarContainer') leftSidebarContainer: ElementRef;
  @ViewChild('rightSidebarContainer') rightSidebarContainer: ElementRef;

  @ViewChild('menubarContainer') menubarContainer: ElementRef;
  @ViewChild('fileMenuItemContainer') fileMenuItemContainer: ElementRef;

  @ViewChild('openFileDialog') openFileDialog: ElementRef;


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

    // console.log(this.configurableClasses);
    // console.log(this.relationships);

    this.propertyService.getProperties(this.marketplace).toPromise().then((properties: Property<any>[]) => {
      this.properties = properties;
    
    });

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

    this.menubarContainer.nativeElement.style.position = 'absolute';
    this.menubarContainer.nativeElement.style.overflow = 'hidden';
    this.menubarContainer.nativeElement.style.padding = '2px';
    this.menubarContainer.nativeElement.style.right = '0px';
    this.menubarContainer.nativeElement.style.top = '0px';
    this.menubarContainer.nativeElement.style.left = '0px';
    this.menubarContainer.nativeElement.style.height = '25px';
    this.menubarContainer.nativeElement.style.background = 'white';
    this.menubarContainer.nativeElement.style.font = 'Arial, Helvetica, sans-serif';

    this.fileMenuItemContainer.nativeElement.style.position = 'absolute';
    this.fileMenuItemContainer.nativeElement.style.overflow = 'hidden';
    this.fileMenuItemContainer.nativeElement.style.padding = '2px';
    this.fileMenuItemContainer.nativeElement.style.top = '25px';
    this.fileMenuItemContainer.nativeElement.style.left = '10px';
    this.fileMenuItemContainer.nativeElement.style.height = 'auto';
    this.fileMenuItemContainer.nativeElement.style.width = '200px'
    this.fileMenuItemContainer.nativeElement.style.background = 'white';
    this.fileMenuItemContainer.nativeElement.style.font = 'Arial, Helvetica, sans-serif';
    this.fileMenuItemContainer.nativeElement.style.display = 'none';


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

      document.addEventListener("click", function (event) {
        outer.clickEvent(event);
      });

      this.graph.addListener(mx.mxEvent.CLICK, function(sender, evt) {
        console.log("clicked cell");
        var cell: mxgraph.mxCell = evt.getProperty("cell");
        console.log(cell);


        if (!isNullOrUndefined(cell)) {

          var parent = cell.getParent();
          console.log("parent cell");

          console.log(parent);

          if (cell.value == "add") {
            // let classDefintion: ClassDefintion;
            for (let c of outer.configurableClasses) {
              if (c.id == parent.id) {
                outer.dialogFactory.addPropertyDialogGeneric(outer.properties, c.properties).then((props: Property<any>[]) => {
                  console.log("returned");
                  console.log(props);
                  outer.classDefinitionService.savePropertiesLegacy(outer.marketplace, c.id, props).toPromise().then((ret) => {
                    c.properties.push(...props);
                    outer.showServerContent();
                  })
                });
                break;
              }
            }
            
          }
        }

      });




      // this.addHelloWorldToGraphTest(); //Remove

      // this.showEmptyEditor();
      // this.showPresshaerteOfen();
      this.parseServerContent();
      this.setLayout('vertical');
      // console.log(this.graph.getModel());
      // const encoder = new mx.mxCodec('');
      // var node = encoder.encode(this.graph.getModel());
      // console.log(mx.mxUtils.getPrettyXml(node));
    }




  }

  //Temporary

  showOpenDialog() {
    let outer = this;
    const dialogRef = this.dialog.open(OpenDialogComponent, {
      width: '500px',
      minWidth: '500px',
      height: '400px',
      minHeight: '400px',
      data: { ret: undefined },
      disableClose: true

    });

    let ret: { ret: string, } = undefined;
    dialogRef.beforeClose().toPromise().then((result: OpenDialogData) => {
      console.log("result = ");
      ret = { ret: result.ret };
      console.log(ret);
    });

    dialogRef.afterClosed().toPromise().then(() => {
      if (ret.ret == 'Haubenofen') {
        outer.showServerContent();
      } else if (ret.ret == 'Presshaerteofen') {
        outer.showPresshaerteOfen();
      }
    });
  }




  showServerContent() {
    this.showEmptyEditor();
    this.parseServerContent();
    this.setLayout('vertical');
  }

  openFileMenuItem(event: any) {
    this.fileMenuItemContainer.nativeElement.style.display = 'block';
  }

  showPresshaerteOfen() {
    this.showEmptyEditor();

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
    const parent = this.graph.getDefaultParent();
    try {
      let root = this.graph.insertVertex(parent, "root", "/", 0, 0, 80, 30);
      let tech = this.graph.insertVertex(parent, "tech", "Technische\nBeschreibung", 0, 0, 80, 30);
      let log = this.graph.insertVertex(parent, "log", "Logistische\nBeschreibung", 0, 0, 80, 30, "shape=swimlane");
      let preis = this.graph.insertVertex(parent, "preis", "Preisliche\nBeschreibung", 0, 0, 80, 30, "shape=swimlane");

      this.graph.insertEdge(parent, "e1", null, root, tech);
      this.graph.insertEdge(parent, "e2", null, root, log);
      this.graph.insertEdge(parent, "e3", null, root, preis);


      preis.geometry.alternateBounds = new mx.mxRectangle(0, 0, 85, 30);
      log.geometry.alternateBounds = new mx.mxRectangle(0, 0, 110, 200);
      tech.geometry.alternateBounds = new mx.mxRectangle(0, 0, 110, 200);


      var v11 = this.graph.insertVertex(preis, "test1", 'Property 1', 5, 45, 100, 20, "movable=0;resizable=0;editable=0;deletable=0;selectable=0;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left");
      var v11i = this.graph.insertVertex(preis, "test1icon", '(I)', 80, 45, 20, 20, "shape=image;image=/assets/mxgraph_resources/images/diamond_start.gif;noLabel=1;imageBackground=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;");
      var vfiller = this.graph.insertVertex(preis, "vfiller", null, 105, 45, 5, 130, "fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;")

      var v12 = this.graph.insertVertex(preis, "test2", 'Property 2', 5, 65, 100, 20, "movable=0;resizable=0;editable=0;deletable=0;selectable=0;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left");
      var v13 = this.graph.insertVertex(preis, "test3", 'Property 3', 5, 85, 100, 20, "movable=0;resizable=0;editable=0;deletable=0;selectable=0;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left");
      var v14 = this.graph.insertVertex(preis, "test4", 'Property 4', 5, 105, 100, 20, "movable=0;resizable=0;editable=0;deletable=0;selectable=0;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left");
      var v15 = this.graph.insertVertex(preis, "test5", 'Property 5', 5, 125, 100, 20, "movable=0;resizable=0;editable=0;deletable=0;selectable=0;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left");

      var v16 = this.graph.insertVertex(preis, "add", 'add', 10, 150, 40, 15, "movable=0;resizable=0;editable=0;deletable=0;selectable=0;fillColor=rgb(62,125,219);fontColor=white;strokeColor=none");
      


      // var v11 = this.graph.insertVertex(preis, null, 'Hello,', 10, 40, 120, 80);
      this.graph.insertVertex(log, "add", "add property", 0, 40, 80, 15);
      tech.setCollapsed(true);
      log.setCollapsed(true);

      let test = this.graph.insertVertex(parent, "test", "Test_after", 0, 0, 80, 30);
      this.graph.insertEdge(parent, "e11", null, preis, test);


      // this.graph.collapseToPreferredSize=false;
    } finally {
      this.graph.getModel().endUpdate();
    }



    this.setLayout('vertical');
  }

  showEmptyEditor() {
    this.graph.getModel().beginUpdate();
    try {
      this.graph.getModel().clear();
    } finally {
      this.graph.getModel().endUpdate();
    }
  }


  private parseServerContent() {
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


    const parent = this.graph.getDefaultParent();


    this.graph.getModel().beginUpdate();
    try {
      for (let c of this.configurableClasses) {

        let cell = this.graph.insertVertex(parent, c.id, c.name, 0, 0, 80, 30, "shape=swimlane");

        if (!isNullOrUndefined(c.properties) && c.properties.length <= 0) {
          cell.setCollapsed(true);
        }

        if (cell.id == "technische_beschreibung") {
          var overlay = new mx.mxCellOverlay(new mx.mxImage("/images/gear.png", 30, 30), "Overlay", mx.mxConstants.ALIGN_LEFT, mx.mxConstants.ALIGN_TOP);
          this.graph.addCellOverlay(cell, overlay);
        }

        if (cell.id == "logistischeBeschreibung") {
          var overlay = new mx.mxCellOverlay(new mx.mxImage("/images/package.png", 30, 30), "Overlay", mx.mxConstants.ALIGN_LEFT, mx.mxConstants.ALIGN_TOP);
          this.graph.addCellOverlay(cell, overlay);
        }

        if (cell.id == "preislicheBeschreibung") {
          var overlay = new mx.mxCellOverlay(new mx.mxImage("/images/printer.png", 30, 30), "Overlay", mx.mxConstants.ALIGN_LEFT, mx.mxConstants.ALIGN_TOP);
          this.graph.addCellOverlay(cell, overlay);
        }

        let i = 20;

        var vfiller = this.graph.insertVertex(cell, "vfiller", null, 105, 45, 5, 130, "fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;")
  

        for (let p of c.properties) {
          cell.geometry.alternateBounds = new mx.mxRectangle(0, 0, 85, 30);
          var v11 = this.graph.insertVertex(cell, p.id, p.name, 5, i + 45, 100, 20, "movable=0;resizable=0;editable=0;deletable=0;selectable=0;fillColor=rgb(186,255,171);fontColor=rgb(54,115,41);strokeColor=rgb(54,115,41);align=left;html=1;overflow=hidden");
          var v11i = this.graph.insertVertex(cell, "test1icon", '(I)', 80, i + 45, 20, 20, "shape=image;image=/assets/mxgraph_resources/images/diamond_start.gif;noLabel=1;imageBackground=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;");

          i = i + 20;
        }

        var add = this.graph.insertVertex(cell, "add", 'add', 5, i + 50, 20, 20, "shape=image;image=/assets/mxgraph_resources/images/add_green.png;noLabel=1;imageBackground=none;imageBorder=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;");
        var hfiller = this.graph.insertVertex(cell, "hfiller", null, 0, i+50+20, 85, 5, "fillColor=none;strokeColor=none;movable=0;resizable=0;editable=0;deletable=0;selectable=0;")
       
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

        var source = this.graph.getModel().getCell(r.classId1);
        var target = this.graph.getModel().getCell(r.classId2);

        if (r.relationshipType == RelationshipType.INHERITANCE) {
          mx.mxConstants.STYLE_EDGE
          // var cell = new myMxCell(undefined, new mx.mxGeometry(0, 0, 0, 0), 'sideToSideEdgeStyle=1;startArrow=classic;endArrow=none');
          var cell = new myMxCell(undefined, new mx.mxGeometry(0, 0, 0, 0), 'sideToSideEdgeStyle=1;startArrow=none;endArrow=none');

          cell.geometry.setTerminalPoint(new mx.mxPoint(), true);
          cell.geometry.setTerminalPoint(new mx.mxPoint(), false);

          cell.geometry.relative = true;
          cell.edge = true;
          cell.cellType = 'inheritance'
          cell.id = r.id;
          this.graph.addEdge(cell, parent, source, target);


        } else if (r.relationshipType == RelationshipType.ASSOCIATION) {
          var edge = new myMxCell('', new mx.mxGeometry(0, 0, 0, 0), 'endArrow=none;html=1;curved=1');
          edge.geometry.setTerminalPoint(new mx.mxPoint(), true);
          edge.geometry.setTerminalPoint(new mx.mxPoint(), false);
          edge.geometry.relative = true;
          edge.edge = true;
          edge.cellType = 'association';
          edge.id = r.id;


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

  private setLayout(layoutType: string) {

    if (layoutType == 'radial') {
      let layout = new mx.mxRadialTreeLayout(this.graph);
      layout.execute(this.graph.getDefaultParent(), this.graph.getModel().getCell('root'));

    } else if (layoutType == 'vertical') {
      let layout = new mx.mxHierarchicalLayout(this.graph, mx.mxConstants.DIRECTION_NORTH);
      // layout.execute(this.graph.getDefaultParent(), [this.graph.getModel().getCell('technische_beschreibung'),
      //                                                this.graph.getModel().getCell('logistischeBeschreibung'),
      //                                                this.graph.getModel().getCell('preislicheBeschreibung')
      //                                               ]);
      layout.execute(this.graph.getDefaultParent(), [this.graph.getModel().getCell('root')]);

    } else if (layoutType == 'horizontal') {
      let layout = new mx.mxHierarchicalLayout(this.graph, mx.mxConstants.DIRECTION_WEST);
      // layout.execute(this.graph.getDefaultParent(), [this.graph.getModel().getCell('technische_beschreibung'),
      //                                                 this.graph.getModel().getCell('logistischeBeschreibung'),
      //                                                 this.graph.getModel().getCell('preislicheBeschreibung')
      //                                               ]);
      layout.execute(this.graph.getDefaultParent(), [this.graph.getModel().getCell('root')]);

    }

    this.resetViewport();

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




          var createClassInstanceItem = menu.addItem('Create new Instance', null, function () {
            outer.createClassInstanceClicked(cell);
          }, null, null, true, true);

          var addIconItem = menu.addItem('Add Icon', null, function () {
            console.log("add Icon");
          }, null, null, true, true);

          menu.addSeparator(null, true);


        }
      }

      // Options present in every cell (vertexes as well as edges)
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
          // let cell = new myMxCell(undefined, new mx.mxGeometry(0, 0, 0, 0), 'curved=1;startArrow=classic;endArrow=none;html=1;');
          var cell = new myMxCell(undefined, new mx.mxGeometry(0, 0, 0, 0), 'sideToSideEdgeStyle=1;startArrow=none;endArrow=none');
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

      // for (let c of outer.graph.getModel().getChildren(outer.graph.getDefaultParent())) {
      //   if (isNullOrUndefined(ret)) {
      //     ret = c;
      //   } else if (!isNullOrUndefined(c.geometry) && c.geometry.x < ret.geometry.x) {
      //     ret = c;
      //   }
      // }
      // return ret;

      return outer.graph.getModel().getCell('technische_beschreibung');


    })(), false);
    const translate = this.graph.view.getTranslate()
    this.graph.view.setTranslate(translate.x + 10, translate.y + 10);
  }




  doShitWithGraph(graph: mxgraph.mxGraph) {

    console.log(graph);

  }

  saveGraphClicked() {
    console.log(this.graph.getModel().cells);
    console.log(this.graph.getModel());


  }

  createClassInstanceClicked(cell: mxgraph.mxCell) {
    console.log("create class instance clicked");
    console.log(cell);

    // if (isNullOrUndefined(cell)) {
    //TODO find root - workaroud assign root manually for demonstration
    cell = this.graph.getModel().getCell('root');
    // }

    if (cell.id.startsWith('new')) {
      console.log("you have to save first");

      //TODO
      this.showWorkInProgressInfo = true;
      let outer = this;
      setTimeout(function () {
        outer.showWorkInProgressInfo = false;
      }, 5000);
    } else {
      this.router.navigate([`main/configurator/instance-editor/${this.marketplace.id}/${cell.id}`]);
    }
  }





  clickEvent(event: any) {
    if (event.srcElement.className != "menuitem") {
      this.fileMenuItemContainer.nativeElement.style.display = 'none';
    }
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
        setTimeout(function () {
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

  ngOnDestroy() {
    //removeEventListeners
  }

}
