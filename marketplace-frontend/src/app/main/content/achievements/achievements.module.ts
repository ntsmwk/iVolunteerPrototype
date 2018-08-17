import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatDividerModule, MatExpansionModule, MatIconModule, MatMenuModule, MatOptionModule, MatSelectModule, MatSidenavModule} from '@angular/material';
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
    MatExpansionModule,
    MatSidenavModule,
    MatDividerModule,
    FuseWidgetModule,

    FuseSharedModule
  ]
})

export class FuseAchievementsModule {
}
