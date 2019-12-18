import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { AssetInboxComponent } from './asset-inbox.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material';


@NgModule({
  declarations: [
    AssetInboxComponent
  ],
  imports: [
    CommonModule,
    FuseSharedModule,
    MatButtonModule,
  
  ],
  exports: [
    AssetInboxComponent
  ]
})

export class AssetInboxModule {
}
