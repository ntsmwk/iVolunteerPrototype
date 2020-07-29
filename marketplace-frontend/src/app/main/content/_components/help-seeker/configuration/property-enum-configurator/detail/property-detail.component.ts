import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { UserRole, User } from "app/main/content/_model/user";
import {
  PropertyDefinition,
  PropertyParentTemplate,
  PropertyParentSubTemplate,
} from "app/main/content/_model/meta/property";
import { Marketplace } from "app/main/content/_model/marketplace";
import { LoginService } from "app/main/content/_service/login.service";
import { MarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { PropertyDefinitionService } from "app/main/content/_service/meta/core/property/property-definition.service";
import { GlobalInfo } from "app/main/content/_model/global-info";
import { Tenant } from "app/main/content/_model/tenant";

@Component({
  selector: "app-property-detail",
  templateUrl: "./property-detail.component.html",
  styleUrls: ["./property-detail.component.scss"],
})
export class PropertyDetailComponent implements OnInit {
  role: UserRole;
  tenantAdmin: User;
  marketplace: Marketplace;
  tenant: Tenant;
  propertyDefintion: PropertyDefinition<any>;

  templateItem: PropertyParentTemplate;
  subtemplateItem: PropertyParentSubTemplate;

  isLoaded: boolean;
  columnsToDisplay = ["value"];

  constructor(
    private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: MarketplaceService,
    private propertyDefinitionService: PropertyDefinitionService
  ) {
    this.isLoaded = false;
  }

  async ngOnInit() {
    let globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.role = globalInfo.userRole;
    this.tenantAdmin = globalInfo.user;
    this.tenant = globalInfo.tenants[0];

    let parameters;
    let queryParameters;

    Promise.all([
      this.route.params.subscribe((params) => {
        parameters = params;
      }),
      this.route.queryParams.subscribe((params) => {
        queryParameters = params;
      }),
    ]).then(() => {
      this.loadProperty(
        parameters["marketplaceId"],
        parameters["templateId"],
        parameters["subtemplateId"],
        parameters["propertyId"],
        queryParameters["ref"]
      );
    });
  }

  loadProperty(
    marketplaceId: string,
    templateId: string,
    subtemplateId: string,
    propId: string,
    ref: string
  ): void {
    this.marketplaceService
      .findById(marketplaceId)
      .toPromise()
      .then((marketplace: Marketplace) => {
        this.marketplace = marketplace;

        if (ref === "list") {
          this.propertyDefinitionService
            .getPropertyDefinitionById(marketplace, propId, this.tenant.id)
            .toPromise()
            .then((propertyDefintion: PropertyDefinition<any>) => {
              this.propertyDefintion = propertyDefintion;
            })
            .then(() => {
              this.isLoaded = true;
            });
        } else if (ref === "template") {
        } else if (ref === "subtemplate") {
          // this.userDefinedTaskTemplateService
          //   .getPropertyFromSubTemplate(
          //     this.marketplace,
          //     templateId,
          //     subtemplateId,
          //     propId
          //   )
          //   .toPromise()
          //   .then((propertyDefintion: PropertyDefinition<any>) => {
          //     this.propertyDefintion = propertyDefintion;
          //   });
        }
      });
  }

  navigateBack() {
    window.history.back();
  }
}
