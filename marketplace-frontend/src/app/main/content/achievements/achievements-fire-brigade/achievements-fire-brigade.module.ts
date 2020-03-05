import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FuseSharedModule } from '@fuse/shared.module';
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
  MatSlideToggleModule
} from '@angular/material';
import { FuseConfirmDialogModule, FuseWidgetModule } from '../../../../../@fuse/components';
import { FuseTruncatePipeModule } from '../../_pipe/truncate-pipe.module';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { CommonModule } from '@angular/common';
import { AchievementsFireBrigadeComponent } from '../../_components/volunteer/task-management/achievements/achievements-fire-brigade/achievement-fire-brigade.component';
import { TasksComponent } from '../../_components/volunteer/task-management/achievements/achievements-fire-brigade/tasks/tasks.component';
import { AccomplishmentsComponent } from '../../_components/volunteer/task-management/achievements/achievements-fire-brigade/accomplishments/accomplishments.component';
import { FunctionsComponent } from '../../_components/volunteer/task-management/achievements/achievements-fire-brigade/functions/functions.component';
import { CompetenciesComponent } from '../../_components/volunteer/task-management/achievements/achievements-fire-brigade/competencies/competencies.component';
import { FuseProjectMembersModule } from '../../_shared_components/project-members/project-members.module';
import { FuseProjectTaskListModule } from '../../_shared_components/project-task-list/project-task-list.module';
import { ShareMenuModule } from '../../_components/volunteer/task-management/achievements/share-menu/share-menu.module';
import { TimelineFilterModule } from '../../_components/volunteer/task-management/achievements/timeline-filter/timeline-filter.module';
import { SunburstTableModule } from '../../_components/volunteer/task-management/achievements/sunburst-table/sunburst-table.module';
import { DonutModule } from '../../_components/volunteer/task-management/achievements/donut/donut.module';

const routes = [
  { path: '', component: AchievementsFireBrigadeComponent }
];

@NgModule({
  declarations: [
    AchievementsFireBrigadeComponent,
    TasksComponent,
    FunctionsComponent,
    AccomplishmentsComponent,
    CompetenciesComponent,

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
    MatTableModule,
    MatCardModule,
    MatButtonToggleModule,
    MatPaginatorModule,
    MatSortModule,
    MatChipsModule,
    MatSlideToggleModule,

    FuseProjectMembersModule,
    FuseProjectTaskListModule,
    FuseTruncatePipeModule,
    FuseSharedModule,
    FuseConfirmDialogModule,
    FuseWidgetModule,

    NgbModalModule,

    NgxChartsModule,

    ShareMenuModule,
    TimelineFilterModule,
    SunburstTableModule,
    DonutModule
  ]

})
export class AchievementsFireBrigadeModule { }
