import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChooseTemplateToCopyDialogComponent } from './choose-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table';
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
