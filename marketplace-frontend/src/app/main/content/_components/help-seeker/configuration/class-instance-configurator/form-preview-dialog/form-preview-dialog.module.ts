import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormPreviewDialogComponent } from './form-preview-dialog.component';
import {
  MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatExpansionModule,
  MatButtonModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FormContainerModule } from '../form-editor/form-container/form-container.module';


@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatProgressSpinnerModule,
    MatExpansionModule,

    MatIconModule,
    MatButtonModule,

    FuseSharedModule,
    FormContainerModule,
  ],
  declarations: [ClassInstanceFormPreviewDialogComponent],
  exports: [ClassInstanceFormPreviewDialogComponent]


}) export class ClassInstanceFormPreviewDialogModule { }