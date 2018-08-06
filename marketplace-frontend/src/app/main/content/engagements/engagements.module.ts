import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseEngagementsComponent} from './engagements.component';
import {MatButtonModule, MatDividerModule, MatIconModule, MatTabsModule} from '@angular/material';
import {FuseCalendarModule} from './calendar/calendar.module';

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

    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    MatTabsModule,

    FuseCalendarModule,

    FuseSharedModule
  ]
})

export class FuseEngagementsModule {
}
