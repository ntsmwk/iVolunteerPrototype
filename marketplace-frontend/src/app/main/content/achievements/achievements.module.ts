import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {
  MatButtonModule, MatDividerModule, MatExpansionModule, MatIconModule, MatMenuModule, MatOptionModule, MatSelectModule, MatSidenavModule,
  MatTabsModule,
  MatListModule,
  MatProgressBarModule, MatSlideToggleModule, MatFormFieldModule, MatToolbarModule, MatInputModule, MatDatepickerModule
} from '@angular/material';
import {FuseAchievementsComponent} from './achievements.component';
import {FuseConfirmDialogModule, FuseWidgetModule} from '../../../../@fuse/components';
import {CollaborationsComponent} from './collaborations/collaborations.component';
import {ContributionsComponent} from './contributions/contributions.component';
import {EncouragementsComponent} from './encouragements/encouragements.component';
import {OpportunitiesComponent} from './opportunities/opportunities.component';
import {FuseProjectTaskListModule} from '../@shared/project-task-list/project-task-list.module';
import {FuseTruncatePipeModule} from '../_pipe/truncate-pipe.module';
import {NgbModalModule} from '@ng-bootstrap/ng-bootstrap';
import {FuseProjectMembersModule} from '../@shared/project-members/project-members.module';
import {ColorPickerModule} from 'ngx-color-picker';
import {CalendarModule} from 'angular-calendar';

const routes = [
  {path: '**', component: FuseAchievementsComponent}
];

@NgModule({
  declarations: [
    FuseAchievementsComponent,
    CollaborationsComponent,
    ContributionsComponent,
    EncouragementsComponent,
    OpportunitiesComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    RouterModule,

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
    
    FuseProjectMembersModule,
    FuseProjectTaskListModule,
    FuseTruncatePipeModule,
    FuseSharedModule,
    FuseConfirmDialogModule,
    FuseWidgetModule,

    NgbModalModule
  ]
})

export class FuseAchievementsModule {
}
