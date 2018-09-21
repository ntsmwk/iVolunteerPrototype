import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {MatTabsModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseCalendarModule} from './calendar/calendar.module';
import {FuseProjectsModule} from './projects/projects.module';
import {FuseEngagementsComponent} from './engagements.component';
import { CollaborationsComponent } from './collaborations/collaborations.component';
import { ContributionsComponent } from './contributions/contributions.component';
import { EncouragementsComponent } from './encouragements/encouragements.component';
import { OpportunitiesComponent } from './opportunities/opportunities.component';

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
    OpportunitiesComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatTabsModule,

    FuseSharedModule,
    FuseCalendarModule,
    FuseProjectsModule
  ]
})

export class FuseEngagementsModule {
}
