import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../_model/marketplace';
import { ClassDefinitionService } from '../../../_service/meta/core/class/class-definition.service';
import { ClassDefintion } from '../../../_model/meta/Class';
import { MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-classes-list',
  templateUrl: './classes-list.component.html',
  styleUrls: ['./classes-list.component.scss']
})
export class ClassesListComponent implements OnInit {

  @Input() marketplace: Marketplace; 
  @Input() configurableClasses: ClassDefintion[];
  
  isLoaded: boolean = false;

  dataSource = new  MatTableDataSource<ClassDefintion>();
  displayedColumns = ['id'];

  constructor(private router: Router,
    private route: ActivatedRoute,
    private classDefinitionService: ClassDefinitionService){

    }

  ngOnInit() {
    this.dataSource.data = this.configurableClasses;
  }



  navigateBack() {
    window.history.back();
  }

}
