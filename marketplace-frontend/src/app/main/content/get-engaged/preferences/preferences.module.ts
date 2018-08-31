import {NgModule} from '@angular/core';
import {FuseSharedModule} from '../../../../../@fuse/shared.module';
import {FuseWidgetModule} from '../../../../../@fuse/components';
import {FusePreferencesComponent} from './preferences.component';
import {FuseSuggestionsComponent} from '../suggestions/suggestions.component';


@NgModule({

  declarations: [
    FusePreferencesComponent
  ],
  imports: [
    FuseSharedModule,
    FuseWidgetModule
  ],
  exports: [
    FusePreferencesComponent
  ]
})

export class FusePreferencesModule {
}
