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
import { FuseAchievementsComponent } from './achievements.component';
import { FuseConfirmDialogModule, FuseWidgetModule } from '../../../../@fuse/components';

import { TasksComponent } from './tasks/tasks.component';
import { AccomplishmentsComponent } from './accomplishments/accomplishments.component';
import { FunctionsComponent } from './functions/functions.component';
import { CompetenciesComponent } from './competencies/competencies.component';

import { FuseProjectTaskListModule } from '../_components/project-task-list/project-task-list.module';
import { FuseTruncatePipeModule } from '../_pipe/truncate-pipe.module';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { FuseProjectMembersModule } from '../_components/project-members/project-members.module';


import { NgxChartsModule } from '@swimlane/ngx-charts';
import { CommonModule } from '@angular/common';
import { HighchartsChartModule } from 'highcharts-angular';
import { ManagementSummaryComponent } from './management-summary/management-summary.component';
import { ShareMenuComponent } from './share-menu/share-menu.component';


const routes = [
  { path: '**', component: FuseAchievementsComponent }
];

@NgModule({
  declarations: [
    FuseAchievementsComponent,
    TasksComponent,
    AccomplishmentsComponent,
    FunctionsComponent,
    CompetenciesComponent,
    ManagementSummaryComponent,
    ShareMenuComponent,
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
    HighchartsChartModule
  ],
  providers   : [
]
})

export class FuseAchievementsModule {
}