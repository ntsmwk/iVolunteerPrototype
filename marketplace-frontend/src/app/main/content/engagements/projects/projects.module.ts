import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTruncatePipeModule} from '../../_pipe/truncate-pipe.module';
import {FuseWidgetModule} from '../../../../../@fuse/components';

import {MatButtonModule, MatDividerModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSidenavModule} from '@angular/material';

import {FuseProjectsComponent} from './projects.component';
import {FuseProjectMemberComponent} from './sidenavs/project-member/project-member.component';
import {FuseProjectTaskListComponent} from './sidenavs/project-task-list/project-task-list.component';

@NgModule({
  declarations: [
    FuseProjectsComponent,
    FuseProjectMemberComponent,
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

    FuseSharedModule,
    FuseTruncatePipeModule,
    FuseWidgetModule
  ],
  exports: [FuseProjectsComponent]
})
export class FuseProjectsModule {
}
