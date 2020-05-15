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
  MatCardModule,
} from "@angular/material";
import { AssetInboxHelpseekerModule } from "../../help-seeker/asset-inbox-helpseeker/asset-inbox-helpseeker.module";
import { AssetInboxModule } from "../../../_shared_components/asset-inbox/asset-inbox.module";
import { DashboardHelpSeekerComponent } from "./dashboard-helpseeker/dashboard-helpseeker.component";
import { OrganisationFilterModule } from "app/main/content/_shared_components/organisation-filter/organisation-filter.module";
import { TenantOverviewComponent } from "./tenant-overview/tenant-overview.component";
import { FuseTruncatePipeModule } from "app/main/content/_pipe/truncate-pipe.module";
import { HeaderModule } from "app/main/content/_shared_components/header/header.module";

const routes = [
  {
    path: "",
    component: DashboardComponent,
  },
  {
    path: "tenants",
    component: TenantOverviewComponent,
  },
];

@NgModule({
  declarations: [
    DashboardVolunteerComponent,
    DashboardHelpSeekerComponent,
    DashboardComponent,
    TenantOverviewComponent,
    ShareDialog,
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,
    AssetInboxModule,
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

    FuseSharedModule,
    FuseWidgetModule,
  ],
  exports: [DashboardComponent],
  entryComponents: [ShareDialog],
})
export class FuseDashboardModule {}
