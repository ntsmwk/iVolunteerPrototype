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
  MatCheckboxModule,
  MatTooltipModule,
  MatDialogModule,
  MatProgressSpinnerModule,
} from "@angular/material";
import {
  FuseConfirmDialogModule,
  FuseWidgetModule,
} from "../../../../../../../@fuse/components";
import { FuseTruncatePipeModule } from "../../../../_pipe/truncate-pipe.module";
import { NgbModalModule } from "@ng-bootstrap/ng-bootstrap";

import { NgxChartsModule } from "@swimlane/ngx-charts";
import { HighchartsChartModule } from "highcharts-angular";
import { CommonModule } from "@angular/common";
import { AchievementsComponent } from "./achievement.component";
import { TasksComponent } from "./tasks/tasks.component";
import { FunctionsComponent } from "./functions/functions.component";
import { AccomplishmentsComponent } from "./accomplishments/accomplishments.component";
import { CompetenciesComponent } from "./competencies/competencies.component";
import { ShareMenuModule } from "./share-menu/share-menu.module";
import { FlexLayoutModule } from "@angular/flex-layout";
import { SunburstTableComponent } from "./tasks/sunburst-table/sunburst-table.component";
import { TimelineFilterComponent } from "./tasks/timeline-filter/timeline-filter.component";
import { DonutComponent } from "./tasks/donut/donut.component";
import { NgxSpinnerModule } from "ngx-spinner";
import { OrganisationFilterModule } from "app/main/content/_components/_shared/organisation-filter/organisation-filter.module";
import { ClassInstanceDetailsComponent } from "../../../common/class-instance-details/class-instance-details.component";
import { ClassInstanceDetailsModule } from "../../../common/class-instance-details/class-instance-details.module";

const routes = [{ path: "", component: AchievementsComponent }];

@NgModule({
  declarations: [
    AchievementsComponent,
    TasksComponent,
    FunctionsComponent,
    AccomplishmentsComponent,
    CompetenciesComponent,

    DonutComponent,
    SunburstTableComponent,
    TimelineFilterComponent,
  ],
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
    MatCardModule,
    MatButtonToggleModule,
    MatPaginatorModule,
    MatSortModule,
    MatChipsModule,
    MatSlideToggleModule,
    MatCheckboxModule,
    MatTableModule,
    MatTooltipModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    FuseTruncatePipeModule,
    FuseSharedModule,
    FuseConfirmDialogModule,
    FuseWidgetModule,

    NgbModalModule,
    FlexLayoutModule,

    NgxChartsModule,
    HighchartsChartModule,
    ShareMenuModule,
    OrganisationFilterModule,

    NgxSpinnerModule,

    ClassInstanceDetailsModule,
  ],
  entryComponents: [ClassInstanceDetailsComponent],
})
export class AchievementsModule {}
