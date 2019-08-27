import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ConfiguratorService } from '../../../_service/configurator.service';
import { ClassDefintion } from '../../../_model/meta/Class';


@Component({
  selector: 'app-classes-configurator',
  templateUrl: './classes-configurator.component.html',
  styleUrls: ['./classes-configurator.component.scss']
})
export class ClassesConfiguratorComponent implements OnInit {

  @Input() marketplace: Marketplace; 
  @Input() configurableClasses: ClassDefintion[];
  
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
