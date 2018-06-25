import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseTaskDetailComponent} from './task-detail.component';
import {FuseTaskHierarchyComponent} from './sidenavs/hierarchy/task-hierarchy.component';
import {FuseTaskTimelineComponent} from './sidenavs/timeline/task-timeline.component';

import {MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSidenavModule} from '@angular/material';
import {MessageService} from '../_service/message.service';
import {ReactiveFormsModule} from '@angular/forms';

const routes: Route[] = [
  {
    path: '',
    runGuardsAndResolvers: 'paramsChange',
    component: FuseTaskDetailComponent
  }
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
  ],
  providers: [MessageService],
  exports: [
    FuseTaskDetailComponent
  ]
})
export class FuseTaskDetailModule {
}
