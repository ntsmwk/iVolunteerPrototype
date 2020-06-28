import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";

import { FuseSharedModule } from "@fuse/shared.module";

import { FuseRuleConfiguratorComponent } from "./rule-configurator.component";
import { MatButtonModule } from "@angular/material/button";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatChipsModule } from "@angular/material/chips";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatTableModule } from "@angular/material/table";
import { MatTabsModule, MatSelectModule } from "@angular/material";
import { FuseClassRulePreconditionConfiguratorComponent } from "./class-rule-configurator-precondition/class-rule-configurator-precondition.component";
import { FuseAttributeRulePreconditionConfiguratorComponent } from "./attribute-rule-configurator-precondition.1/attribute-rule-configurator-precondition.component";
import { HeaderModule } from "app/main/content/_shared_components/header/header.module";
import { GeneralPreconditionConfiguratorComponent } from './general-precondition-configurator/general-precondition-configurator.component';
import { TargetRuleConfiguratorComponent } from './target-rule-configurator/target-rule-configurator.component';
import { TargetAttributeRuleConfiguratorComponent } from './target-attribute-rule-configurator/target-attribute-rule-configurator.component';
import { TestRuleConfigurationComponent } from './test-rule-configuration/test-rule-configuration.component';

const routes = [
  { path: "", component: FuseRuleConfiguratorComponent },
  { path: ":ruleId", component: FuseRuleConfiguratorComponent },
];

@NgModule({
  declarations: [
    FuseRuleConfiguratorComponent,
    FuseAttributeRulePreconditionConfiguratorComponent,
    FuseClassRulePreconditionConfiguratorComponent,
    GeneralPreconditionConfiguratorComponent,
    TargetRuleConfiguratorComponent,
    TargetAttributeRuleConfiguratorComponent,
    TestRuleConfigurationComponent,
  ],
  imports: [
    ReactiveFormsModule,
    RouterModule.forChild(routes),

    MatSelectModule,
    MatTabsModule,
    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatDividerModule,
    MatTableModule,
    MatCheckboxModule,
    HeaderModule,
    FuseSharedModule,
  ],
})
export class FuseRuleConfiguratorModule { }
