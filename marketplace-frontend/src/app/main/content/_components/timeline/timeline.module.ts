import {NgModule} from '@angular/core';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTimelineComponent} from './timeline.component';
import {MatDividerModule, MatIconModule} from '@angular/material';

@NgModule({
  declarations: [
    FuseTimelineComponent
  ],
  imports: [
    MatDividerModule,
    MatIconModule,

    FuseSharedModule
  ],
  exports: [
    FuseTimelineComponent
  ]
})
export class FuseTimelineModule {
}
