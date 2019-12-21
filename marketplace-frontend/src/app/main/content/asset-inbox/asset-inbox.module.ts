import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { AssetInboxComponent } from './asset-inbox.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material';
import { RouterModule } from '@angular/router';

const routes = [
  { path: '', component: AssetInboxComponent }
];

@NgModule({
  declarations: [
    AssetInboxComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),       
    FuseSharedModule,
    MatButtonModule,
  
  ],
  exports: [
    AssetInboxComponent
  ]
})

export class AssetInboxModule {
}
