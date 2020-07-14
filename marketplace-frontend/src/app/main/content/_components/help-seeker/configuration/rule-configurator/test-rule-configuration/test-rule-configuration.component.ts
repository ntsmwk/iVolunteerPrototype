import { Component, OnInit, Input, SimpleChanges } from "@angular/core";
import { User } from "app/main/content/_model/user";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatTableDataSource } from "@angular/material/table";
import { Tenant } from "app/main/content/_model/tenant";
import {
  RuleExecution,
  RuleStatus,
} from "app/main/content/_model/derivation-rule-execution";
import {
  DerivationRule,
  ClassCondition,
} from "app/main/content/_model/derivation-rule";
import { DerivationRuleService } from "app/main/content/_service/derivation-rule.service";
import { LoginService } from "app/main/content/_service/login.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { GlobalInfo } from "app/main/content/_model/global-info";

@Component({
  selector: "test-rule-configuration",
  templateUrl: "./test-rule-configuration.component.html",
  styleUrls: ["./test-rule-configuration.component.scss"],
})
export class TestRuleConfigurationComponent implements OnInit {
  @Input("derivationRule") derivationRule: DerivationRule;
  marketplace: Marketplace;
  dataSource = new MatTableDataSource<RuleExecution>();
  classConditions: ClassCondition[];
  ruleExecutions: RuleExecution[];
  displayedColumns = ["Name", "Status"];
  helpseeker: User;
  tenant: Tenant;

  constructor(
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private derivationRuleService: DerivationRuleService,
  ) { }

  async ngOnInit() {
    const globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.marketplace = globalInfo.marketplace;
    this.helpseeker = globalInfo.user;

  }

  private testRule() {
    this.derivationRule.container =
      "simulate execution " + this.derivationRule.name;
    this.derivationRuleService
      .test(this.marketplace, this.derivationRule)
      .toPromise()
      //  .then(() => this.derivationRuleService.getTestResults(this.marketplace, this.derivationRule)
      //  .toPromise()
      .then((ruleExecutions: RuleExecution[]) => {
        this.dataSource.data = ruleExecutions;
      });
  }

  private retrieveStatusValueOf(status) {
    let x: RuleStatus = RuleStatus[status as keyof typeof RuleStatus];
    return x;
  }
}
