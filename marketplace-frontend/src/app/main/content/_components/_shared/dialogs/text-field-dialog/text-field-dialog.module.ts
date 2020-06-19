import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TextFieldDialogComponent } from './text-field-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
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
