import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { UserDefinedTaskTemplateService } from "../_service/user-defined-task-template.service";

import { MatTableDataSource } from '@angular/material';
import { Marketplace } from '../_model/marketplace';
import { UserDefinedTaskTemplate } from "../_model/user-defined-task-template";
import { Participant } from '../_model/participant';
import { isNullOrUndefined } from 'util';
import { Property } from '../_model/properties/Property';
import { fuseAnimations } from '@fuse/animations';

@Component({
  templateUrl: './user-defined-task-template-list.component.html',
  styleUrls: ['./user-defined-task-template-list.component.scss'],
  animations: fuseAnimations
})
export class FuseUserDefinedTaskTemplateListComponent implements OnInit {

  dataSource = new MatTableDataSource<UserDefinedTaskTemplate>();
  displayedColumns = ['id', 'name'];
  displayedColumnsProperties = ['id', 'name', 'value', 'kind'];

  marketplace: Marketplace;
  isLoaded: boolean =  false;


  constructor(private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService) {
    
    
    }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.userDefinedTaskTemplateService.getTaskTemplates(this.marketplace).toPromise().then((templates: UserDefinedTaskTemplate[]) => {
            
            
            this.dataSource.data = templates;
          
            //TODO remove
            if (templates.length > 0) {
              console.log(templates[0].id + " " + templates[0].name) 
              console.log("PROPERTIES");
              console.log(templates[0].properties[0].name);
              console.log(templates[0].properties[1].name);
            }

            this.isLoaded = true;
            
          });
        }
      });
    });

    

  }

  onRowSelect(t: UserDefinedTaskTemplate) {
    console.log(t.id + ": " + t.name + " row selected!");
    console.log("navigate to TaskTemplate detail page");
    this.router.navigate(['/main/task-templates/user/detail/' + this.marketplace.id + '/' + t.id]);
  }

  newTaskTemplate() {
    console.log("clicked new TaskTemplate!");
    console.log("navigate to new TaskTemplate from");

    

    this.userDefinedTaskTemplateService.newEmptyTemplate(this.marketplace, "my Template").toPromise().then((t: UserDefinedTaskTemplate) => {
      this.router.navigate(['/main/task-templates/user/detail/' + this.marketplace.id + '/' + t.id]);
    });
  }

}
