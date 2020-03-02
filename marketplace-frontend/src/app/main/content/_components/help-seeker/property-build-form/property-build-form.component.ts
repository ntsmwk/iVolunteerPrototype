import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
// import { PropertyService } from '../_service/property.service';
import { LoginService } from '../../../_service/login.service';
import { CoreHelpSeekerService } from '../../../_service/core-helpseeker.service';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../../../_model/marketplace';
import { PropertyType, PropertyDefinition } from '../../../_model/meta/Property';
import { CoreMarketplaceService } from '../../../_service/core-marketplace.service';
import { PropertyDefinitionService } from '../../../_service/meta/core/property/property-definition.service';
import { Helpseeker } from '../../../_model/helpseeker';


@Component({
  selector: 'app-property-build-form',
  templateUrl: './property-build-form.component.html',
  styleUrls: ['./property-build-form.component.scss']
})
export class PropertyBuildFormComponent implements OnInit {

  marketplace: Marketplace;
  helpseeker: Helpseeker;
  currentProperty: PropertyDefinition<any>;
  // form: FormGroup = new FormGroup({});



  isLoaded: boolean = false;
  whichProperty: string;


  propertyListItems: PropertyDefinition<any>[];

  constructor(private router: Router,
    private route: ActivatedRoute,
    private propertyDefinitionService: PropertyDefinitionService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private marketPlaceService: CoreMarketplaceService) {

  }

  ngOnInit() {
    console.log("init property build form");


    // get propertylist
    this.route.params.subscribe(params => {
      this.marketPlaceService.findById(params['marketplaceId']).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;

        let marketplaceLoaded, propertyLoaded: boolean = false;

        this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
          this.helpseeker = helpseeker;

          this.propertyDefinitionService.getAllPropertyDefinitons(marketplace, this.helpseeker.tenantId).toPromise().then((pdArr: PropertyDefinition<any>[]) => {
            this.propertyListItems = pdArr;

            console.log("properties:");
            console.log(this.propertyListItems);


            marketplaceLoaded = true;
            this.isLoaded = marketplaceLoaded && propertyLoaded;
          });


          console.log(params['propertyId']);

          if (!isNullOrUndefined(params['propertyId'])) {
            this.propertyDefinitionService.getPropertyDefinitionById(marketplace, params['propertyId'], this.helpseeker.tenantId).toPromise().then((property: PropertyDefinition<any>) => {
              this.currentProperty = property;

              console.log("current Property");
              console.log(this.currentProperty);

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
    });


    // this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
    //   this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
    //     if (!isNullOrUndefined(marketplace)) {
    //       this.marketplace = marketplace;
    //       this.propertyService.getPropertyList(marketplace).toPromise().then((pArr: PropertyListItem[]) => {
    //         this.propertyListItems = pArr;

    //         console.log("properties:");
    //         console.log(this.propertyListItems);


    //         this.isLoaded = true;
    //       });


    //       this.route.params.subscribe(params => {

    //         if (params['taskId'] == undefined) {

    //         }
    //         console.log("PARAMS");
    //         console.log(params['marketplaceId']);
    //         console.log(params['taskId']);
    //       })



    //     }
    //   })
    // });
  }

  setWhichProperty() {
    if (this.currentProperty.type == PropertyType.MULTI) {
      this.whichProperty = 'multi';
    } else {
      this.whichProperty = 'single';
    }
  }

  //   trackByFn(index: any, item: any) {
  //     return index;
  //  }

  navigateBack() {
    window.history.back();
  }

}
