import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { fuseAnimations } from '@fuse/animations';

import { PropertyDefinition } from "../../../../_model/meta/Property";

import { LoginService } from '../../../../_service/login.service';
import { CoreHelpSeekerService } from '../../../../_service/core-helpseeker.service';
import { Participant, ParticipantRole } from '../../../../_model/participant';
import { Marketplace } from '../../../../_model/marketplace';

import { isNullOrUndefined } from 'util';
import { PropertyDefinitionService } from '../../../../_service/meta/core/property/property-definition.service';
import { CoreFlexProdService } from '../../../../_service/core-flexprod.service';
import { Helpseeker } from '../../../../_model/helpseeker';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss'],
  animations: fuseAnimations
})
export class PropertyListComponent implements OnInit {

  dataSource = new MatTableDataSource<PropertyDefinition<any>>();
  displayedColumns = ['id', 'name', 'defaultValue', 'kind', 'actions'];
  // displayedColumns = ['id', 'name', 'defaultValue', 'kind'];

  marketplace: Marketplace;
  helpseeker: Helpseeker;

  propertyDefinitionArray: PropertyDefinition<any>[];

  customOnly: boolean;
  isLoaded: boolean;

  constructor(private router: Router,
    private propertyDefinitionService: PropertyDefinitionService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private flexProdService: CoreFlexProdService) {
  }

  ngOnInit() {
    this.isLoaded = false;
    this.customOnly = false;
    this.loadAllProperties();

  }

  onRowSelect(p: PropertyDefinition<any>) {
    console.log("Property Clicked: " + p.name);
    console.log("CURRENT URL: " + this.router.url)
    this.router.navigate(['/main/properties/' + this.marketplace.id + '/' + p.id]);
  }



  loadAllProperties() {
    console.log("load props from Server...");

    let service: CoreHelpSeekerService | CoreFlexProdService;

    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.helpseeker = helpseeker;
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
        if (role == 'FLEXPROD') {
          service = this.flexProdService;
        } else if (role == 'HELP_SEEKER') {
          service = this.helpSeekerService;
        } else {
          return;
        }
      }).then(() => {
        service.findRegisteredMarketplaces(helpseeker.id).toPromise().then((marketplace: Marketplace) => {
          if (!isNullOrUndefined(marketplace)) {
            this.marketplace = marketplace;
            this.propertyDefinitionService.getAllPropertyDefinitons(marketplace, this.helpseeker.tenantId).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
              this.propertyDefinitionArray = propertyDefinitions;
              this.updateDataSource();
              this.isLoaded = true;
            });
          }
        });
      });
    });

      
  }

  updateDataSource() {
    let ret: PropertyDefinition<any>[] = [];

    for (let property of this.propertyDefinitionArray) {
      if (!this.customOnly) {
        ret.push(property);
      } else {
        property.custom ? ret.push(property) : null;
      }
    }

    this.dataSource.data = ret;
  }



  viewPropertyAction(property: PropertyDefinition<any>) {
    console.log("clicked view Property")
    this.router.navigate(['main/property/detail/view/' + this.marketplace.id + '/' + property.id], { queryParams: { ref: 'list' } });
    console.log(property);
  }

  newPropertyAction() {
    console.log("clicked new Property");
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/']);
  }

  editPropertyAction(property: PropertyDefinition<any>) {
    console.log("clicked edit Property: ");
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/' + property.id]);

    console.log(property)

    console.log("TODO create detail/edit page");

  }

  deletePropertyAction(property: PropertyDefinition<any>) {
    console.log("clicked delete Property: ");
    console.log(property)

    this.propertyDefinitionService.deletePropertyDefinition(this.marketplace, property.id).toPromise().then(() => {
      this.ngOnInit();
      console.log("done");
    });


  }


  // updateProperty(item: PropertyListItem) {
  // console.log("clicked to update property " + item.id + " " + item.name + " TODO - link to detail page");

  // }

  // updatePropertySave(property: Property<string>) {
  //   console.log("attempt to update");
  //   this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
  //     this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
  //       if (!isNullOrUndefined(marketplace)) {
  //         this.propertyService.updateProperty(marketplace, <Property<string>>property).toPromise().then(() => 
  //           console.log("Updated"));

  //       }
  //     })
  //   });
  //}

  // displayPropertyValue(property: PropertyListItem): string {    
  //   return PropertyListItem.getValue(property);
  // }
}
