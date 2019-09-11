import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { LoginService } from '../_service/login.service';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { ParticipantRole, Participant } from '../_model/participant';
import { Property, PropertyParentItem, PropertyParentSubTemplate, PropertyParentTemplate } from '../_model/meta/Property';
import { Marketplace } from '../_model/marketplace';
import { PropertyService } from '../_service/property.service';
import { UserDefinedTaskTemplateService } from '../_service/user-defined-task-template.service';

@Component({
  selector: 'app-property-detail',
  templateUrl: './property-detail.component.html',
  styleUrls: ['./property-detail.component.scss']
})
export class PropertyDetailComponent implements OnInit {

  role: ParticipantRole;
  participant: Participant;
  marketplace: Marketplace;
  property: Property<any>;

  templateItem: PropertyParentTemplate;
  subtemplateItem: PropertyParentSubTemplate;

  isLoaded: boolean;
  columnsToDisplay = ['value'];

  constructor(private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private propertyService: PropertyService,
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
        this.propertyService.getPropertyFromList(marketplace, propId).toPromise().then((property: Property<any>) => {
          this.property = property;    
        }).then(() => {
          this.isLoaded = true;
        });
       
      } else if (ref == 'template') {

      } else if (ref == 'subtemplate') {
        this.userDefinedTaskTemplateService.getPropertyFromSubTemplate(this.marketplace, templateId, subtemplateId, propId).toPromise().then((property: Property<any>) => {
          this.property = property;

          this.propertyService.getPropertyParentItems(this.marketplace, property.id, templateId, subtemplateId).toPromise().then((parents: PropertyParentItem[]) => {
            this.templateItem = parents[0];

            if (parents.length >= 2) {
              this.subtemplateItem = parents[0];
            }
            this.isLoaded = true;
          });
        });
      }
    }); 
  }

  displayPropertyValue(property: Property<any>): string {    
    return Property.getValue(property);
  }

  displayPropertyDefaultValue(property: Property<any>): string {
    return Property.getDefaultValue(property);
  }

  navigateBack() {
    window.history.back();
  }
}
