import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { AssetInboxComponent } from './asset-inbox.component';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    AssetInboxComponent
  ],
  imports: [
    CommonModule,
    FuseSharedModule,
  ],
  exports: [
    AssetInboxComponent
  ]
})

export class AssetInboxModule {
}
