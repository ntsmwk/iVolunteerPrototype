import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { LoginService } from '../_service/login.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { ParticipantRole, Participant } from '../_model/participant';
import { PropertyDefinition, PropertyItem, PropertyParentSubTemplate, PropertyParentTemplate } from '../_model/meta/Property';
import { Marketplace } from '../_model/marketplace';
<<<<<<< HEAD
import { isNullOrUndefined } from 'util';
import { async } from '@angular/core/testing';
=======
import { PropertyService } from '../_service/property.service';
>>>>>>> flexProd_Changes
import { UserDefinedTaskTemplateService } from '../_service/user-defined-task-template.service';
import { PropertyDefinitionService } from '../_service/meta/core/property/property-definition.service';

@Component({
  selector: 'app-property-detail',
  templateUrl: './property-detail.component.html',
  styleUrls: ['./property-detail.component.scss']
})
export class PropertyDetailComponent implements OnInit {

  role: ParticipantRole;
  participant: Participant;
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

    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((participant: Participant) => this.participant = participant)
    
    ]).then(() => {
      let parameters;
      let queryParameters;

      Promise.all([
        
        this.route.params.subscribe(params => {
          parameters = params;
        }),
        this.route.queryParams.subscribe(params => {
          queryParameters = params;
        })  
      ]).then(() => {
        this.loadProperty(parameters['marketplaceId'], parameters['templateId'], 
          parameters['subtemplateId'], parameters['propertyId'], queryParameters['ref']);
      });
    });
  }

  loadProperty(marketplaceId: string, templateId: string, subtemplateId: string, propId: string, ref: string): void {
    
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;

      if (ref == 'list') {
        this.propertyDefinitionService.getPropertyDefinitionById(marketplace, propId).toPromise().then((propertyDefintion: PropertyDefinition<any>) => {
          this.propertyDefintion = propertyDefintion;    
        }).then(() => {
<<<<<<< HEAD
          console.log(this.propertyDefintion);
=======
>>>>>>> flexProd_Changes
          this.isLoaded = true;
        });
       
      } else if (ref == 'template') {

      } else if (ref == 'subtemplate') {
        this.userDefinedTaskTemplateService.getPropertyFromSubTemplate(this.marketplace, templateId, subtemplateId, propId).toPromise().then((propertyDefintion: PropertyDefinition<any>) => {
          this.propertyDefintion = propertyDefintion;

<<<<<<< HEAD
          // this.propertyDefinitionService.getPropertyParentItems(this.marketplace, propertyDefintion.id, templateId, subtemplateId).toPromise().then((parents: PropertyItem[]) => {
          //   console.log("Recheived PropertyParentItem");
          //   console.log(parents);
          //   this.templateItem = parents[0];

          //   if (parents.length >= 2) {
          //     this.subtemplateItem = parents[0];
          //   }
          //   this.isLoaded = true;
          // });

=======
          this.propertyService.getPropertyParentItems(this.marketplace, property.id, templateId, subtemplateId).toPromise().then((parents: PropertyParentItem[]) => {
            this.templateItem = parents[0];

            if (parents.length >= 2) {
              this.subtemplateItem = parents[0];
            }
            this.isLoaded = true;
          });
>>>>>>> flexProd_Changes
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
