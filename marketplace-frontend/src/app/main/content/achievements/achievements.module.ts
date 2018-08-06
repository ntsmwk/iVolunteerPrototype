import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatIconModule, MatMenuModule} from '@angular/material';
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
    FuseWidgetModule,

    FuseSharedModule
  ]
})

export class FuseAchievementsModule {
}
