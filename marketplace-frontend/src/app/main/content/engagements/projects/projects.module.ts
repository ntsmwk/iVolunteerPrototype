import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTruncatePipeModule} from '../../_pipe/truncate-pipe.module';
import {FuseWidgetModule} from '../../../../../@fuse/components';

import {MatButtonModule, MatDividerModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule, MatProgressBarModule, MatSidenavModule} from '@angular/material';

import {FuseProjectsComponent} from './projects.component';
import {FuseProjectMembersComponent} from './sidenavs/project-members/project-members.component';
import {FuseProjectTaskListComponent} from './sidenavs/project-task-list/project-task-list.component';
import {FuseProjectTaskListModule} from './sidenavs/project-task-list/project-task-list.module';
import {FuseProjectMembersModule} from './sidenavs/project-members/project-members.module';

@NgModule({
  declarations: [
    FuseProjectsComponent
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

    FuseProjectMembersModule,
    FuseProjectTaskListModule,
    FuseSharedModule,
    FuseTruncatePipeModule
  ],
  exports: [FuseProjectsComponent]
})
export class FuseProjectsModule {
}
