import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { MatTableDataSource } from "@angular/material/table";
import { Marketplace } from "../../../../_model/marketplace";
import { LoginService } from "../../../../_service/login.service";
import { fuseAnimations } from "@fuse/animations";
import {
  DerivationRule,
  ComparisonOperatorType,
  AggregationOperatorType,
} from "../../../../_model/derivation-rule";
import { DerivationRuleService } from "../../../../_service/derivation-rule.service";
import { Tenant } from "app/main/content/_model/tenant";
import { User } from "app/main/content/_model/user";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "fuse-rule-overview",
  templateUrl: "./rule-overview.component.html",
  styleUrls: ["./rule-overview.component.scss"],
  animations: fuseAnimations,
})
export class FuseRuleOverviewComponent implements OnInit {
  marketplace: Marketplace;
  dataSource = new MatTableDataSource<DerivationRule>();
  displayedColumns = ["active", "name", "sourcesGeneral", "sourcesClasses", "target"];
  helpseeker: User;
  tenant: Tenant;

  constructor(
    private router: Router,
    private loginService: LoginService,
    private derivationRuleService: DerivationRuleService,
  ) { }

  async ngOnInit() {
    this.loadAllDerivationRules();
  }

  onRowSelect(derivationRule: DerivationRule, event) {
    this.router.navigate(["/main/rule/" + derivationRule.id]);
  }

  onChangeActivation(derivationRule: DerivationRule){
    this.derivationRuleService
      .save(this.marketplace, derivationRule)
      .toPromise();
    // this.loadAllDerivationRules();
  }

  private async loadAllDerivationRules() {
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
    this.helpseeker = globalInfo.user;
    this.tenant = globalInfo.tenants[0];

    this.derivationRuleService
      .findAll(this.marketplace, this.tenant.id)
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
