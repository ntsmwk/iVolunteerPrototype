import { Component, OnInit } from '@angular/core';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { PropertyDefinition } from 'app/main/content/_model/meta/property';
import { isNullOrUndefined } from 'util';
import { Tenant } from 'app/main/content/_model/tenant';
import { TenantService } from 'app/main/content/_service/core-tenant.service';
import { Router, Route, ActivatedRoute } from '@angular/router';
import { MarketplaceService } from 'app/main/content/_service/core-marketplace.service';

@Component({
  selector: "app-property-build-form",
  templateUrl: './property-build-form.component.html',
  styleUrls: ['./property-build-form.component.scss'],
})
export class PropertyBuildFormComponent implements OnInit {

  marketplaceId: string;
  marketplace: Marketplace;

  entryId: string;

  helpseeker: Helpseeker;
  loaded: boolean;

  displayBuilder: boolean;
  displayResultSuccess: boolean;
  builderType: string;

  // tenant: Tenant;

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private helpseekerService: CoreHelpSeekerService,
    private marketplaceService: MarketplaceService,
    private tenantService: TenantService
  ) { }

  async ngOnInit() {
    this.displayBuilder = true;
    this.displayResultSuccess = false;

    const [queryParamResult, paramResult] = await Promise.all([

      this.route.queryParams.subscribe((params) => {
        if (isNullOrUndefined(params['type'] || params['type'] === 'property')) {
          this.builderType = 'property';
        } else {
          this.builderType = params['type'];
        }
      }),

      this.route.params.subscribe((params) => {
        // console.log(params);
        this.marketplaceId = params['marketplaceId'];
        this.entryId = params['entryId'];
      })
    ]);

    // console.log("????????????????????????????????ßßß");
    // console.log(queryParamResult);
    // console.log(paramResult);

    // console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    // console.log(this.marketplaceId);
    // console.log(this.builderType);
    // console.log(this.entryId);
    // TODO - open existing asset in build form

    this.helpseeker = <Helpseeker>(
      await this.loginService.getLoggedIn().toPromise()
    );
    // console.log(this.helpseeker);

    this.marketplace = <Marketplace>(
      // await this.helpseekerService
      //   .findRegisteredMarketplaces(this.helpseeker.id)
      //   .toPromise()
      await this.marketplaceService.findById(this.marketplaceId).toPromise()
    );




    this.loaded = true;
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
