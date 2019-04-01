import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { LoginService } from '../_service/login.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { ParticipantRole, Participant } from '../_model/participant';
import { Property } from '../_model/properties/Property';
import { Marketplace } from '../_model/marketplace';
import { PropertyService } from '../_service/property.service';
import { UserDefinedTaskTemplate } from '../_model/user-defined-task-template';
import { UserDefinedTaskTemplateService } from '../_service/user-defined-task-template.service';
import { QuestionService } from '../_service/question.service';
import { MatDialog } from '@angular/material';
import { AddOrRemoveDialogComponent, AddOrRemoveDialogData } from '../_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.component';
import { QuestionBase } from '../_model/dynamic-forms/questions';
import { isNullOrUndefined } from 'util';
import { TextFieldDialogComponent, TextFieldDialogData } from '../_components/dialogs/text-field-dialog/text-field-dialog.component';
import { ConfirmDialogComponent } from '../_components/dialogs/confirm-dialog/confirm-dialog.component';
import { SortDialogComponent, SortDialogData } from '../_components/dialogs/sort-dialog/sort-dialog.component';

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
      this.userDefinedTaskTemplateService.getTaskTemplate(marketplace, templateId).toPromise().then((template: UserDefinedTaskTemplate) => {
        this.template = template;    
     
      })
      .then(() => {
        this.propertyService.getProperties(this.marketplace).toPromise().then((properties: Property<any>[]) => {
          this.properties = properties;
          console.log("loaded Properties: ")
          console.log(this.properties);
        
        })
        .then(() => {
          if (!isNullOrUndefined(this.template)) {
            this.questions = this.questionService.getQuestionsFromProperties(this.template.properties);
          }
          this.isLoaded = true;
        })
      });
    });  
  }


  loadProperties() {
    this.propertyService.getProperties(this.marketplace).toPromise().then((properties: Property<any>[]) => {
      this.properties = properties;
    });
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

    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '500px',
      data: {title: "Are you sure?", description: "Are you sure you want to delete this Template? This action cannot be reverted"}
    });

    dialogRef.afterClosed().subscribe((result: boolean) => {
      if (result) {
        this.userDefinedTaskTemplateService.deleteTaskTemplate(this.marketplace, this.template.id).toPromise().then( () => {
          console.log("done - navigate back");
          this.navigateBack();
        });
      } else {
        console.log("cancelled");
      }
    });
  }

  addPropertyDialog() {
    console.log("clicked add property");

    const dialogRef = this.dialog.open(AddOrRemoveDialogComponent, {
      position: {top: '50px', },
      width: '500px',
      data: this.prepareDataForAdd('Add Properties')
    });

    dialogRef.afterClosed().subscribe((result: AddOrRemoveDialogData) => {
      if (!isNullOrUndefined(result)) {
        console.log("dialog closed...result: " + result.label);
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

    dialogRef.afterClosed().subscribe((result: AddOrRemoveDialogData) => {
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

  propertyOrderDialog() {
    console.log("clicked order properties");

    const dialogRef = this.dialog.open(SortDialogComponent, {
      width: '500px',
      data: {order: this.properties, label: "Change Property Order"}
    });
    
    dialogRef.afterClosed().subscribe((result: SortDialogData) => {
      console.log("Dialog closed - displayed result");
      console.log(result);
    })
  }

  editDescriptionDialog() {
    console.log("entered edit Description Dialog");

    const dialogRef = this.dialog.open(TextFieldDialogComponent, {
      width: '500px',
      data: {label: 'Edit Description', 
             fields: [{description: 'Descripton', hintText: 'Description', value: this.template.description}]
          }
    });

    dialogRef.afterClosed().subscribe((result: TextFieldDialogData) => {
      if (!isNullOrUndefined(result)) {
        console.log("Result: " + result.fields[0].value);
        console.log(result);

        this.userDefinedTaskTemplateService.updateTaskTemplate(this.marketplace, this.template.id, null, result.fields[0].value).toPromise().then(() => {
          this.refresh();
        });

      } else {
        console.log("Cancelled");
      }
    });
  }
  
  editNameDialog() {
    console.log("Entered edit Name Dialog");
    const dialogRef = this.dialog.open(TextFieldDialogComponent, {
      width: '500px',
      data: {label: 'Edit Name', 
             fields: [{description: 'Name', hintText: 'Name', value: this.template.name}]}
    });

    dialogRef.afterClosed().subscribe((result: TextFieldDialogData) => {
      
      if (!isNullOrUndefined(result)) {
        this.userDefinedTaskTemplateService.updateTaskTemplate(this.marketplace, this.template.id, result.fields[0].value, null).toPromise().then(() => {
          this.refresh();
        });
        
      } else {
        console.log("cancelled");
      }
    });
  }

  private prepareDataForAdd(label: string): AddOrRemoveDialogData {
    console.log("entered prepareDataForAdd");
    
    let states: {property: Property<any>, disabled: boolean, checked: boolean, dirty: boolean}[] = [];

    for (let p of this.properties) {
      states.push({property: p, disabled: false, checked: false, dirty: false});
      for (let tp of this.template.properties) {
        if (p.id === tp.id) {
          states[states.length-1].disabled = true;
          states[states.length-1].checked = true;
        } 
      }
    }
    let data: AddOrRemoveDialogData = { label: label, checkboxStates: states}
    return data;
  }

  private prepareDataForRemove(label: string): AddOrRemoveDialogData {
    let states: {property: Property<any>, disabled: boolean, checked: boolean, dirty: boolean}[] = [];
    
    for (let tp of this.template.properties) {
        states.push({property: tp, disabled: false, checked: false, dirty: false});   
    } 

    let data: AddOrRemoveDialogData = { label: label, checkboxStates: states};
    return data;
  }

  private refresh() {
    this.isLoaded = false;
    this.ngOnInit();
  }
}

