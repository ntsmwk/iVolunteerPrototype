import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ConfiguratorService } from '../../../_service/configurator.service';
import { ConfigurableClass } from '../../../_model/configurables/Configurable';
import { MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-classes-list',
  templateUrl: './classes-list.component.html',
  styleUrls: ['./classes-list.component.scss']
})
export class ClassesListComponent implements OnInit {

  @Input() marketplace: Marketplace; 
  @Input() configurableClasses: ConfigurableClass[];
  
  isLoaded: boolean = false;

  dataSource = new  MatTableDataSource<ConfigurableClass>();
  displayedColumns = ['id'];

  constructor(private router: Router,
    private route: ActivatedRoute,
    private configuratorService: ConfiguratorService){

    }

  ngOnInit() {
    this.dataSource.data = this.configurableClasses;
  }



  navigateBack() {
    window.history.back();
  }

}
