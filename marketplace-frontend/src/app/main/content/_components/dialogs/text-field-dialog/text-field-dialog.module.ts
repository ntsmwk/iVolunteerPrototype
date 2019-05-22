import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TextFieldDialogComponent } from './text-field-dialog.component';
import { MatTableModule, MatCommonModule, MatDialogModule, MatButtonModule, MatCheckboxModule, MatInputModule, MatFormFieldModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatTableModule,
    MatDialogModule,
    MatButtonModule,
    MatCheckboxModule,
    MatInputModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    
  ],
  declarations: [TextFieldDialogComponent],
  exports: [TextFieldDialogComponent]
})
export class TextFieldDialogModule { }
