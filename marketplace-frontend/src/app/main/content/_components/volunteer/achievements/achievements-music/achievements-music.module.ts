import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AchievementsMusicComponent } from './achievements-music.component';
import { RouterModule } from '@angular/router';
import { TasksMusicComponent } from './tasks/tasks-music.component';
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
import { FuseConfirmDialogModule, FuseWidgetModule } from '../../../../../../../@fuse/components';
import { FuseProjectTaskListModule } from '../../../../_shared_components/project-task-list/project-task-list.module';
import { FuseTruncatePipeModule } from '../../../../_pipe/truncate-pipe.module';
import { NgbModalModule } from '@ng-bootstrap/ng-bootstrap';
import { FuseProjectMembersModule } from '../../../../_shared_components/project-members/project-members.module';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { HighchartsChartModule } from 'highcharts-angular';
import { ShareMenuModule } from '../share-menu/share-menu.module';
import { CompetenciesMusicComponent } from './competencies/competencies-music.component';
const routes = [
  { path: '', component: AchievementsMusicComponent }
];

@NgModule({
  declarations: [
    AchievementsMusicComponent,
    TasksMusicComponent,
    CompetenciesMusicComponent
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
    HighchartsChartModule,
    ShareMenuModule
  ]
})
export class AchievementsMusicModule { }
