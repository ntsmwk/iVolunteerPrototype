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
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss'],
  animations: fuseAnimations
})
export class PropertyListComponent implements OnInit {

  dataSource = new MatTableDataSource<PropertyListItem>();
  displayedColumns = ['id', 'name', 'defaultValue', 'kind', 'actions'];

  marketplace: Marketplace;

  propertyArray: PropertyListItem[];

  customOnly: boolean;
  isLoaded: boolean;

  constructor(private router: Router,
    private propertyService: PropertyService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService) {
    }

  ngOnInit() {
    this.isLoaded = false;
    this.customOnly = false;
    this.loadAllProperties();

  }

  onRowSelect(p: PropertyListItem) {
    this.router.navigate(['/main/properties/' + this.marketplace.id + '/' + p.id]);
  }


 
  loadAllProperties() {

    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.propertyService.getPropertyList(marketplace).toPromise().then((pArr: PropertyListItem[]) => {
            this.propertyArray = pArr;
            this.updateDataSource();
            this.isLoaded = true;
        })}
      })
    });
  }

  updateDataSource() {
    let ret: PropertyListItem[] = [];

    for (let property of this.propertyArray) {
      if (!this.customOnly) {
        ret.push(property);
      }  else {
        property.custom ? ret.push(property) : null ;
      }
    }

    this.dataSource.data = ret;
  }

  

  viewPropertyAction(property: PropertyListItem) {
    this.router.navigate(['main/property/detail/view/' + this.marketplace.id + '/' + property.id],{queryParams: {ref: 'list'}});
  }

  newPropertyAction() {
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/'] );
  }

  editPropertyAction(property: PropertyListItem) { 
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/' + property.id]);

  }

  deletePropertyAction(property: PropertyListItem) {
    this.propertyService.deleteProperty(this.marketplace, property.id).toPromise().then(() => {
      this.ngOnInit();
    });
  }
}
