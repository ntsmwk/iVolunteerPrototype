import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { InboxOverlayComponent } from './inbox-overlay.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material';


@NgModule({
  declarations: [
    InboxOverlayComponent
  ],
  imports: [
    CommonModule,
    FuseSharedModule,
    MatButtonModule,

        
  
  ],
  exports: [
    InboxOverlayComponent
  ]
})

export class InboxOverlayModule {
}
