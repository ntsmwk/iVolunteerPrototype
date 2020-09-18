import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

import { FuseSharedModule } from "@fuse/shared.module";

import { DashboardComponent } from "./dashboard.component";
import { MatButtonModule } from "@angular/material/button";
import { MatCommonModule } from "@angular/material/core";
import { MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule, MatIcon } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatRadioModule } from "@angular/material/radio";
import { MatSelectModule } from "@angular/material/select";
import { MatTableModule } from "@angular/material/table";
import { MatTabsModule } from "@angular/material/tabs";
import { MatTooltipModule } from "@angular/material/tooltip";
import { FuseWidgetModule } from "@fuse/components";
import { ShareDialog } from "./dashboard-volunteer/share-dialog/share-dialog.component";
import { DashboardVolunteerComponent } from "./dashboard-volunteer/dashboard-volunteer.component";
import {
  MatBadgeModule,
  MatPaginatorModule,
  MatSortModule,
  MatCard,
  MatCardModule
} from "@angular/material";
import { OrganisationFilterModule } from "app/main/content/_components/_shared/organisation-filter/organisation-filter.module";
import { TenantOverviewComponent } from "./tenant-overview/tenant-overview.component";
import { FuseTruncatePipeModule } from "app/main/content/_pipe/truncate-pipe.module";
import { HeaderModule } from "app/main/content/_components/_shared/header/header.module";
import { HighchartsChartModule } from "highcharts-angular";
import { DialogFactoryModule } from "app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.module";
import { DashboardHelpSeekerTenantAdminComponent } from "./dashboard-helpseeker-tenantAdmin/dashboard-helpseeker-tenantAdmin.component";

const routes = [
  {
    path: "",
    component: DashboardComponent
  },
  {
    path: "tenants",
    component: TenantOverviewComponent
  }
];

@NgModule({
  declarations: [
    DashboardVolunteerComponent,
    DashboardHelpSeekerTenantAdminComponent,
    DashboardComponent,
    TenantOverviewComponent,
    ShareDialog
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,
    OrganisationFilterModule,
    HeaderModule,
    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatTabsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCommonModule,
    MatDividerModule,
    MatMenuModule,
    MatTooltipModule,
    MatDialogModule,
    MatRadioModule,
    MatProgressSpinnerModule,
    MatBadgeModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    FuseTruncatePipeModule,
    HighchartsChartModule,

    DialogFactoryModule,

    FuseSharedModule,
    FuseWidgetModule
  ],
  exports: [DashboardComponent],
  entryComponents: [ShareDialog]
})
export class FuseDashboardModule {}
