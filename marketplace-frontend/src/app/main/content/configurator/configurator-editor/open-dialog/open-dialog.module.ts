import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OpenDialogComponent } from './open-dialog.component';
import { MatTableModule, MatCommonModule, MatDialogModule, MatButtonModule, MatCheckboxModule, MatInputModule, MatFormFieldModule, MatIconModule } from '@angular/material';
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
    MatIconModule,

    
  ],
  declarations: [OpenDialogComponent],
  exports: [OpenDialogComponent]
})
export class OpenDialogModule { }
