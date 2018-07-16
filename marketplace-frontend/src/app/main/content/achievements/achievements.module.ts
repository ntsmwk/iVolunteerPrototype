import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatIconModule} from '@angular/material';
import {FuseAchievementsComponent} from './achievements.component';

const routes = [
  {path: '', component: FuseAchievementsComponent}
];

@NgModule({
  declarations: [
    FuseAchievementsComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    MatIconModule,
    FuseSharedModule
  ]
})

export class FuseAchievementsModule {
}
