import { Component, OnInit, Input, ViewChild, ElementRef, HostListener, AfterViewInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ConfiguratorService } from '../../../_service/configurator.service';
import { ClassDefintion } from '../../../_model/meta/Class';

import { fuseAnimations } from '@fuse/animations';


import { Node, Edge, Network, DataSet } from "vis";
import { Relationship, RelationshipType, Association, Inheritance, AssociationParameter } from 'app/main/content/_model/meta/Relationship';
import { detectChanges } from '@angular/core/src/render3';
import { isNullOrUndefined } from 'util';


const CANVAS_WIDTH = '100%';
const CANVAS_HEIGHT = (window.innerHeight - 120).toString();

const EDGE_SPRING_LENGTH = 100;

const SIDEBAR_CLOSED_COLOR = 'rgba(234, 254, 255, 0.0)';
const SIDEBAR_OPEN_COLOR = 'rgba(234, 254, 255, 0.4)';







@Component({
  selector: 'app-classes-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss'],
  animations: fuseAnimations

})
export class ClassesOverviewComponent implements OnInit {

  @Input() marketplace: Marketplace;
  @Input() configurableClasses: ClassDefintion[];
  @Input() relationships: Relationship[];

  isLoaded: boolean = false;
  sidebarClosed = true;
  sidebarColor = SIDEBAR_CLOSED_COLOR;
  sidebarContentType: string = 'none';
  sidebarInEditMode: boolean = false;


  sidebarArgClass: any;
  sidebarArgRelationship: any;

  currentClickEvent: any;


  public nodes: DataSet<Node>;
  public edges: DataSet<Edge>;
  public network: Network;
  public options: any;


  constructor(private router: Router,
    private route: ActivatedRoute,
    private configuratorService: ConfiguratorService) {

  }

  @ViewChild('mynetwork') el: ElementRef;


  ngOnInit() {

    console.log(this.configurableClasses);
    console.log(this.relationships);


    // var nodes = new DataSet([]);
    // var edges = new DataSet([]);

    // for (let c of this.configurableClasses) {
    //   nodes.add({id: c.id, label: c.name});
    // }

    // for (let r of this.relationships) {
    //   edges.add({id: r.id, from: r.classId1, to: r.classId2});
    // }


    var container = this.el.nativeElement;

    this.nodes = this.addNodes();
    this.edges = this.addEdges();

    var data = {
      nodes: this.nodes,
      edges: this.edges
    };

    this.options = {

      // configure: {
      //   enabled: true,
      // },
      width: CANVAS_WIDTH,
      // height: '1000px',
      height: CANVAS_HEIGHT,

      // physics: false,
      
      // physics: {
      //   barnesHut: {
      //     springLength: EDGE_SPRING_LENGTH, 
      //     springConstant: 0.5
      //   }
      // },
      
      layout: {
        hierarchical: {
          enabled: true,
          parentCentralization: true,
          direction: 'LR',
          sortMethod: 'directed'
        }
      },
      nodes: {
        shape: 'box',
        // physics: false
      },

     
      interaction: {
        zoomView: false
      },
       
      

    };
    this.network = new Network(container, data, this.options);

    this.network.on('click', (params) => {
      this.handleClickEvent(params);
    });

    this.network.on('dragStart', (params) => {

      this.handleDragEvent('dragStart', params);
    });



    this.network.on('dragEnd', (params) => {
      this.handleDragEvent('dragEnd', params);
    });

    // this.network.fit();
    this.network.focus(1, { scale: 1 });
    // this.network.once('initRedraw', function() {
    //   this.network.moveTo({offset:{x:- (0.5 * width),y:- (0.5 * height)}})
    // });


  }

  rebuildNetwork() {
    var nodes = this.addNodes();
    var edges = this.addEdges();
    // var container = this.el.nativeElement;
    var data = {
      nodes: nodes,
      edges: edges
    };

    // this.network.getViewPosition
    // this.network = new Network(container, data, this.options);
    this.network.setData(data);

  }

  addNodes(): DataSet<any> {
    var nodes = new DataSet([]);
    for (let c of this.configurableClasses) {
      nodes.add({ id: c.id, label: c.name });
    }

    return nodes;
  }

  addEdges(): DataSet<any> {
    var edges = new DataSet([]);
    for (let r of this.relationships) {


      if (r.relationshipType == RelationshipType.ASSOCIATION) {

        console.log((r as Association).param1 + "     " + (r as Association).param2);
        console.log(r);


        let label1 = AssociationParameter[(r as Association).param1];
        let label2 = AssociationParameter[(r as Association).param2];

        let edge: Edge = {
            id: r.id,
            from: r.classId1,
            to: r.classId2,
            label: label2 + this.calculateLabelSpaces() + label1,
            font: {align: 'top'}
            // smooth: {enabled: true, type: 'curvedCCW', roundness: 0.2}
          }
        
        edges.add(edge);

      } else if (r.relationshipType == RelationshipType.INHERITANCE) {

        let edge: Edge = {
          id: r.id,
          from: r.classId1,
          to: r.classId2,

        };

        if ((r as Inheritance).superClassId == r.classId1) {
          edge.arrows = {
            from: true
          };
        } else if ((r as Inheritance).superClassId == r.classId2) {
          edge.arrows = {
            to: true
          };
        }

        // edge.smooth = { enabled: true, type: 'curvedCCW', roundness: -0.2 };
        edges.add(edge);

      }





    }




    return edges;
  }


  private calculateLabelSpaces() {
    let spaces: string = ''

    let i = 0;

    while (i <= EDGE_SPRING_LENGTH) {
      spaces = spaces + ' ';
      i = i + 9;
    }

    return spaces;
  }

  openSidebar() {
    this.sidebarClosed = false;
    this.sidebarColor = SIDEBAR_OPEN_COLOR;
    // this.network.fit();
  }

  closeSidebar() {
    this.sidebarClosed = true;
    this.sidebarContentType = 'none';
    // this.network.focus(1, {scale: 1});
    this.sidebarColor = SIDEBAR_CLOSED_COLOR;
    this.sidebarInEditMode = false;
  }


  handleClickEvent(event: any) {
    console.log(event);

    console.log(this.network.getSelectedEdges());
    console.log(this.network.getSelectedNodes());

    if (this.sidebarInEditMode) {
      return;
    }

    if (event.nodes.length > 0) {
      //HANDLE NODE CLICKED EVENT

      this.handleNodeClickedEvent(event);

      console.log("NODE CLICKeD");
    }


    if (event.nodes.length <= 0 && event.edges.length > 0) {
      //HANDLE EDGE CLICKED EVENT
      this.handleEdgeClickedEvent(event);
      console.log("EDGE CLICKED");
    }

    if (event.nodes.length <= 0 && event.edges.length <= 0) {
      //HANDLE CANVAS CLICKED EVENT
      console.log("CANVAS CLICKeD");
    }
  }

  handleDragEvent(type: string, event: any) {

    console.log(event);

    if (this.sidebarInEditMode) {
      return;
    }

    if (type == 'dragStart') {

      console.log("Started dragging");

    } else if (type == 'dragEnd') {

      console.log("Stopped dragging");

    }
  }

  handleNewClicked() {
    this.sidebarContentType = 'newClass';
    this.openSidebar();
    this.sidebarInEditMode = true;
  }

  handleDeleteClicked() {
    this.sidebarContentType = 'delete';
    this.openSidebar();
    this.sidebarInEditMode = true;

    this.sidebarArgClass = this.configurableClasses;
    this.sidebarArgRelationship = this.relationships;
  }

  handleNodeClickedEvent(event: any) {
    //get Properties

    this.sidebarContentType = 'node';
    this.openSidebar();

    let n = this.configurableClasses.find((c: ClassDefintion) => {
      return c.id == event.nodes[0];
    });

    let e = this.relationships.filter((r: Relationship) => {
      return event.edges.find((e: string) => {
        return e == r.id;
      });
    });

    // console.log("bla");
    // console.log(e);

    this.sidebarArgClass = n;
    this.sidebarArgRelationship = e;



    // console.log(n);
  }

  handleEdgeClickedEvent(event: any) {
    //get Properties
    this.openSidebar();
    this.sidebarContentType = 'edge'


    console.log(event.edges[0]);
    let e = this.relationships.find((r: Relationship) => {
      return r.id == event.edges[0];
    });

    this.sidebarArgRelationship = e;

    // console.log(e);

  }

  consumeToggleEditModeEvent(setting: boolean) {
    this.sidebarInEditMode = setting;
  }




  consumeSidebarCloseEvent() {
    this.closeSidebar();
    this.rebuildNetwork();

  }

  consumeDeleteEvent(event: any) {
    // console.log(event)
    this.configurableClasses = event.configurableClasses;
    this.relationships = event.relationships;

    this.closeSidebar();
    this.rebuildNetwork();
  }

  consumeRebuildNetworkEvent(event: any) {
    if (!isNullOrUndefined(event.configurableClasses)) {
      this.configurableClasses = event.configurableClasses;
    }

    if (!isNullOrUndefined(event.relationships)) {
      this.relationships = event.relationships;
    }

    this.rebuildNetwork();
  }



  navigateBack() {
    window.history.back();
  }

}
