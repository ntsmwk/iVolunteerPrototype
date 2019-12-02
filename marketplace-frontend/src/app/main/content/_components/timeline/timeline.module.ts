import {NgModule} from '@angular/core';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTimelineComponent} from './timeline.component';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';

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
