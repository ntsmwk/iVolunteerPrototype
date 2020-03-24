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
  MatCheckboxModule
} from "@angular/material";
import {
  FuseConfirmDialogModule,
  FuseWidgetModule
} from "../../../../../../../@fuse/components";
import { FuseProjectTaskListModule } from "../../../../_shared_components/project-task-list/project-task-list.module";
import { FuseTruncatePipeModule } from "../../../../_pipe/truncate-pipe.module";
import { NgbModalModule } from "@ng-bootstrap/ng-bootstrap";
import { FuseProjectMembersModule } from "../../../../_shared_components/project-members/project-members.module";

import { NgxChartsModule } from "@swimlane/ngx-charts";
import { HighchartsChartModule } from "highcharts-angular";
import { CommonModule } from "@angular/common";
import { AchievementsFireBrigadeComponent } from "./achievement-fire-brigade.component";
import { TasksComponent } from "./tasks/tasks.component";
import { FunctionsComponent } from "./functions/functions.component";
import { AccomplishmentsComponent } from "./accomplishments/accomplishments.component";
import { CompetenciesComponent } from "./competencies/competencies.component";
import { ShareMenuModule } from "./share-menu/share-menu.module";
import { FlexLayoutModule } from '@angular/flex-layout';
import { SunburstTableComponent } from './tasks/sunburst-table/sunburst-table.component';
import { TimelineFilterComponent } from './tasks/timeline-filter/timeline-filter.component';
import { DonutComponent } from './tasks/donut/donut.component';

const routes = [{ path: "", component: AchievementsFireBrigadeComponent }];

@NgModule({
  declarations: [
    AchievementsFireBrigadeComponent,
    TasksComponent,
    FunctionsComponent,
    AccomplishmentsComponent,
    CompetenciesComponent,

    DonutComponent,
    SunburstTableComponent,
    TimelineFilterComponent
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

    FuseProjectMembersModule,
    FuseProjectTaskListModule,
    FuseTruncatePipeModule,
    FuseSharedModule,
    FuseConfirmDialogModule,
    FuseWidgetModule,

    NgbModalModule,
    FlexLayoutModule,

    NgxChartsModule,
    HighchartsChartModule,
    ShareMenuModule,
  ]
})
export class AchievementsFireBrigadeModule { }
