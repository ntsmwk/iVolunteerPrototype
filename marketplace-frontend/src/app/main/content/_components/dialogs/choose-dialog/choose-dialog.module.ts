import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChooseTemplateToCopyDialogComponent } from './choose-dialog.component';
import { MatCommonModule, MatTableModule, MatButtonModule, MatDialogModule, MatRadioModule, MatStepperModule, MatFormFieldModule, MatInputModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,


    ReactiveFormsModule,

    MatFormFieldModule,
    MatInputModule,

    MatTableModule,
    MatDialogModule,
    MatButtonModule,
    MatRadioModule,

    MatStepperModule,
    
  ],
  declarations: [ChooseTemplateToCopyDialogComponent],
  exports: [ChooseTemplateToCopyDialogComponent]
})
export class ChooseTemplateToCopyDialogModule { }
