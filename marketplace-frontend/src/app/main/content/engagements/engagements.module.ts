import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {
  MatButtonModule, MatDatepickerModule, MatDividerModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule, MatProgressBarModule, MatSidenavModule,
  MatSlideToggleModule,
  MatTabsModule, MatToolbarModule
} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseEngagementsComponent} from './engagements.component';
import { CollaborationsComponent } from './collaborations/collaborations.component';
import { ContributionsComponent } from './contributions/contributions.component';
import { EncouragementsComponent } from './encouragements/encouragements.component';
import { OpportunitiesComponent } from './opportunities/opportunities.component';
import {FuseProjectMembersModule} from '../@shared/project-members/project-members.module';
import {FuseProjectTaskListModule} from '../@shared/project-task-list/project-task-list.module';
import {FuseTruncatePipeModule} from '../_pipe/truncate-pipe.module';
import {CalendarModule} from 'angular-calendar';
import {ColorPickerModule} from 'ngx-color-picker';
import {NgbModalModule} from '@ng-bootstrap/ng-bootstrap';
import {FuseConfirmDialogModule} from '../../../../@fuse/components';
import {FuseCalendarComponent} from './calendar/calendar.component';

const routes: Routes = [
  {path: '', component: FuseEngagementsComponent},
  {path: 'task', loadChildren: '../task-detail/task-detail.module#FuseTaskDetailModule'}
];

@NgModule({

  declarations: [
    FuseEngagementsComponent,
    CollaborationsComponent,
    ContributionsComponent,
    EncouragementsComponent,
    OpportunitiesComponent,
    FuseCalendarComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    RouterModule,

    MatTabsModule,
    MatExpansionModule,

    MatButtonModule,
    MatDividerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatProgressBarModule,
    MatDatepickerModule,
    MatSlideToggleModule,
    MatToolbarModule,

    CalendarModule.forRoot(),
    ColorPickerModule,

    FuseProjectMembersModule,
    FuseProjectTaskListModule,
    FuseTruncatePipeModule,
    FuseSharedModule,
    FuseConfirmDialogModule,

    NgbModalModule
  ]
})

export class FuseEngagementsModule {
}
