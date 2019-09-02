import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../_model/marketplace';
import { ClassDefinitionService } from '../../_service/meta/core/class/class-definition.service';
import { ClassDefintion } from '../../_model/meta/Class';
import { fuseAnimations } from '@fuse/animations';
import { Relationship } from '../../_model/meta/Relationship';


@Component({
  selector: 'app-configurator-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss'],
  animations: fuseAnimations

})
export class ClassesComponent implements OnInit {

  @Input() marketplace: Marketplace; 
  @Input() configurableClasses: ClassDefintion[];
  @Input() relationships: Relationship[];
  
  isLoaded: boolean = false;


  constructor(private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService){

    }

  ngOnInit() {
  }



  navigateBack() {
    window.history.back();
  }

}
