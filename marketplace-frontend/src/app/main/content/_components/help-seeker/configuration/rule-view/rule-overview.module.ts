import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";

import { FuseSharedModule } from "@fuse/shared.module";

import { FuseRuleConfiguratorComponent } from "../rule-configurator/rule-configurator.component";
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
import { FuseRuleOverviewComponent } from "./rule-overview.component";
import { HeaderModule } from "app/main/content/_components/_shared/header/header.module";

const routes = [{ path: "", component: FuseRuleOverviewComponent }];

@NgModule({
  declarations: [FuseRuleOverviewComponent],
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
export class FuseRuleOverviewModule { }
