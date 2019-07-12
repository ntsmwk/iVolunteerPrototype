import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassesListComponent } from './classes-list.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';

@NgModule({
  imports: [
    CommonModule,
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTableModule,


    FuseSharedModule,
    FuseTruncatePipeModule,

  ],
  declarations: [ClassesListComponent],
  exports: [ClassesListComponent]

})



export class ClassesListModule { }
