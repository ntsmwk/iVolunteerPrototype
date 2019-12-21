import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { InboxOverlayComponent } from './inbox-overlay.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule, MatTableModule, MatTooltipModule } from '@angular/material';


@NgModule({
  declarations: [
    InboxOverlayComponent
  ],
  imports: [
    CommonModule,
    FuseSharedModule,
    MatButtonModule,

    MatTableModule,
    MatTooltipModule,
        
  
  ],
  exports: [
    InboxOverlayComponent
  ]
})

export class InboxOverlayModule {
}
