import {NgModule} from '@angular/core';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTimelineTasksComponent} from './timeline-tasks.component';

@NgModule({
  declarations: [
    FuseTimelineTasksComponent
  ],
  imports: [
    FuseSharedModule
  ],
  exports: [
    FuseTimelineTasksComponent
  ]
})
export class FuseTimelineTasksModule {
}
