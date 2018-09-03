import {NgModule} from '@angular/core';
import {FuseSuggestionsComponent} from './suggestions.component';
import {FuseSharedModule} from '../../../../../@fuse/shared.module';
import {FuseWidgetModule} from '../../../../../@fuse/components';
import {
  MatButtonModule,
  MatDividerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatMenuModule,
  MatRadioModule,
  MatSidenavModule
} from '@angular/material';
import {RouterModule} from '@angular/router';
import {FuseTruncatePipeModule} from '../../_pipe/truncate-pipe.module';
import {FuseProjectTaskListModule} from '../../engagements/projects/sidenavs/project-task-list/project-task-list.module';


@NgModule({

  declarations: [
    FuseSuggestionsComponent
  ],
  imports: [
    MatFormFieldModule,
    MatRadioModule,
    MatInputModule,
    MatMenuModule,
    MatExpansionModule,
    MatSidenavModule,
    MatDividerModule,
    MatIconModule,
    MatButtonModule,

    FuseProjectTaskListModule,
    FuseSharedModule,
    FuseTruncatePipeModule,
    FuseWidgetModule
  ],
  exports: [
    FuseSuggestionsComponent
  ]
})

export class FuseSuggestionsModule {
}
