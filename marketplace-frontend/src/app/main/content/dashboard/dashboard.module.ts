import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseDashboardComponent} from './dashboard.component';

const routes = [
  {path: '', component: FuseDashboardComponent}
];

@NgModule({
  declarations: [
    FuseDashboardComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    FuseSharedModule
  ],
  exports: [
    FuseDashboardComponent
  ]
})

export class FuseDashboardModule {
}
