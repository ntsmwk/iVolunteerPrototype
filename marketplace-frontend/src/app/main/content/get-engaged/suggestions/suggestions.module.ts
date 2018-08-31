import {NgModule} from '@angular/core';
import {FuseSuggestionsComponent} from './suggestions.component';
import {FuseSharedModule} from '../../../../../@fuse/shared.module';
import {FuseWidgetModule} from '../../../../../@fuse/components';
                                                                                                                                                        

@NgModule({

  declarations: [
    FuseSuggestionsComponent
  ],
  imports: [
    FuseSharedModule,
    FuseWidgetModule
  ],
  exports: [
    FuseSuggestionsComponent
  ]
})

export class FuseSuggestionsModule {
}
