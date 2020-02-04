import { Component, OnInit } from '@angular/core';
<<<<<<< HEAD
import { Router, ActivatedRoute } from '@angular/router';
// import { PropertyService } from '../_service/property.service';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
=======
import { ActivatedRoute } from '@angular/router';
import { PropertyService } from '../_service/property.service';
>>>>>>> flexProd_Changes
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
<<<<<<< HEAD
  currentProperty: PropertyDefinition<any>;
  // form: FormGroup = new FormGroup({});
  

=======
  currentProperty: Property<any>;
>>>>>>> flexProd_Changes

  isLoaded: boolean = false;
  whichProperty: string;

<<<<<<< HEAD

  propertyListItems: PropertyDefinition<any>[];
=======
  propertyListItems: Property<any>[];
>>>>>>> flexProd_Changes

  constructor(
    private route: ActivatedRoute,
<<<<<<< HEAD
    private propertyDefinitionService: PropertyDefinitionService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
=======
    private propertyService: PropertyService,
>>>>>>> flexProd_Changes
    private marketPlaceService: CoreMarketplaceService){
  }

  ngOnInit() {
    // get propertylist
    this.route.params.subscribe(params => {
      this.marketPlaceService.findById(params['marketplaceId']).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;

        let marketplaceLoaded, propertyLoaded: boolean = false;

<<<<<<< HEAD
        this.propertyDefinitionService.getAllPropertyDefinitons(marketplace).toPromise().then((pdArr: PropertyDefinition<any>[]) => {
          this.propertyListItems = pdArr;
            
            console.log("properties:");
            console.log(this.propertyListItems);


            marketplaceLoaded= true;
            this.isLoaded = marketplaceLoaded && propertyLoaded;
=======
        this.propertyService.getProperties(marketplace).toPromise().then((pArr: Property<any>[]) => {
          this.propertyListItems = pArr;
          marketplaceLoaded= true;
          this.isLoaded = marketplaceLoaded && propertyLoaded;
>>>>>>> flexProd_Changes
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
