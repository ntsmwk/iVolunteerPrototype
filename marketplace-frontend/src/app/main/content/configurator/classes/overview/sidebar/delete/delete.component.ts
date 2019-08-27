import { Component, OnInit, Input, ViewChild, ElementRef, HostListener, AfterViewInit, Output, EventEmitter } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../../../_model/marketplace';
import { ConfiguratorService } from '../../../../../_service/configurator.service';
import { ClassDefintion } from '../../../../../_model/meta/Class';

import { fuseAnimations } from '@fuse/animations';

import { Relationship } from 'app/main/content/_model/meta/Relationship';
import { Network, DataSet } from 'vis';


@Component({
  selector: 'app-sidebar-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.scss'],
  animations: fuseAnimations

})
export class SidebarDeleteComponent implements OnInit {

  @Input() marketplace: Marketplace;
  @Input() configurableClassesCurrent: ClassDefintion[];
  @Input() relationshipsCurrent: Relationship[];
  @Input() network: Network;
  @Output() submitDeleteEvent: EventEmitter<any> = new EventEmitter();



  configurableClassesToRemove: Set<ClassDefintion> = new Set();
  relationshipsToRemove: Set<Relationship> = new Set();


  constructor(private router: Router,
    private route: ActivatedRoute,
    private configuratorService: ConfiguratorService) {

  }


  ngOnInit() {

    this.network.on('click', params => {
      this.handleClickEvent(params);
    });
  }

  handleClickEvent(event: any) {

    if (event.nodes.length > 0) {
      //HANDLE NODE CLICKED EVENT

      this.handleNodeClickedEvent(event);

    }


    if (event.nodes.length <= 0 && event.edges.length > 0) {
      //HANDLE EDGE CLICKED EVENT
      this.handleEdgeClickedEvent(event);
    }

  }

  handleNodeClickedEvent(event: any) {
    //remove node
    let delClass = this.configurableClassesCurrent.find((c: ClassDefintion) => {
      return c.id == event.nodes[0];
    });


    this.configurableClassesToRemove.add(delClass);

    //remove edges

    let delRelationships = this.relationshipsCurrent.filter((r: Relationship) => {
      return event.edges.find((edge: string) => {
        return r.id == edge;
      });

    });

    for (let r of delRelationships) {
      this.relationshipsToRemove.add(r);
    }

    // console.log("DEL");
    // console.log(delClass);
    // console.log(delRelationships);

  }

  handleEdgeClickedEvent(event: any) {
    //TODO remove edge
    let delRelationship = this.relationshipsCurrent.find((r: Relationship) => {
      return r.id == event.edges[0];
    });

    this.relationshipsToRemove.add(delRelationship);
  }

  onSubmit() {
    let retRelationships: Relationship[];
    let retConfigurableClasses: ClassDefintion[];

    Promise.all([
      this.configuratorService.deleteConfigClasses(this.marketplace, 
        Array.from(this.configurableClassesToRemove).map(v => v.id)).toPromise().then((ret: ClassDefintion[]) => {
          retConfigurableClasses = ret;
      }),

      this.configuratorService.deleteRelationships(this.marketplace, 
        Array.from(this.relationshipsToRemove).map(r => r.id)).toPromise().then((ret: Relationship[]) => {
        retRelationships = ret;
      })
    ]).then(() => {
      this.submitDeleteEvent.emit({ configurableClasses: retConfigurableClasses, relationships: retRelationships });
      console.log(retRelationships);
      console.log(retConfigurableClasses);
    });


  }



  navigateBack() {
    window.history.back();
  }

}
