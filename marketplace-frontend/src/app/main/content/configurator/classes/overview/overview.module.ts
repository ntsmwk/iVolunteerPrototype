import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassesOverviewComponent } from './overview.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatButtonModule, MatTooltipModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { SidebarNewClassModule } from './sidebar/new-class/new-class.module';
import { SidebarNodeModule } from "./sidebar/node/node.module";
import { SidebarEdgeModule } from "./sidebar/edge/edge.module";
import { SidebarDeleteModule } from './sidebar/delete/delete.module';


@NgModule({
  imports: [
    CommonModule,
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTableModule,

    MatButtonModule,
    MatTooltipModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

    SidebarNewClassModule,
    SidebarNodeModule,
    SidebarEdgeModule,
    SidebarDeleteModule,

  ],
  declarations: [ClassesOverviewComponent],
  exports: [ClassesOverviewComponent]

})



export class ClassesOverviewModule { }
