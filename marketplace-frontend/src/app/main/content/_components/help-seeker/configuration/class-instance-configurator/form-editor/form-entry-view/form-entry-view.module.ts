import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormEntryViewComponent } from './form-entry-view.component';
import { MatCommonModule, MatProgressSpinnerModule, MatExpansionModule, MatButtonModule, MatIconModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { DynamicClassInstanceCreationFormModule } from 'app/main/content/_components/_shared/dynamic-forms/dynamic-class-instance-creation-form/dynamic-class-instance-creation-form.module';


@NgModule({
  imports: [
    CommonModule,


    MatCommonModule,
    MatProgressSpinnerModule,
    MatExpansionModule,

    MatButtonModule,
    MatIconModule,

    FuseSharedModule,
    DynamicClassInstanceCreationFormModule,

  ],
  declarations: [FormEntryViewComponent],
  exports: [FormEntryViewComponent]


})



export class FormEntryViewModule { }
