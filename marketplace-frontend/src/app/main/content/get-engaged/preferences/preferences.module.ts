import {NgModule} from '@angular/core';
import {FuseSharedModule} from '../../../../../@fuse/shared.module';
import {FuseWidgetModule} from '../../../../../@fuse/components';
import {FusePreferencesComponent} from './preferences.component';
import {MatCheckboxModule, MatDividerModule, MatFormFieldModule, MatInputModule, MatRadioModule, MatSlideToggleModule} from '@angular/material';


@NgModule({

  declarations: [
    FusePreferencesComponent
  ],
  imports: [
    MatRadioModule,
    MatDividerModule,
    MatCheckboxModule,
    MatSlideToggleModule,
    MatFormFieldModule,
    MatInputModule,
    FuseSharedModule,
    FuseWidgetModule
  ],
  exports: [
    FusePreferencesComponent
  ]
})

export class FusePreferencesModule {
}
