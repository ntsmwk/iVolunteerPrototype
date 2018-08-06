import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {MatTabsModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseCalendarModule} from './calendar/calendar.module';
import {FuseProjectsModule} from './projects/projects.module';
import {FuseEngagementsComponent} from './engagements.component';

const routes: Routes = [
  {
    path: '**', component: FuseEngagementsComponent
  }
];

@NgModule({

  declarations: [
    FuseEngagementsComponent
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
