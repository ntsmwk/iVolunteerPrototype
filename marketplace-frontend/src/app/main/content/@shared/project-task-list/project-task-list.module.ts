import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatDividerModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule, MatProgressBarModule, MatSidenavModule} from '@angular/material';
import {FuseProjectTaskListComponent} from './project-task-list.component';
import {FuseTruncatePipeModule} from '../../_pipe/truncate-pipe.module';
import {FuseWidgetModule} from '../../../../../@fuse/components/index';


@NgModule({
  declarations: [
    FuseProjectTaskListComponent
  ],
  imports: [
    RouterModule,

    MatButtonModule,
    MatDividerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatProgressBarModule,

    FuseSharedModule,
    FuseTruncatePipeModule,
    FuseWidgetModule
  ],
  exports: [FuseProjectTaskListComponent]
})
export class FuseProjectTaskListModule {
}
