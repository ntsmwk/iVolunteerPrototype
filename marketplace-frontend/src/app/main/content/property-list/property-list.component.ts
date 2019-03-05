import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatTableDataSource } from '@angular/material';
import { fuseAnimations } from '@fuse/animations';

import { PropertyListItem, Property, PropertyKind } from "../_model/properties/Property";

import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { Participant } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';

import { PropertyService } from "../_service/property.service";
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'fuse-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss'],
  animations: fuseAnimations
})
export class FusePropertyListComponent implements OnInit {

  dataSource = new MatTableDataSource<PropertyListItem>();
  displayedColumns = ['id', 'name', 'value', 'kind'];

  marketplace: Marketplace;

  constructor(private router: Router,
    private propertyService: PropertyService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService) {
    }

  ngOnInit() {
    this.loadAllProperties();
  }

  onRowSelect(p: Property<Object>) {
    console.log("Property Clicked: " + p.name );
    console.log("CURRENT URL: " + this.router.url)
    this.router.navigate(['/main/property/' + this.marketplace.id + '/' + p.id]);
  }


 
  loadAllProperties() {
    console.log ("load props from Server...");

   
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.propertyService.getPropertyList(marketplace).toPromise().then((pArr: PropertyListItem[]) => {
          this.dataSource.data = pArr;
        })}
      })
    });
  }

  newProperty() {
    console.log("clicked new Property");
    this.addProperty();
  }

 private addProperty() {
  console.log("TODO: Navigate to add Property Detail page");

  }

  updateProperty(item: PropertyListItem) {
  console.log("clicked to update property " + item.id + " " + item.name + " TODO - link to detail page");

  }

  updatePropertySave(property: Property<string>) {
    console.log("attempt to update");
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.propertyService.updateProperty(marketplace, <Property<string>>property).toPromise().then(() => 
            console.log("Updated"));
          
        }
      })
    });
  }

}
