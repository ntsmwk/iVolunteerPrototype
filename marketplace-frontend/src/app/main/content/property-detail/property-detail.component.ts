import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatListModule} from '@angular/material/list'

import { TaskService } from '../_service/task.service';
import { LoginService } from '../_service/login.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { ParticipantRole, Participant } from '../_model/participant';
import { PropertyDefinition, PropertyItem, PropertyParentSubTemplate, PropertyParentTemplate } from '../_model/meta/Property';
import { Marketplace } from '../_model/marketplace';
import { isNullOrUndefined } from 'util';
import { async } from '@angular/core/testing';
import { UserDefinedTaskTemplateService } from '../_service/user-defined-task-template.service';
import { PropertyDefinitionService } from '../_service/meta/core/property/property-definition.service';
import { Helpseeker } from '../_model/helpseeker';

@Component({
  selector: 'app-property-detail',
  templateUrl: './property-detail.component.html',
  styleUrls: ['./property-detail.component.scss']
})
export class PropertyDetailComponent implements OnInit {

  role: ParticipantRole;
  helpseeker: Helpseeker;
  marketplace: Marketplace;
  propertyDefintion: PropertyDefinition<any>;

  templateItem: PropertyParentTemplate;
  subtemplateItem: PropertyParentSubTemplate;

  isLoaded: boolean;
  columnsToDisplay = ['value'];

  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private propertyDefinitionService: PropertyDefinitionService,
    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService) {
      this.isLoaded = false;
      }

  ngOnInit() {
    console.log("Navigated Property Detail Page");
    
    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => this.helpseeker = helpseeker)
    ]).then(() => {
      
      let parameters;
      let queryParameters;

      Promise.all([
        this.route.params.subscribe(params => {
          console.log(params);
          parameters = params;
        }),
        this.route.queryParams.subscribe(params => {
          console.log(params);
          queryParameters = params;
        })  
      ]).then(() => {

        console.log(parameters);
        this.loadProperty(parameters['marketplaceId'], parameters['templateId'], 
          parameters['subtemplateId'], parameters['propertyId'], queryParameters['ref']);
      });
    });
  }


  loadProperty(marketplaceId: string, templateId: string, subtemplateId: string, propId: string, ref: string): void {
    console.log("MP: " + marketplaceId + " TEMPLID: " + templateId + " SUBTEMPLID: " + subtemplateId + " PROPID " + propId + " REF" + ref); 


    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;

      if (ref == 'list') {
        this.propertyDefinitionService.getPropertyDefinitionById(marketplace, propId, this.helpseeker.tenantId).toPromise().then((propertyDefintion: PropertyDefinition<any>) => {
          this.propertyDefintion = propertyDefintion;    
        }).then(() => {
          console.log(this.propertyDefintion);
          this.isLoaded = true;
        });
       
      } else if (ref == 'template') {

      } else if (ref == 'subtemplate') {
        this.userDefinedTaskTemplateService.getPropertyFromSubTemplate(this.marketplace, templateId, subtemplateId, propId).toPromise().then((propertyDefintion: PropertyDefinition<any>) => {
          this.propertyDefintion = propertyDefintion;

          // this.propertyDefinitionService.getPropertyParentItems(this.marketplace, propertyDefintion.id, templateId, subtemplateId).toPromise().then((parents: PropertyItem[]) => {
          //   console.log("Recheived PropertyParentItem");
          //   console.log(parents);
          //   this.templateItem = parents[0];

          //   if (parents.length >= 2) {
          //     this.subtemplateItem = parents[0];
          //   }
          //   this.isLoaded = true;
          // });

        });

      }
    }); 
  }

  // displayPropertyValue(property: PropertyDefintion<any>): string {    
  //   return PropertyDefinition.getValue(property);
  // }

  // displayPropertyDefaultValue(property: Property<any>): string {
  //   return Property.getDefaultValue(property);
  // }

  navigateBack() {
    window.history.back();
  }
}
