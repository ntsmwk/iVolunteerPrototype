import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../_model/marketplace';
import { ConfiguratorService } from '../../_service/configurator.service';
import { ConfigurableClass } from '../../_model/configurables/Configurable';
import { MatTableDataSource } from '@angular/material';
import { fuseAnimations } from '@fuse/animations';


@Component({
  selector: 'app-configurator-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss'],
  animations: fuseAnimations

})
export class ClassesComponent implements OnInit {

  @Input() marketplace: Marketplace; 
  @Input() configurableClasses: ConfigurableClass[];
  
  isLoaded: boolean = false;


  constructor(private router: Router,
    private route: ActivatedRoute,
    private configuratorService: ConfiguratorService){

    }

  ngOnInit() {
  }



  navigateBack() {
    window.history.back();
  }

}
