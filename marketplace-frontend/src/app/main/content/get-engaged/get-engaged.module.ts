import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseGetEngagedComponent} from './get-engaged.component';
import {MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSidenavModule, MatTabsModule} from '@angular/material';
import {FuseWidgetModule} from '../../../../@fuse/components';
import {FuseSuggestionsModule} from './suggestions/suggestions.module';
import {FusePreferencesModule} from './preferences/preferences.module';
import { FuseSharedModule } from '../../../../@fuse/shared.module';

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
    MatTabsModule,

    FuseSharedModule,
    FuseWidgetModule,
    FuseSuggestionsModule,
    FusePreferencesModule
  ]
})

export class FuseGetEngagedModule {
}
