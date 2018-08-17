import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseGetEngagedComponent} from './get-engaged.component';

const routes: Routes = [
  {path: '', component: FuseGetEngagedComponent},
  {path: 'task', loadChildren: '../task-detail/task-detail.module#FuseTaskDetailModule'}
  /*,{path: 'marketplace', loadChildren: '../marketplace-detail/marketplace-detail.module#FuseMarketplaceDetailModule'}*/
];

@NgModule({

  declarations: [
    FuseGetEngagedComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    FuseSharedModule
  ]
})

export class FuseGetEngagedModule {
}
