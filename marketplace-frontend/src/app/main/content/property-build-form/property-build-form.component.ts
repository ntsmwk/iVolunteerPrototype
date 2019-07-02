import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PropertyService } from '../_service/property.service';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../_model/marketplace';
import { PropertyListItem, PropertyKind, Property } from '../_model/properties/Property';

import { CoreMarketplaceService } from '../_service/core-marketplace.service';

@Component({
  selector: 'app-property-build-form',
  templateUrl: './property-build-form.component.html',
  styleUrls: ['./property-build-form.component.scss']
})
export class PropertyBuildFormComponent implements OnInit {

  marketplace: Marketplace;
  currentProperty: Property<any>;
  // form: FormGroup = new FormGroup({});

  isLoaded: boolean = false;
  whichProperty: string;


  propertyListItems: PropertyListItem[];

  constructor(
    private route: ActivatedRoute,
    private propertyService: PropertyService,

    private marketPlaceService: CoreMarketplaceService){

    }

  ngOnInit() {
    // get propertylist
    this.route.params.subscribe(params => {
      this.marketPlaceService.findById(params['marketplaceId']).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;

        let marketplaceLoaded, propertyLoaded: boolean = false;

        this.propertyService.getPropertyList(marketplace).toPromise().then((pArr: PropertyListItem[]) => {
          this.propertyListItems = pArr;

            marketplaceLoaded= true;
            this.isLoaded = marketplaceLoaded && propertyLoaded;
        });

        if (!isNullOrUndefined(params['propertyId'])) {
          this.propertyService.getPropertyFromList(marketplace, params['propertyId']).toPromise().then((property: Property<any>) => {
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
    if (this.currentProperty.kind == PropertyKind.MULTI) {
      this.whichProperty = 'multi';
    } else {
      this.whichProperty = 'single';
    }
  }

  navigateBack() {
    window.history.back();
  }

}
