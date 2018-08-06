import {NgModule} from '@angular/core';
import {FuseSharedModule} from '@fuse/shared.module';
import {MatButtonModule, MatDividerModule, MatIconModule} from '@angular/material';
import {FuseCalendarComponent} from './calendar.component';

@NgModule({

  declarations: [
    FuseCalendarComponent
  ],
  imports: [
    MatButtonModule,
    MatDividerModule,
    MatIconModule,

    FuseSharedModule
  ],
  exports: [
    FuseCalendarComponent
  ]
})

export class FuseCalendarModule {
}
