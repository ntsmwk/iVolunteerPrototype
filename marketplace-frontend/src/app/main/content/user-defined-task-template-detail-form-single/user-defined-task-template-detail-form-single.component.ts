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
import { Property, PropertyKind, ListEntry } from '../_model/properties/Property';
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

 

  constructor(private router: Router,
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
          queryParameters = params;
        }),
        this.route.params.subscribe(params => {
          urlParameters = params;
        })
      ]).then(() => {
        this.loadProperty(urlParameters['marketplaceId'], urlParameters['templateId'], urlParameters['subtemplateId'], queryParameters['ref']);
      });
    });
  
  }

  private loadProperty(marketplaceId: string, templateId: string, subtemplateId: string, ref: string): void {

    this.templateId = templateId;
    this.subtemplateId = subtemplateId;

    if (ref == 'single') {
      this.loadFromSingleTemplate(marketplaceId, templateId);
    } else if (ref == 'nested') {
      this.loadFromNestedTemplate(marketplaceId, templateId, subtemplateId);
    } else {
      if (!isNullOrUndefined(subtemplateId)) {
        this.loadFromNestedTemplate(marketplaceId, templateId, subtemplateId);
      } else {
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
        this.questions = this.questionService.getQuestionsFromProperties(this.template.properties);
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
        this.questions = this.questionService.getQuestionsFromProperties(this.template.properties);
        this.isLoaded = true;
      });
    });
  }

  navigateBack() {
    window.history.back();
  }

  consumeResultEvent(form: FormGroup) {
    let props: Property<any>[] = [];
    props = this.traverseResultAndUpdateProperties(form.value, this.template.properties);

    this.userDefinedTaskTemplateService.updateProperties(this.marketplace, this.templateId, this.subtemplateId, props).toPromise().then(() => {
      this.navigateBack();
    });

  }


  private traverseResultAndUpdateProperties(values: any[], properties: Property<any>[]): Property<any>[] {
    
    for (let prop of properties) {
      if (prop.kind == PropertyKind.MULTI) {
        this.traverseResultAndUpdateProperties(values[prop.id], prop.properties);
      } else {
        if (!isNullOrUndefined(values[prop.id]) && values[prop.id] != '') {
          if (prop.kind === PropertyKind.LIST) {
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
            } else {
              prop.values[0].value = values[prop.id];
            }
          }
        }
      }
    }
    return properties;
  }
}