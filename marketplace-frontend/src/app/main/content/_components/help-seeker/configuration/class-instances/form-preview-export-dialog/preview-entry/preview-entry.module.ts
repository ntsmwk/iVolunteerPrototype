import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormPreviewEntryComponent } from './preview-entry.component';
import { MatCommonModule, MatProgressSpinnerModule, MatExpansionModule, MatButtonModule, MatIconModule, MatFormFieldModule, MatSelectModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { DynamicClassInstanceCreationFormModule } from 'app/main/content/_components/_shared_components/dynamic-forms/dynamic-class-instance-creation-form/dynamic-class-instance-creation-form.module';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,

    MatCommonModule,
    MatProgressSpinnerModule,
    MatExpansionModule,

    MatButtonModule,
    MatIconModule,

    FuseSharedModule,
    DynamicClassInstanceCreationFormModule,

  ],
  declarations: [FormPreviewEntryComponent],
  exports: [FormPreviewEntryComponent]


})



export class FormPreviewEntryModule { }
