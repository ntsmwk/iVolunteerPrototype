import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { fuseAnimations } from '@fuse/animations';

import { PropertyDefinition } from "../_model/meta/property";

import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { Participant, ParticipantRole } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';

import { isNullOrUndefined } from 'util';
import { PropertyDefinitionService } from '../_service/meta/core/property/property-definition.service';
import { CoreFlexProdService } from '../_service/core-flexprod.service';

@Component({
  selector: 'app-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss'],
  animations: fuseAnimations
})
export class PropertyListComponent implements OnInit {

  dataSource = new MatTableDataSource<PropertyDefinition<any>>();
  displayedColumns = ['id', 'name', 'defaultValue', 'kind', 'actions'];

  marketplace: Marketplace;

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
    this.router.navigate(['/main/properties/' + this.marketplace.id + '/' + p.id]);
  }



  loadAllProperties() {

    let service: CoreHelpSeekerService | CoreFlexProdService;

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => {
        if (role === 'FLEXPROD') {
          service = this.flexProdService;
        } else if (role === 'HELP_SEEKER') {
          service = this.helpSeekerService;
        } else {
          return;
        }
      }).then(() => {
        service.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
          if (!isNullOrUndefined(marketplace)) {
            this.marketplace = marketplace;
            this.propertyDefinitionService.getAllPropertyDefinitons(marketplace).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
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
    const ret: PropertyDefinition<any>[] = [];

    for (const property of this.propertyDefinitionArray) {
      if (!this.customOnly) {
        ret.push(property);
      } else {
        property.custom ? ret.push(property) : null;
      }
    }
    this.dataSource.data = ret;
  }



  viewPropertyAction(property: PropertyDefinition<any>) {
    this.router.navigate(['main/property/detail/view/' + this.marketplace.id + '/' + property.id], { queryParams: { ref: 'list' } });
  }

  newPropertyAction() {
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/']);
  }

  editPropertyAction(property: PropertyDefinition<any>) {
    this.router.navigate(['main/property/detail/edit/' + this.marketplace.id + '/' + property.id]);
  }

  deletePropertyAction(property: PropertyDefinition<any>) {
    this.propertyDefinitionService.deletePropertyDefinition(this.marketplace, property.id).toPromise().then(() => {
      this.ngOnInit();
    });
  }
}
