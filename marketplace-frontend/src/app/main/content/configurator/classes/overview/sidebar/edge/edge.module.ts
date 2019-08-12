import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarEdgeComponent } from './edge.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatButtonModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';

@NgModule({
  imports: [
    CommonModule,
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTableModule,

    MatButtonModule,


    FuseSharedModule,
    FuseTruncatePipeModule,

  ],
  declarations: [SidebarEdgeComponent],
  exports: [SidebarEdgeComponent]

})



export class SidebarEdgeModule { }
