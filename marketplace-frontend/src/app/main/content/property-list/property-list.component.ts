import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material';
import { fuseAnimations } from '@fuse/animations';

import {Property } from "../_model/meta/Property";

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

  dataSource = new MatTableDataSource<Property<any>>();
  displayedColumns = ['id', 'name', 'defaultValue', 'kind', 'actions'];

  marketplace: Marketplace;

  propertyArray: Property<any>[];

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

  onRowSelect(p: Property<any>) {
    this.router.navigate(['/main/properties/' + this.marketplace.id + '/' + p.id]);
  }


 
  loadAllProperties() {
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.propertyService.getProperties(marketplace).toPromise().then((pArr: Property<any>[]) => {
            this.propertyArray = pArr;
            this.updateDataSource();
            this.isLoaded = true;
        })}
      })
    });
  }

  updateDataSource() {
    let ret: Property<any>[] = [];

    for (let property of this.propertyArray) {
      if (!this.customOnly) {
        ret.push(property);
      }  else {
        property.custom ? ret.push(property) : null ;
      }
    }
    this.dataSource.data = ret;
  }

  

  viewPropertyAction(property: Property<any>) {
    this.router.navigate(['main/property/detail/view/' + this.marketplace.id + '/' + property.id],{queryParams: {ref: 'list'}});
  }

  newPropertyAction() {
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/'] );
  }

  editPropertyAction(property: Property<any>) { 
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/' + property.id]);
  }

  deletePropertyAction(property: Property<any>) {
    this.propertyService.deleteProperty(this.marketplace, property.id).toPromise().then(() => {
      this.ngOnInit();
    });
  }

}
