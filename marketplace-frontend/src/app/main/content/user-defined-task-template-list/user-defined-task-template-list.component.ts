import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { UserDefinedTaskTemplateService } from "../_service/user-defined-task-template.service";

import { MatTableDataSource, MatDialog } from '@angular/material';
import { Marketplace } from '../_model/marketplace';
import { UserDefinedTaskTemplate, UserDefinedTaskTemplateStub } from "../_model/user-defined-task-template";
import { Participant } from '../_model/participant';
import { isNullOrUndefined } from 'util';
import { fuseAnimations } from '@fuse/animations';
import { TextFieldDialogComponent, TextFieldDialogData } from '../_components/dialogs/text-field-dialog/text-field-dialog.component';

@Component({
  templateUrl: './user-defined-task-template-list.component.html',
  styleUrls: ['./user-defined-task-template-list.component.scss'],
  animations: fuseAnimations
})
export class FuseUserDefinedTaskTemplateListComponent implements OnInit {

  dataSource = new MatTableDataSource<UserDefinedTaskTemplateStub>();
  displayedColumns = ['id', 'name', 'description'];
  marketplace: Marketplace;
  isLoaded: boolean =  false;

  constructor(private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService,
    public dialog: MatDialog) 
    { }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.userDefinedTaskTemplateService.getAllTaskTemplates(this.marketplace, true).toPromise().then((templates: UserDefinedTaskTemplateStub[]) => {

            this.dataSource.data = templates;
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

    const dialogRef = this.dialog.open(TextFieldDialogComponent, {
      width: '500px',
      data: {label: 'New Template', 
             fields: [{description: 'Name', hintText: 'Name', value: null},
                      {description: 'Description', hintText: 'Description', value: null}]
          }
    });

    dialogRef.afterClosed().subscribe((result: TextFieldDialogData) => {
      if (!isNullOrUndefined(result)) {
        
        console.log(result.fields.length) //must be 2
        console.log(result.fields);


        let name = result.fields[0].value;
        let description = result.fields[1].value; //why??
      

        this.userDefinedTaskTemplateService.newTaskTemplate(this.marketplace, name, description).toPromise().then((t: UserDefinedTaskTemplate) => {
          this.router.navigate(['/main/task-templates/user/detail/' + this.marketplace.id + '/' + t.id]);
        });

      } else {
        console.log("Cancelled");
      }
    });
  }
}
