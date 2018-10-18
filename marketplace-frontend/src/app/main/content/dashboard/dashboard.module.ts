import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseDashboardComponent} from './dashboard.component';
import {MatButtonModule, MatDividerModule, MatExpansionModule, MatIconModule, MatMenuModule, MatOptionModule, MatSelectModule, MatSidenavModule} from '@angular/material';
import {FuseWidgetModule} from '../../../../@fuse/components';
import {DynamicModule} from 'ng-dynamic-component';
import {GridsterModule} from 'angular-gridster2';
import {FuseDashletComponent} from './dashlet.component';
import {FuseDashletsSelectorComponent} from './dashlets-selector.component';

import {FuseProjectMembersModule} from '../_components/project-members/project-members.module';
import {FuseProjectMembersComponent} from '../_components/project-members/project-members.component';

const routes = [
  {path: '', component: FuseDashboardComponent}
];

@NgModule({
  declarations: [
    FuseDashletComponent,
    FuseDashboardComponent,
    FuseDashletsSelectorComponent
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

    GridsterModule,
    FuseProjectMembersModule,
    DynamicModule.withComponents([]),

    FuseSharedModule
  ],
  entryComponents: [
    FuseProjectMembersComponent
  ]
})

export class FuseDashboardModule {
}
