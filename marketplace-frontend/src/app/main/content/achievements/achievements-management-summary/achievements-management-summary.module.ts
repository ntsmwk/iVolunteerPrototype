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
import { CommonModule } from '@angular/common';
import { AchievementsManagementSummaryComponent } from './achievements-management-summary.component';
import { ShareMenuComponent } from '../share-menu/share-menu.component';
import { ShareMenuModule } from '../share-menu/share-menu.module';

const routes = [
  { path: '', component: AchievementsManagementSummaryComponent }
];

@NgModule({
  declarations: [
    AchievementsManagementSummaryComponent,
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
    
  ],
  providers   : [
]
})

export class AchievementsManagementSummary {
}