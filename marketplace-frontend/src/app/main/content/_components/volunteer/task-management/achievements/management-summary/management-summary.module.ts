import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { FuseSharedModule } from "@fuse/shared.module";
import {
  MatButtonModule,
  MatDividerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatOptionModule,
  MatProgressBarModule,
  MatSelectModule,
  MatSidenavModule,
  MatTabsModule,
  MatToolbarModule,
  MatTableModule,
  MatCardModule,
  MatButtonToggleModule,
  MatPaginatorModule,
  MatSortModule,
  MatChipsModule,
  MatSlideToggleModule,
  MatProgressSpinnerModule,
} from "@angular/material";
import {
  FuseConfirmDialogModule,
  FuseWidgetModule,
} from "../../../../../../../../@fuse/components";

import { FuseTruncatePipeModule } from "../../../../../_pipe/truncate-pipe.module";
import { NgbModalModule } from "@ng-bootstrap/ng-bootstrap";

import { NgxChartsModule } from "@swimlane/ngx-charts";
import { CommonModule } from "@angular/common";
import { ManagementSummaryComponent } from "./management-summary.component";
import { ShareMenuComponent } from "../share-menu/share-menu.component";
import { ShareMenuModule } from "../share-menu/share-menu.module";

const routes = [{ path: "", component: ManagementSummaryComponent }];

@NgModule({
  declarations: [ManagementSummaryComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,

    MatProgressBarModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatListModule,
    MatOptionModule,
    MatSelectModule,
    MatExpansionModule,
    MatSidenavModule,
    MatDividerModule,
    MatTabsModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    MatTableModule,
    MatCardModule,
    MatButtonToggleModule,
    MatPaginatorModule,
    MatSortModule,
    MatChipsModule,
    MatSlideToggleModule,
    MatProgressSpinnerModule,

    FuseTruncatePipeModule,
    FuseSharedModule,
    FuseConfirmDialogModule,
    FuseWidgetModule,

    NgbModalModule,

    NgxChartsModule,
    ShareMenuModule,
  ],
  providers: [],
})
export class ManagementSummary {}
