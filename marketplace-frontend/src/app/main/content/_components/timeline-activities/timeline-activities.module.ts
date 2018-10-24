import {NgModule} from '@angular/core';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTimelineActivitiesComponent} from './timeline-activities.component';

@NgModule({
  declarations: [
    FuseTimelineActivitiesComponent
  ],
  imports: [
    FuseSharedModule
  ],
  exports: [
    FuseTimelineActivitiesComponent
  ]
})
export class FuseTimelineActivitiesModule {
}
