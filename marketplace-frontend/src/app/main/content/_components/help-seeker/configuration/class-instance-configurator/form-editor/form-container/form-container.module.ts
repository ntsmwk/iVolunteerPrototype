import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormContainerComponent } from './form-container.component';
import { MatCommonModule, MatProgressSpinnerModule, MatExpansionModule, MatButtonModule, MatIconModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { DynamicFormBlockModule } from 'app/main/content/_components/_shared/dynamic-forms/dynamic-form-block/dynamic-form-block.module';


@NgModule({
  imports: [
    CommonModule,


    MatCommonModule,
    MatProgressSpinnerModule,
    MatExpansionModule,

    MatButtonModule,
    MatIconModule,

    FuseSharedModule,
    DynamicFormBlockModule,

  ],
  declarations: [FormContainerComponent],
  exports: [FormContainerComponent]


})



export class FormContainerModule { }
