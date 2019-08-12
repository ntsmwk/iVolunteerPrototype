import { Component, OnInit, Input, ViewChild, ElementRef, HostListener, AfterViewInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../../../_model/marketplace';
import { ConfiguratorService } from '../../../../../_service/configurator.service';
import { ConfigurableClass } from '../../../../../_model/configurables/Configurable';

import { fuseAnimations } from '@fuse/animations';

import { Relationship } from 'app/main/content/_model/configurables/Relationship';


@Component({
  selector: 'app-sidebar-edge',
  templateUrl: './edge.component.html',
  styleUrls: ['./edge.component.scss'],
  animations: fuseAnimations

})
export class SidebarEdgeComponent implements OnInit {

  @Input() marketplace: Marketplace; 
  @Input() relationship: Relationship;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private configuratorService: ConfiguratorService){

    }


  ngOnInit() {

    console.log(this.relationship);
  }



  navigateBack() {
    window.history.back();
  }

}
