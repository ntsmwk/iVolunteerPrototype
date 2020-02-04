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
import { PropertyType, TemplateProperty } from '../_model/meta/Property';
import { FormGroup } from '@angular/forms';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-user-defined-task-template-detail-form-single',
  templateUrl: './user-defined-task-template-detail-form-single.component.html',
  styleUrls: ['./user-defined-task-template-detail-form-single.component.scss'],
  providers:  [QuestionService]
})
export class SingleUserDefinedTaskTemplateDetailFormComponent implements OnInit {
  questions: QuestionBase<any>[];

  role: ParticipantRole;
  participant: Participant;
  marketplace: Marketplace;
  template: UserDefinedTaskTemplate;
  isLoaded: boolean;

  templateId: string;
  subtemplateId: string;

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService,
    private questionService: QuestionService) {
      this.isLoaded = false;
    
    }

  ngOnInit() {
    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant)
    ]).then(() => {
      
      let queryParameters;
      let urlParameters;

      Promise.all([
        this.route.queryParams.subscribe(params => {
<<<<<<< HEAD
          // console.log(params);
          queryParameters = params;
        }),
        this.route.params.subscribe(params => {
          // console.log(params);
=======
          queryParameters = params;
        }),
        this.route.params.subscribe(params => {
>>>>>>> flexProd_Changes
          urlParameters = params;
        })
      ]).then(() => {
        this.loadProperty(urlParameters['marketplaceId'], urlParameters['templateId'], urlParameters['subtemplateId'], queryParameters['ref']);
      });  
    });
  
  }

  private loadProperty(marketplaceId: string, templateId: string, subtemplateId: string, ref: string): void {

<<<<<<< HEAD
    // console.log(ref);
=======
>>>>>>> flexProd_Changes
    this.templateId = templateId;
    this.subtemplateId = subtemplateId;

    if (ref == 'single') {
      this.loadFromSingleTemplate(marketplaceId, templateId);
    } else if (ref == 'nested') {
<<<<<<< HEAD
      // console.log("entered nested");
      this.loadFromNestedTemplate(marketplaceId, templateId, subtemplateId);
    } else {
      // console.log("no reference key");
      if (!isNullOrUndefined(subtemplateId)) {
        // console.log("load nested");
        this.loadFromNestedTemplate(marketplaceId, templateId, subtemplateId);
      } else {
        // console.log("load single");
=======
      this.loadFromNestedTemplate(marketplaceId, templateId, subtemplateId);
    } else {
      if (!isNullOrUndefined(subtemplateId)) {
        this.loadFromNestedTemplate(marketplaceId, templateId, subtemplateId);
      } else {
>>>>>>> flexProd_Changes
        this.loadFromSingleTemplate(marketplaceId, templateId);
      }
    }
  }

  private loadFromSingleTemplate(marketplaceId: string, templateId: string) {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;
      this.userDefinedTaskTemplateService.getTemplate(marketplace, templateId).toPromise().then((template: UserDefinedTaskTemplate) => {
        this.template = template;    
      }).then(() => {
<<<<<<< HEAD
        // console.log("DETAIL PAGE FOR PROPERTY " + this.template.id);
        // console.log(this.template.name + ": ");

        // console.log("VALUES:");
        // for (let property of this.template.templateProperties) {
        //   console.log(property.id + ": " + TemplateProperty.getDefaultValue(property));

        // }

        this.questions = this.questionService.getQuestionsFromProperties(this.template.templateProperties);
=======

        this.questions = this.questionService.getQuestionsFromProperties(this.template.properties);
>>>>>>> flexProd_Changes
        this.isLoaded = true;
      });
    }); 
  }

  private loadFromNestedTemplate(marketplaceId: string, templateId: string, subtemplateId: string) {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;
      this.userDefinedTaskTemplateService.getSubTemplate(marketplace, templateId, subtemplateId).toPromise().then((subtemplate: UserDefinedTaskTemplate) => {
        this.template = subtemplate;
      }).then(() => {
<<<<<<< HEAD
        // console.log(this.template);
        this.questions = this.questionService.getQuestionsFromProperties(this.template.templateProperties);
=======
        this.questions = this.questionService.getQuestionsFromProperties(this.template.properties);
>>>>>>> flexProd_Changes
        this.isLoaded = true;
      });
    });
  }

  navigateBack() {
    window.history.back();
  }

  consumeResultEvent(form: FormGroup) {
<<<<<<< HEAD
    // console.log("EVENT RECEIVED");
    // //console.log(value);
    // console.log("attempt to update properties of template");
    // console.log("Values:");

   
    let props: TemplateProperty<any>[] = [];
    // console.log("====================================================");
    // //console.log("form");
    // //console.log(JSON.stringify(form));
    // console.log("form.value");
    // console.log(JSON.stringify(form.value));
    
    
    props = this.traverseResultAndUpdateProperties(form.value, this.template.templateProperties);
=======
    let props: Property<any>[] = [];
    props = this.traverseResultAndUpdateProperties(form.value, this.template.properties);
>>>>>>> flexProd_Changes

    this.userDefinedTaskTemplateService.updateProperties(this.marketplace, this.templateId, this.subtemplateId, props).toPromise().then(() => {
<<<<<<< HEAD
      // console.log("finished - returning to previous page");
=======
>>>>>>> flexProd_Changes
      this.navigateBack();
    });
  }


  private traverseResultAndUpdateProperties(values: any[], templateProperties: TemplateProperty<any>[]): TemplateProperty<any>[] {
    
    for (let prop of templateProperties) {
      if (prop.type == PropertyType.MULTI) {
<<<<<<< HEAD
        //TODO DO NESTED
        // this.traverseResultAndUpdateProperties(values[prop.id], prop.properties);
      } else {
        if (!isNullOrUndefined(values[prop.id]) && values[prop.id] != '') {
          if (prop.multiple) {
            //TODO do list stuff
            // const result: any[] = [];
            // let arr = values[prop.id];

            // for (let val of prop.allowedValues) {
            //   for (let i = 0; i < arr.length; i++) { 
            //     if (val.id === arr[i]) {
            //       result.push(val);
            //     }
            //   } 
            // }

            // prop.defaultValues = result;
            prop.defaultValues = values[prop.id];
          } else {

            if (isNullOrUndefined(prop.defaultValues) || isNullOrUndefined(prop.defaultValues[0])) {
              prop.defaultValues = []
              prop.defaultValues.push(values[prop.id]);
=======
        this.traverseResultAndUpdateProperties(values[prop.id], prop.properties);

      } else {
        if (!isNullOrUndefined(values[prop.id]) && values[prop.id] != '') {
          if (prop.type === PropertyType.LIST) {
            const result: ListEntry<any>[] = [];
            let arr = values[prop.id];

            for (let val of prop.legalValues) {
              for (let i = 0; i < arr.length; i++) { 
                if (val.id === arr[i]) {
                  result.push(new ListEntry<any>(val.id, val.value));
                }
              } 
            }
            prop.values = result;

          } else {
            if (isNullOrUndefined(prop.values) || isNullOrUndefined(prop.values[0])) {
              prop.values = []
              prop.values.push(new ListEntry<any>(null, values[prop.id]));
>>>>>>> flexProd_Changes
            } else {
              prop.defaultValues[0] = values[prop.id];
            }
          }
        }
      }
    }
    return templateProperties;
  }
}
