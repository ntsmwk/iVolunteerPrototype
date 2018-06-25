import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseSampleComponent} from './sample.component';

const routes = [
  {path: '', component: FuseSampleComponent}
];

@NgModule({
  declarations: [
    FuseSampleComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    FuseSharedModule
  ],
  exports: [
    FuseSampleComponent
  ]
})

export class FuseSampleModule {
}
