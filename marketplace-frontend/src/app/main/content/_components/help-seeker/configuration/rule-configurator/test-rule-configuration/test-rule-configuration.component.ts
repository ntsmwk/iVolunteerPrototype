import { Component, OnInit, Input } from '@angular/core';
import { Helpseeker } from "app/main/content/_model/helpseeker";
import { Marketplace } from "app/main/content/_model/marketplace";
import { MatTableDataSource } from "@angular/material/table";
import { Tenant } from "app/main/content/_model/tenant";
import { RuleExecution, RuleStatus } from 'app/main/content/_model/derivation-rule-execution';
import { DerivationRule, ClassCondition } from 'app/main/content/_model/derivation-rule';
import { DerivationRuleService } from 'app/main/content/_service/derivation-rule.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';

@Component({
  selector: 'test-rule-configuration',
  templateUrl: './test-rule-configuration.component.html',
  styleUrls: ['./test-rule-configuration.component.scss']
})
export class TestRuleConfigurationComponent implements OnInit {
  @Input("derivationRule") derivationRule: DerivationRule;
  marketplace: Marketplace;
  dataSource = new MatTableDataSource<RuleExecution>();
  classConditions: ClassCondition[];
  ruleExecutions: RuleExecution[];
  displayedColumns = ["Name", "Status"];
  helpseeker: Helpseeker;
  tenant: Tenant;

  constructor(   
    
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService,
    private derivationRuleService: DerivationRuleService,

  ) {
    console.log("constructor test-rule-configuration");
    console.log(" derivationRule: " + this.derivationRule);
  }

  async ngOnInit() {
    console.log("init test-rule-configuration --> " + this.derivationRule);
    this.helpseeker = <Helpseeker>(
      await this.loginService.getLoggedIn().toPromise()
    );

    this.marketplace = <Marketplace>(
      await this.helpSeekerService
        .findRegisteredMarketplaces(this.helpseeker.id)
        .toPromise()
    );

    this.testRule();
  }

  private testRule(){
    console.log("in component test-rule-configuration " + this.derivationRule);
    this.derivationRule.container = "simulate execution " + this.derivationRule.name;
    this.derivationRuleService
      .test(this.marketplace, this.derivationRule)
      .toPromise()
    //  .then(() => this.derivationRuleService.getTestResults(this.marketplace, this.derivationRule)
    //  .toPromise()
      .then((ruleExecutions: RuleExecution[]) => {
        console.log(ruleExecutions); 
        this.dataSource.data = ruleExecutions;
        // this.ruleExecutions = ruleExecutions;
      });
    //this.dataSource.data = this.ruleExecutions;
    console.log("loaded? ruleExecutions = " + this.ruleExecutions);
  }

  private retrieveStatusValueOf(status) {
    let x: RuleStatus =
      RuleStatus[status as keyof typeof RuleStatus];
    return x;
  }

}

