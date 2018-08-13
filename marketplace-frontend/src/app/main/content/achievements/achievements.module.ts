import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatIconModule, MatMenuModule, MatOptionModule, MatSelectModule} from '@angular/material';
import {FuseAchievementsComponent} from './achievements.component';
import {FuseWidgetModule} from '../../../../@fuse/components';

const routes = [
  {path: '**', component: FuseAchievementsComponent}
];

@NgModule({
  declarations: [
    FuseAchievementsComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatOptionModule,
    MatSelectModule,
    FuseWidgetModule,

    FuseSharedModule
  ]
})

export class FuseAchievementsModule {
}
