import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseTaskDetailComponent} from './task-detail.component';
import {FuseTaskHierarchyComponent} from './sidenavs/hierarchy/task-hierarchy.component';
import {FuseTaskTimelineComponent} from './sidenavs/timeline/task-timeline.component';

import {MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSidenavModule} from '@angular/material';

const routes = [
  {path: ':marketplaceId/:taskId', component: FuseTaskDetailComponent}
];

@NgModule({
  declarations: [
    FuseTaskDetailComponent,
    FuseTaskHierarchyComponent,
    FuseTaskTimelineComponent,
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

    FuseSharedModule
  ]
})
export class FuseTaskDetailModule {
}
