import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { InboxOverlayComponent } from './inbox-overlay.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule, MatTableModule } from '@angular/material';


@NgModule({
  declarations: [
    InboxOverlayComponent
  ],
  imports: [
    CommonModule,
    FuseSharedModule,
    MatButtonModule,

    MatTableModule,
        
  
  ],
  exports: [
    InboxOverlayComponent
  ]
})

export class InboxOverlayModule {
}
