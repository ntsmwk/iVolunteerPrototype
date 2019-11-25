import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseEngagementsComponent} from './engagements.component';
import {CollaborationsComponent} from './collaborations/collaborations.component';
import {ContributionsComponent} from './contributions/contributions.component';
import {EncouragementsComponent} from './encouragements/encouragements.component';
import {OpportunitiesComponent} from './opportunities/opportunities.component';
import {FuseProjectMembersModule} from '../_components/project-members/project-members.module';
import {FuseProjectTaskListModule} from '../_components/project-task-list/project-task-list.module';
import {FuseTruncatePipeModule} from '../_pipe/truncate-pipe.module';
import {CalendarModule} from 'angular-calendar';
import {ColorPickerModule} from 'ngx-color-picker';
import {NgbModalModule} from '@ng-bootstrap/ng-bootstrap';
import {FuseConfirmDialogModule, FuseWidgetModule} from '../../../../@fuse/components';
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

    MatTabsModule,
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
    MatMenuModule,

    CalendarModule.forRoot(),
    ColorPickerModule,

    FuseProjectMembersModule,
    FuseProjectTaskListModule,
    FuseTruncatePipeModule,
    FuseSharedModule,
    FuseConfirmDialogModule,
    FuseWidgetModule,

    NgbModalModule
  ]
})

export class FuseEngagementsModule {
}
