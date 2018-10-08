import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseDashboardComponent} from './dashboard.component';
import {MatButtonModule, MatDividerModule, MatExpansionModule, MatIconModule, MatMenuModule, MatOptionModule, MatSelectModule, MatSidenavModule} from '@angular/material';
import {FuseWidgetModule} from '../../../../@fuse/components';
import {DynamicModule} from 'ng-dynamic-component';
import {GridsterModule} from 'angular-gridster2';

const routes = [
  {path: '', component: FuseDashboardComponent}
];

@NgModule({
  declarations: [
    FuseDashboardComponent
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
    DynamicModule.withComponents([]),

    FuseSharedModule
  ]
})

export class FuseDashboardModule {
}
