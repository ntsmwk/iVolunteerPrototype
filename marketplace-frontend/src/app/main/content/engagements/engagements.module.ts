import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {CalendarComponent} from './calendar/calendar.component';
import {FuseEngagementsComponent} from './engagements.component';
import {MatButtonModule, MatDividerModule, MatIconModule, MatTabsModule} from '@angular/material';
import {FuseProjectsComponent} from './projects/projects.component';

const routes: Routes = [
  {
    path: '**', component: FuseEngagementsComponent
  }
];

@NgModule({

  declarations: [
    CalendarComponent,
    FuseProjectsComponent,
    FuseEngagementsComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    MatTabsModule,

    FuseSharedModule
  ]
})

export class FuseEngagementsModule {
}
