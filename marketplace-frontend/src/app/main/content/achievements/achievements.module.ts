import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {
  MatButtonModule, MatDividerModule, MatExpansionModule, MatIconModule, MatMenuModule, MatOptionModule, MatSelectModule, MatSidenavModule,
  MatTabsModule,
  MatListModule,
  MatProgressBarModule
} from '@angular/material';
import {FuseAchievementsComponent} from './achievements.component';
import {FuseWidgetModule} from '../../../../@fuse/components';
import { CollaborationsComponent } from './collaborations/collaborations.component';
import { ContributionsComponent } from './contributions/contributions.component';
import { EncouragementsComponent } from './encouragements/encouragements.component';
import { OpportunitiesComponent } from './opportunities/opportunities.component';

const routes = [
  {path: '**', component: FuseAchievementsComponent}
];

@NgModule({
  declarations: [
    FuseAchievementsComponent,
    CollaborationsComponent,
    ContributionsComponent,
    EncouragementsComponent,
    OpportunitiesComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    MatProgressBarModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatListModule,
    MatOptionModule,
    MatSelectModule,
    MatExpansionModule,
    MatSidenavModule,
    MatDividerModule,
    MatTabsModule,
    FuseWidgetModule,

    FuseSharedModule
  ]
})

export class FuseAchievementsModule {
}
