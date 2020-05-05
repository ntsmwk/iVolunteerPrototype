import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyCreationDialogComponent } from './property-creation-dialog.component';
import {
  MatCommonModule, MatProgressSpinnerModule, MatButtonModule, MatDialogModule, MatIconModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';


@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    FuseSharedModule,
    MatDialogModule,

    MatProgressSpinnerModule,
    MatButtonModule,
    MatIconModule,

  ],
  declarations: [PropertyCreationDialogComponent],
  exports: [PropertyCreationDialogComponent]


}) export class PropertyCreationDialogModule { }
