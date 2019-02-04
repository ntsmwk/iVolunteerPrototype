import { Component, OnInit, OnDestroy, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatListModule} from '@angular/material/list'

import { TaskService } from '../_service/task.service';
import { LoginService } from '../_service/login.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { ParticipantRole, Participant } from '../_model/participant';
import { Property, PropertyKind, PropertyListItem } from '../_model/properties/Property';
import { Marketplace } from '../_model/marketplace';
import { PropertyService } from '../_service/property.service';
import { UserDefinedTaskTemplate } from '../_model/user-defined-task-template';
import { UserDefinedTaskTemplateService } from '../_service/user-defined-task-template.service';
import { QuestionService } from '../_service/question.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { AddOrRemoveDialogComponent, DialogData } from './add-or-remove-dialog/add-or-remove-dialog.component';
import { QuestionBase } from '../_model/dynamic-forms/questions';
import { isNullOrUndefined } from 'util';
//import { AddOrRemoveDialogComponent } from './add-or-remove-dialog/add-or-remove-dialog.component';




@Component({
  selector: 'user-defined-task-template-detail',
  templateUrl: './user-defined-task-template-detail.html',

  styleUrls: ['./user-defined-task-template-detail.scss'],
  providers:  [QuestionService]
})
export class FuseUserDefinedTaskTemplateDetailComponent implements OnInit {

  role: ParticipantRole;
  participant: Participant;
  marketplace: Marketplace;
  template: UserDefinedTaskTemplate;
  isLoaded: boolean;

  dialogIds: string[];

  questions: QuestionBase<any>[];
  properties: Property<any>[];
 

  constructor(public dialog: MatDialog,
    private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,

    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService,

    private questionService: QuestionService,
    private propertyService: PropertyService
    ) {
      this.isLoaded = false;
    
    }

  ngOnInit() {
    
    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant),
    ]).then(() => {
      
      this.route.params.subscribe(params => this.loadPropertiesFromTemplate(params['marketplaceId'], params['templateId']));
    })
    
    
  }


  loadPropertiesFromTemplate(marketplaceId: string, templateId: string): void {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;
      this.userDefinedTaskTemplateService.findById(marketplace, templateId).toPromise().then((template: UserDefinedTaskTemplate) => {
        this.template = template;    
      }).then(() => {
        // console.log("DETAIL PAGE FOR PROPERTY " + this.template.id);
        // console.log(this.template.name + ": ");

        // console.log("VALUES:");
        // for (let property of this.template.properties) {
        //   console.log(property.id + ": " + property.value);

        // }

        this.propertyService.findAllFromServerFull(this.marketplace).toPromise().then((properties: Property<any>[]) => {
          this.properties = properties;
          console.log("loaded Properties: ")
          console.log(this.properties);
        }).then(() => {
          if (!isNullOrUndefined(this.template)) {
            this.questions = this.questionService.getQuestionsFromProperties(this.template.properties);
  
            //Test before getting everything from server
            // for (let q of this.questions) {
            //   this.questionsInForm.push(q);
            // }
    
            // this.questionsInForm.pop();
            // this.questionsInForm.pop();
            // this.questionsInForm.pop();
          }
          this.isLoaded = true;
        })
      });
    });  
  }


  loadProperties() {
    this.propertyService.findAllFromServer(this.marketplace).toPromise().then((properties: Property<any>[]) => {
      this.properties = properties;
      console.log("loaded Properties: ")
    })
  }


  navigateBack() {
    window.history.back();
  }

  navigateEditForm() {
    console.log("navigate to edit form");
    this.router.navigate(["/main/task-templates/user/detail/edit/" + this.marketplace.id + "/" + this.template.id]);
  }

  deleteTemplate() {
    console.log("clicked delete template");
    this.userDefinedTaskTemplateService.deleteTemplate(this.marketplace, this.template.id).toPromise().then( () => {
      console.log("done - navigate back");
      this.navigateBack();
    });

  }

  addPropertyDialog() {
    console.log("clicked add property");

    const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
      position: {top: '50px', },
       width: '500px',
      
      //data: {questions: this.questions, disabledQuestions: this.questions, label: 'Add Properties'}
      


      data: this.prepareDataForAdd('Add Properties')
    });

    dialogRef.afterClosed().subscribe((result: DialogData) => {
      if (!isNullOrUndefined(result)) {
        console.log("dialog closed...");
        console.log("result: " + result.label);
        console.log(result.checkboxStates);
        console.log("======");

        let propIds: string[] = [];

        for (let s of result.checkboxStates) {
          if (s.dirty) {
            console.log(s.property.name + " has changed and marked to be added" + s.dirty);
            propIds.push(s.property.id);
            
          } 
        }

        this.userDefinedTaskTemplateService.addPropertiesToTemplate(this.marketplace, this.template.id, propIds).toPromise().then(() => {
          this.refresh();
        });

      } else {
        console.log("Cancelled");
      }

    });
  }
  
  removePropertyDialog() {
    console.log("clicked remove properies");
    const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
      width: '500px',
      data: this.prepareDataForRemove('Remove Properties')
    });

    dialogRef.afterClosed().subscribe((result: DialogData) => {
      if (!isNullOrUndefined(result)) {
        console.log("dialog closed..." + result.label);
        console.log(result.checkboxStates);
        console.log("======");
        
        let propIds: string[] = [];
        for (let s of result.checkboxStates) {
          if (s.dirty) {
            console.log(s.property.name + " has changed and marked to be removed" + s.dirty);
            propIds.push(s.property.id);
          }
        }
        this.userDefinedTaskTemplateService.removePropertiesFromTemplate(this.marketplace, this.template.id, propIds).toPromise().then(() => {
          this.refresh();
        });

      } else {
        console.log("Cancelled");
      }

    });
  }

  private prepareDataForAdd(label: string): DialogData {
    let states: {property: Property<any>, disabled: boolean, checked: boolean, dirty: boolean}[] = [];
   
    console.log("entered prepareDataForAdd");
    
    for (let p of this.properties) {
      states.push({property: p, disabled: false, checked: false, dirty: false});
      for (let tp of this.template.properties) {
        if (p.id === tp.id) {
          states[states.length-1].disabled = true;
          states[states.length-1].checked = true;
        } 
      }
      
      
    }
    // console.log("STATES");
    // for (let s of states) {
    //   console.log(s.question.label + " " + s.disabled);
    // }

    let data: DialogData = { label: label, checkboxStates: states}
    return data;
  }

  private prepareDataForRemove(label: string): DialogData {
    let states: {property: Property<any>, disabled: boolean, checked: boolean, dirty: boolean}[] = [];
    
    for (let tp of this.template.properties) {
        states.push({property: tp, disabled: false, checked: false, dirty: false});   
    }
    

    let data: DialogData = { label: label, checkboxStates: states};
    return data;
  }

  private refresh() {
    this.isLoaded = false;
    this.ngOnInit();
  }


  
  

  

}

