import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDefinedTaskTemplateService } from '../_service/user-defined-task-template.service';
import { QuestionService } from '../_service/question.service';
import { QuestionBase } from '../_model/dynamic-forms/questions';
import { ParticipantRole, Participant } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';
import { UserDefinedTaskTemplate } from '../_model/user-defined-task-template';
import { LoginService } from '../_service/login.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { Property, PropertyKind, ListValue, ReturnProperty } from '../_model/properties/Property';
import { FormGroup } from '@angular/forms';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-user-defined-task-template-detail-form',
  templateUrl: './user-defined-task-template-detail-form.component.html',
  styleUrls: ['./user-defined-task-template-detail-form.component.scss'],
  providers:  [QuestionService]
})
export class FuseUserDefinedTaskTemplateDetailFormComponent implements OnInit {
  questions: QuestionBase<any>[];

  role: ParticipantRole;
  participant: Participant;
  marketplace: Marketplace;
  template: UserDefinedTaskTemplate;
  isLoaded: boolean;
 

  constructor(private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService,
    private questionService: QuestionService) {
      this.isLoaded = false;
    
    }

  ngOnInit() {
    console.log("call on init");
    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant)
    ]).then(() => {
      
      this.route.params.subscribe(params => this.loadProperty(params['marketplaceId'], params['templateId']));
    });
  
  }

  loadProperty(marketplaceId: string, templateId: string): void {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;
      this.userDefinedTaskTemplateService.findById(marketplace, templateId).toPromise().then((template: UserDefinedTaskTemplate) => {
        this.template = template;    
      }).then(() => {
         console.log("DETAIL PAGE FOR PROPERTY " + this.template.id);
         console.log(this.template.name + ": ");

         console.log("VALUES:");
         for (let property of this.template.properties) {
           console.log(property.id + ": " + property.value);

         }
        this.questions = this.questionService.getQuestionsFromProperties(this.template.properties);
        this.isLoaded = true;
      });
    });  
  }

  navigateBack() {
    window.history.back();
  }

  displayResult(form: FormGroup) {
    console.log("EVENT RECEIVED");
    //console.log(value);
    console.log("attempt to update properties of template");
    console.log("Values:");

   
   // console.log(form.controls.name.value);
   let props: ReturnProperty[] = [];

   for (let prop of this.template.properties) {
      if (!isNullOrUndefined(form.controls[prop.id].value)) {
        //console.log(prop.id + ": " + form.controls[prop.id].value);

        
        if (prop.kind === PropertyKind.LIST) {
          let values: ListValue<any>[] = [];
          let arr = form.controls[prop.id].value;

          for (let val of prop.legalValues) {
            for (let i = 0; i < form.controls[prop.id].value.length; i++) { 
              if (val.id === form.controls[prop.id].value[i]) {
                values.push(new ListValue<any>(val.id, val.value));
              }
            } 
          }

          console.log(values);
          prop.values = values;





        } else {
          prop.value = form.controls[prop.id].value;
        }

        props.push(prop);
      }
    }
    
    console.log(props);

    this.userDefinedTaskTemplateService.updatePropertiesInTemplate(this.marketplace, this.template.id, props).toPromise().then(() => {
      console.log("finished - returning to previous page");
      this.navigateBack();
    })
  }

}
