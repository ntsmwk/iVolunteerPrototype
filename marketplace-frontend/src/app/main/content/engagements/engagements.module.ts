import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {CalendarComponent} from './calendar/calendar.component';
import {FuseEngagementsComponent} from './engagements.component';
import {MatTabsModule} from '@angular/material';
import {FuseProjectsModule} from './projects/projects.module';

const routes: Routes = [
  {
    path: '**', component: FuseEngagementsComponent
  }
];

@NgModule({

  declarations: [
    CalendarComponent,
    FuseEngagementsComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatTabsModule,

    FuseSharedModule,
    FuseProjectsModule
  ]
})

export class FuseEngagementsModule {
}
