import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseGetEngagedComponent} from './get-engaged.component';
import {MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSidenavModule} from '@angular/material';
import {FuseMarketplacesModule} from './marketplaces/marketplaces.module';
import {FuseWidgetModule} from '../../../../@fuse/components';

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

    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,

    FuseMarketplacesModule,
    FuseSharedModule,
    FuseWidgetModule
  ]
})

export class FuseGetEngagedModule {
}
