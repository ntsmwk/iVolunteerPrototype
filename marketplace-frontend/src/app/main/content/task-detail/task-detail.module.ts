import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseTaskDetailComponent} from './task-detail.component';
import {FuseTaskTimelineComponent} from './sidenavs/timeline/task-timeline.component';
import {FuseTaskAssignComponent} from './assign/task-assign.component';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatSidenavModule,
  MatTableModule
} from '@angular/material';

const routes = [
  {path: ':marketplaceId/:taskId', component: FuseTaskDetailComponent}
];

@NgModule({
  declarations: [
    FuseTaskDetailComponent,
    FuseTaskTimelineComponent,
    FuseTaskAssignComponent
  ],
  imports: [
    ReactiveFormsModule,
    RouterModule.forChild(routes),

    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatDividerModule,
    MatTableModule,
    MatCheckboxModule,
    FuseSharedModule
  ]
})
export class FuseTaskDetailModule {
}
