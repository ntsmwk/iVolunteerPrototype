import {NgModule} from '@angular/core';
import {FuseSharedModule} from '@fuse/shared.module';
import {MatButtonModule, MatDatepickerModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSlideToggleModule, MatToolbarModule} from '@angular/material';
import {FuseCalendarComponent} from './calendar.component';
import {CalendarModule} from 'angular-calendar';
import {ColorPickerModule} from 'ngx-color-picker';
import {FuseConfirmDialogModule} from '../../../../../@fuse/components';

@NgModule({

  declarations: [
    FuseCalendarComponent
  ],
  imports: [
    MatButtonModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSlideToggleModule,
    MatToolbarModule,

    CalendarModule.forRoot(),
    ColorPickerModule,

    FuseSharedModule,
    FuseConfirmDialogModule
  ],
  exports: [
    FuseCalendarComponent
  ]
})

export class FuseCalendarModule {
}
