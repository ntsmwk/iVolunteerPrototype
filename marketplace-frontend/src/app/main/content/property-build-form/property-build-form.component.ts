import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PropertyService } from '../_service/property.service';
import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { ParticipantRole, Participant } from '../_model/participant';
import { isNullOrUndefined } from 'util';
import { Marketplace } from '../_model/marketplace';
import { PropertyListItem, PropertyKind, RuleKind, Rule } from '../_model/properties/Property';
import { FormGroup } from '@angular/forms';





@Component({
  selector: 'app-property-build-form',
  templateUrl: './property-build-form.component.html',
  styleUrls: ['./property-build-form.component.scss']
})
export class PropertyBuildFormComponent implements OnInit {

  marketplace: Marketplace;
  // form: FormGroup = new FormGroup({});

  isLoaded: boolean = false;


  propertyListItems: PropertyListItem[];

  constructor(private router: Router,
    private route: ActivatedRoute,
    private propertyService: PropertyService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService){

    }

  ngOnInit() {
    console.log("init property build form");
    // get propertylist
    
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.propertyService.getPropertyList(marketplace).toPromise().then((pArr: PropertyListItem[]) => {
          this.propertyListItems = pArr;
          
          console.log("properties:");
          console.log(this.propertyListItems);

          
          this.isLoaded = true;
        })}
      })
    });

  }


  createProperty() {

  }

  createMultiProperty() {

  }

  

  trackByFn(index: any, item: any) {
    return index;
 }

  navigateBack() {
    window.history.back();
  }

}
