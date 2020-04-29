import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormPreviewDialogComponent } from './form-preview-dialog.component';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatExpansionModule, MatCardModule, MatSlideToggleModule, MatDatepicker, MatDatepickerModule, MatDividerModule, MatButtonModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FormEntryViewModule } from 'app/main/content/configurator/class-instances/form-editor/form-entry-view/form-entry-view.module';


@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatProgressSpinnerModule,
    MatExpansionModule,

    MatIconModule,
    MatButtonModule,

    FuseSharedModule,
    FormEntryViewModule,
  ],
  declarations: [ClassInstanceFormPreviewDialogComponent],
  exports: [ClassInstanceFormPreviewDialogComponent]


}) export class ClassInstanceFormPreviewDialogModule { }
