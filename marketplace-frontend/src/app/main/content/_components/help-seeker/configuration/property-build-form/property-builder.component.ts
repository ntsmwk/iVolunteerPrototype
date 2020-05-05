import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { PropertyDefinition } from 'app/main/content/_model/meta/property';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'app-property-builder',
  templateUrl: './property-builder.component.html',
  styleUrls: ['./property-builder.component.scss']
})
export class PropertyBuildFormComponent implements OnInit {

  marketplace: Marketplace;
  helpseeker: Helpseeker;
  loaded: boolean;

  displayBuilder: boolean;
  displayResultSuccess: boolean;

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private helpseekerService: CoreHelpSeekerService
  ) { }

  ngOnInit() {
    // get propertylist
    this.displayBuilder = true;
    this.displayResultSuccess = false;

    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.helpseeker = helpseeker;
      this.helpseekerService.findRegisteredMarketplaces(helpseeker.id).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.loaded = true;
      });
    });

  }

  navigateBack() {
    window.history.back();
  }

  handleResultEvent(result: PropertyDefinition<any>) {
    this.displayBuilder = false;

    if (!isNullOrUndefined(result)) {
      this.displayResultSuccess = true;
    } else {
      this.displayResultSuccess = false;
    }
  }

  handleAddAnotherClick() {
    this.displayResultSuccess = false;
    this.displayBuilder = true;
  }

}
