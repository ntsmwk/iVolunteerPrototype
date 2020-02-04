import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
// import { PropertyService } from '../_service/property.service';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../_model/marketplace';
import { PropertyType, PropertyDefinition } from '../_model/meta/Property';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';
import { PropertyDefinitionService } from '../_service/meta/core/property/property-definition.service';


@Component({
  selector: 'app-property-build-form',
  templateUrl: './property-build-form.component.html',
  styleUrls: ['./property-build-form.component.scss']
})
export class PropertyBuildFormComponent implements OnInit {

  marketplace: Marketplace;
  currentProperty: PropertyDefinition<any>;
  // form: FormGroup = new FormGroup({});
  


  isLoaded: boolean = false;
  whichProperty: string;


  propertyListItems: PropertyDefinition<any>[];

  constructor(
    private route: ActivatedRoute,
    private propertyDefinitionService: PropertyDefinitionService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private marketPlaceService: CoreMarketplaceService){
  }

  ngOnInit() {
    // get propertylist
    this.route.params.subscribe(params => {
      this.marketPlaceService.findById(params['marketplaceId']).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;

        let marketplaceLoaded, propertyLoaded: boolean = false;

        this.propertyDefinitionService.getAllPropertyDefinitons(marketplace).toPromise().then((pdArr: PropertyDefinition<any>[]) => {
          this.propertyListItems = pdArr;
            
            console.log("properties:");
            console.log(this.propertyListItems);


            marketplaceLoaded= true;
            this.isLoaded = marketplaceLoaded && propertyLoaded;
        });

        if (!isNullOrUndefined(params['propertyId'])) {
          this.propertyDefinitionService.getPropertyDefinitionById(marketplace, params['propertyId']).toPromise().then((property: PropertyDefinition<any>) => {
            this.currentProperty = property;

            this.setWhichProperty();

            propertyLoaded = true;
            this.isLoaded = marketplaceLoaded && propertyLoaded;
          });
        } else {
          propertyLoaded = true;
          this.isLoaded = marketplaceLoaded && propertyLoaded;
        }
      });
    });
  }

  setWhichProperty() {
    if (this.currentProperty.type == PropertyType.MULTI) {
      this.whichProperty = 'multi';
    } else {
      this.whichProperty = 'single';
    }
  }

  navigateBack() {
    window.history.back();
  }

}
