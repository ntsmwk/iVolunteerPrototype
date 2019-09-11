import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PropertyService } from '../_service/property.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../_model/marketplace';
import { PropertyType, Property } from '../_model/meta/Property';
import { CoreMarketplaceService } from '../_service/core-marketplace.service';


@Component({
  selector: 'app-property-build-form',
  templateUrl: './property-build-form.component.html',
  styleUrls: ['./property-build-form.component.scss']
})
export class PropertyBuildFormComponent implements OnInit {

  marketplace: Marketplace;
  currentProperty: Property<any>;

  isLoaded: boolean = false;
  whichProperty: string;

  propertyListItems: Property<any>[];

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

        this.propertyService.getProperties(marketplace).toPromise().then((pArr: Property<any>[]) => {
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
