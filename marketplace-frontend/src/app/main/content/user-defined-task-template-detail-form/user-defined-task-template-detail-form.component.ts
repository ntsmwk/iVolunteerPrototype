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
import { Property, PropertyKind, ListValue } from '../_model/properties/Property';
import { FormGroup } from '@angular/forms';
import { isNullOrUndefined, isNull } from 'util';

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
      this.userDefinedTaskTemplateService.getTaskTemplate(marketplace, templateId).toPromise().then((template: UserDefinedTaskTemplate) => {
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

  consumeResultEvent(form: FormGroup) {
    console.log("EVENT RECEIVED");
    //console.log(value);
    console.log("attempt to update properties of template");
    console.log("Values:");

   
    let props: Property<any>[] = [];
    console.log("====================================================");
    //console.log("form");
    //console.log(JSON.stringify(form));
    console.log("form.value");
    console.log(JSON.stringify(form.value));
    
    
    props = this.traverseResultAndUpdateProperties(form.value, this.template.properties);

   
    
    console.log("====================================================");
    console.log("Properties:");
    console.log(props);
    console.log("Form Values:");
    console.log(form.value);
    console.log("====================================================");


    this.userDefinedTaskTemplateService.updatePropertiesInTemplate(this.marketplace, this.template.id, props).toPromise().then(() => {
      console.log("finished - returning to previous page");
      this.navigateBack();
    })
  }


  private traverseResultAndUpdateProperties(values: any[], properties: Property<any>[]): Property<any>[] {
    
    for (let prop of properties) {
      if (prop.kind == PropertyKind.MULTIPLE) {
        //TODO DO NESTED
        this.traverseResultAndUpdateProperties(values[prop.id], prop.properties);
      } else {
        if (!isNullOrUndefined(values[prop.id])) {
          if (prop.kind === PropertyKind.LIST) {
            //TODO do list stuff
            const result: ListValue<any>[] = [];
            let arr = values[prop.id];

            for (let val of prop.legalValues) {
              for (let i = 0; i < arr.length; i++) { 
                if (val.id === arr[i]) {
                  result.push(new ListValue<any>(val.id, val.value));
                }
              } 
            }

            prop.values = result;
          } else {
            prop.value = values[prop.id];
          }
        }
      }
    }
    return properties;
  }
}
