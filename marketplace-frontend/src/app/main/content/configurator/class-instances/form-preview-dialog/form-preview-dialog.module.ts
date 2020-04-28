import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormPreviewDialogComponent } from './form-preview-dialog.component';
import { RouterModule } from '@angular/router';
import {
  MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatExpansionModule,
  MatFormFieldModule, MatInputModule, MatSelectModule, MatOptionModule, MatCardModule, MatSlideToggleModule,
  MatDatepickerModule, MatDividerModule, MatButtonModule
} from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { DynamicClassInstanceCreationFormModule } from
  'app/main/content/_components/dynamic-forms/dynamic-class-instance-creation-form/dynamic-class-instance-creation-form.module';
import { FormEntryViewModule } from '../form-editor/form-entry-view/form-entry-view.module';


@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatProgressSpinnerModule,
    // MatIconModule,
    // MatTableModule,
    MatExpansionModule,

    // MatFormFieldModule,
    // MatInputModule,
    // MatSelectModule,
    // MatOptionModule,
    // MatCardModule,
    // MatSlideToggleModule,
    // MatDatepickerModule,
    // MatDividerModule,
    MatIconModule,
    MatButtonModule,

    FuseSharedModule,
    // FuseTruncatePipeModule,
    // DynamicClassInstanceCreationFormModule,
    FormEntryViewModule,
  ],
  declarations: [ClassInstanceFormPreviewDialogComponent],
  exports: [ClassInstanceFormPreviewDialogComponent]


}) export class ClassInstanceFormPreviewDialogModule { }
