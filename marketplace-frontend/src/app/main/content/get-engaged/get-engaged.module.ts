import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseGetEngagedComponent} from './get-engaged.component';
import {
  MatButtonModule, MatCheckboxModule,
  MatDividerModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule, MatMenuModule, MatRadioModule, MatSidenavModule, MatSlideToggleModule,
  MatTabsModule
} from '@angular/material';
import {FuseWidgetModule} from '../../../../@fuse/components';
import {FuseSharedModule} from '../../../../@fuse/shared.module';
import {RecommendationsComponent} from './recommendations/recommendations.component';
import {SearchComponent} from './search/search.component';
import {SuggestionsComponent} from './suggestions/suggestions.component';
import {PreferencesComponent} from './preferences/preferences.component';
import {FuseProjectTaskListModule} from '../@shared/project-task-list/project-task-list.module';
import {FuseTruncatePipeModule} from '../_pipe/truncate-pipe.module';

const routes: Routes = [
  {path: '', component: FuseGetEngagedComponent},
  {path: 'task', loadChildren: '../task-detail/task-detail.module#FuseTaskDetailModule'}
  /*,{path: 'marketplace', loadChildren: '../marketplace-detail/marketplace-detail.module#FuseMarketplaceDetailModule'}*/
];

@NgModule({

  declarations: [
    FuseGetEngagedComponent,
    RecommendationsComponent,
    SuggestionsComponent,
    PreferencesComponent,
    SearchComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatTabsModule,
    MatRadioModule,
    MatMenuModule,
    MatDividerModule,
    MatButtonModule,
    MatCheckboxModule,
    MatSlideToggleModule,

    FuseSharedModule,
    FuseWidgetModule,
    FuseProjectTaskListModule,
    FuseTruncatePipeModule

  ]
})

export class FuseGetEngagedModule {
}
