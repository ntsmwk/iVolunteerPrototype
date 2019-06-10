import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PropertyService } from '../_service/property.service';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { ParticipantRole, Participant } from '../_model/participant';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../_model/marketplace';
import { PropertyListItem, PropertyKind, RuleKind, Rule, Property } from '../_model/properties/Property';
import { FormGroup } from '@angular/forms';
import { EventEmitter } from 'events';
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

  constructor(private router: Router,
    private route: ActivatedRoute,
    private propertyService: PropertyService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private marketPlaceService: CoreMarketplaceService){

    }

  ngOnInit() {
    console.log("init property build form");


    // get propertylist
    this.route.params.subscribe(params => {
      this.marketPlaceService.findById(params['marketplaceId']).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;

        let marketplaceLoaded, propertyLoaded: boolean = false;

        this.propertyService.getPropertyList(marketplace).toPromise().then((pArr: PropertyListItem[]) => {
          this.propertyListItems = pArr;
            
            console.log("properties:");
            console.log(this.propertyListItems);


            marketplaceLoaded= true;
            this.isLoaded = marketplaceLoaded && propertyLoaded;
        });


        console.log(params['propertyId']);

        if (!isNullOrUndefined(params['propertyId'])) {
          this.propertyService.getPropertyFromList(marketplace, params['propertyId']).toPromise().then((property: Property<any>) => {
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
    if (this.currentProperty.kind == PropertyKind.MULTI) {
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
