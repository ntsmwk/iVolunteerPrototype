import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyCreationDialogComponent } from './property-creation-dialog.component';
import {
  MatCommonModule, MatProgressSpinnerModule
} from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';


@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatProgressSpinnerModule,

    FuseSharedModule,

  ],
  declarations: [PropertyCreationDialogComponent],
  exports: [PropertyCreationDialogComponent]


}) export class PropertyCreationDialogModule { }
