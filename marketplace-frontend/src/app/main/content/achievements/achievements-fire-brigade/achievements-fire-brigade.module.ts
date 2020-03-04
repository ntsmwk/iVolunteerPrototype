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


import { FuseProjectTaskListModule } from '../../_components/project-task-list/project-task-list.module';
import { FuseTruncatePipeModule } from '../../_pipe/truncate-pipe.module';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { FuseProjectMembersModule } from '../../_components/project-members/project-members.module';

import { NgxChartsModule } from '@swimlane/ngx-charts';
import { HighchartsChartModule } from 'highcharts-angular';
import { CommonModule } from '@angular/common';
import { AchievementsFireBrigadeComponent } from './achievement-fire-brigade.component';
import { TasksComponent } from './tasks/tasks.component';
import { FunctionsComponent } from './functions/functions.component';
import { AccomplishmentsComponent } from './accomplishments/accomplishments.component';
import { CompetenciesComponent } from './competencies/competencies.component';
import { ShareMenuModule } from '../share-menu/share-menu.module';
import { TimelineFilterModule } from '../timeline-filter/timeline-filter.module';
import { SunburstTableModule } from '../sunburst-table/sunburst-table.module';
import { DonutModule } from '../donut/donut.module';

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
