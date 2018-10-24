import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseDashboardComponent} from './dashboard.component';
import {
  MatButtonModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatIconModule,
  MatMenuModule,
  MatOptionModule,
  MatSelectModule,
  MatSidenavModule
} from '@angular/material';
import {FuseWidgetModule} from '../../../../@fuse/components';
import {DynamicModule} from 'ng-dynamic-component';
import {GridsterModule} from 'angular-gridster2';
import {FuseDashletComponent} from './dashlet.component';
import {FuseDashletSelectorDialog} from './dashlet-selector.dialog';

import {FuseProjectMembersModule} from '../_components/project-members/project-members.module';
import {FuseProjectMembersComponent} from '../_components/project-members/project-members.component';
import {FuseTimelineModule} from '../_components/timeline/timeline.module';
import {FuseTimelineComponent} from '../_components/timeline/timeline.component';
import {FuseTimelineActivitiesModule} from '../_components/timeline-activities/timeline-activities.module';
import {FuseTimelineActivitiesComponent} from '../_components/timeline-activities/timeline-activities.component';
import {FuseTimelineTasksModule} from '../_components/timeline-tasks/timeline-tasks.module';
import {FuseTimelineTasksComponent} from '../_components/timeline-tasks/timeline-tasks.component';
import { FuseHelpSeekerDashboardComponent } from './dashboard-helpseeker/dashboard-helpseeker.component';

const routes = [
  {path: '', component: FuseDashboardComponent}
];

@NgModule({
  declarations: [
    FuseDashletComponent,
    FuseDashboardComponent,
    FuseDashletSelectorDialog,
    FuseHelpSeekerDashboardComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatIconModule,
    MatMenuModule,
    MatOptionModule,
    MatSelectModule,
    MatSidenavModule,

    FuseProjectMembersModule,
    FuseTimelineModule,
    FuseTimelineActivitiesModule,
    FuseTimelineTasksModule,
    FuseWidgetModule,

    GridsterModule,
    DynamicModule.withComponents([]),

    FuseSharedModule
  ],
  entryComponents: [
    FuseProjectMembersComponent,
    FuseTimelineComponent,
    FuseTimelineActivitiesComponent,
    FuseTimelineTasksComponent,

    FuseDashletSelectorDialog
  ]
})

export class FuseDashboardModule {
}
