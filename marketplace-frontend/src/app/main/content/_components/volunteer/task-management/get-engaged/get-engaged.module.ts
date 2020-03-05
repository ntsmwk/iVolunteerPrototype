import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseGetEngagedComponent} from './get-engaged.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatRadioModule } from '@angular/material/radio';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTabsModule } from '@angular/material/tabs';
import {FuseWidgetModule} from '../../../../../../../@fuse/components';
import {FuseSharedModule} from '../../../../../../../@fuse/shared.module';
import {RecommendationsComponent} from './recommendations/recommendations.component';
import {SearchComponent} from './search/search.component';
import {SuggestionsComponent} from './suggestions/suggestions.component';
import {PreferencesComponent} from './preferences/preferences.component';
import {FuseProjectTaskListModule} from '../../../../_shared_components/project-task-list/project-task-list.module';
import {FuseTruncatePipeModule} from '../../../../_pipe/truncate-pipe.module';

const routes: Routes = [
  {path: '', component: FuseGetEngagedComponent},
  {path: 'task',   loadChildren: () => import(`../../../help-seeker/task-management/task-detail/task-detail.module`).then(m => m.FuseTaskDetailModule)}
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
