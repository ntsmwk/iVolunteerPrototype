import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { MatTableDataSource } from "@angular/material/table";
import { Marketplace } from "../../../../_model/marketplace";
import { CoreHelpSeekerService } from "../../../../_service/core-helpseeker.service";
import { LoginService } from "../../../../_service/login.service";
import { fuseAnimations } from "@fuse/animations";
import { isNullOrUndefined } from "util";
import { DerivationRule, ComparisonOperatorType, AggregationOperatorType } from "../../../../_model/derivation-rule";
import { DerivationRuleService } from "../../../../_service/derivation-rule.service";
import { Tenant } from "app/main/content/_model/tenant";
import { HelpSeekerGuard } from "app/main/content/_guard/help-seeker.guard";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { User, UserRole } from "app/main/content/_model/user";

@Component({
  selector: "fuse-rule-overview",
  templateUrl: "./rule-overview.component.html",
  styleUrls: ["./rule-overview.component.scss"],
  animations: fuseAnimations,
})
export class FuseRuleOverviewComponent implements OnInit {
  marketplace: Marketplace;
  dataSource = new MatTableDataSource<DerivationRule>();
  displayedColumns = ["name", "sourcesGeneral", "sourcesClasses", "target"];
  helpseeker: User;
  tenant: Tenant;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private derivationRuleService: DerivationRuleService,
    private tenantService: TenantService
  ) {}

  async ngOnInit() {
    this.loadAllDerivationRules();
  }

  onRowSelect(derivationRule: DerivationRule) {
    console.log("row selected: " + derivationRule.id);
    this.router.navigate(["/main/rule/" + derivationRule.id]);
  }

  private async loadAllDerivationRules() {
    this.helpseeker = <User>await this.loginService.getLoggedIn().toPromise();

    this.marketplace = <Marketplace>(
      await this.helpSeekerService
        .findRegisteredMarketplaces(this.helpseeker.id)
        .toPromise()
    );

    this.tenant = <Tenant>(
      await this.tenantService
        .findById(
          this.helpseeker.subscribedTenants.find(
            (t) => t.role === UserRole.HELP_SEEKER
          ).tenantId
        )
        .toPromise()
    );

    this.derivationRuleService
      .findAll(this.marketplace, this.helpseeker.subscribedTenants.find(
        (t) => t.role === UserRole.HELP_SEEKER
      ).tenantId)
      .toPromise()
      .then((rules: DerivationRule[]) => (this.dataSource.data = rules));
  }

  addDerivationRule() {
    this.router.navigate(["/main/rule"]);
  }

  private retrieveComparisonOperatorValueOf(op) {
    let x: ComparisonOperatorType =
      ComparisonOperatorType[op as keyof typeof ComparisonOperatorType];
    return x;
  }

  private retrieveAggregationOperatorValueOf(op) {
    let x: AggregationOperatorType =
      AggregationOperatorType[op as keyof typeof AggregationOperatorType];
    return x;
  }
}
