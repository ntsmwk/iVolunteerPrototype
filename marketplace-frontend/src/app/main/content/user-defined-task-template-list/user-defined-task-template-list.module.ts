import { NgModule } from '@angular/core';
import { RouterModule, Route } from '@angular/router';

import { CommonModule } from '@angular/common';
import { FuseUserDefinedTaskTemplateListComponent } from './user-defined-task-template-list.component';
import { MatTableModule, MatIconModule, MatButtonModule, MatProgressSpinnerModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FuseTruncatePipeModule } from '../_pipe/truncate-pipe.module';


const routes: Route[] = [
  {path: '', component: FuseUserDefinedTaskTemplateListComponent}
];

@NgModule({
  declarations: [FuseUserDefinedTaskTemplateListComponent],
  imports: [
    RouterModule.forChild(routes),
    
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,

    FuseSharedModule,
    FuseTruncatePipeModule
  ]
})
export class FuseUserDefinedTaskTemplateListModule { }
