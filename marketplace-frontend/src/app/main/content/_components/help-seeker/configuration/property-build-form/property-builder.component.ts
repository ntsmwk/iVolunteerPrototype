import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';

@Component({
  selector: 'app-property-builder',
  templateUrl: './property-builder.component.html',
  styleUrls: ['./property-builder.component.scss']
})
export class PropertyBuildFormComponent implements OnInit {

  marketplace: Marketplace;
  helpseeker: Helpseeker;
  loaded = false;

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private helpseekerService: CoreHelpSeekerService
  ) { }

  ngOnInit() {
    // get propertylist
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

}
