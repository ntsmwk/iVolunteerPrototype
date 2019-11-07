import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SaveAsDialogComponent } from './save-as-dialog.component';
import { MatTableModule, MatCommonModule, MatDialogModule, MatButtonModule, MatCheckboxModule, MatInputModule, MatFormFieldModule, MatIconModule } from '@angular/material';

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
  declarations: [SaveAsDialogComponent],
  exports: [SaveAsDialogComponent]
})
export class SaveAsDialogModule { }
