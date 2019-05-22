import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddOrRemoveDialogComponent } from './add-or-remove-dialog.component';
import { MatTableModule, MatCommonModule, MatDialogModule, MatButtonModule, MatCheckboxModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatTableModule,
    MatDialogModule,
    MatButtonModule,
    MatCheckboxModule,
    
  ],
  declarations: [AddOrRemoveDialogComponent],
  exports: [AddOrRemoveDialogComponent]
})
export class AddOrRemoveDialogModule { }
