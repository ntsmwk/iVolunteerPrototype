import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddOrRemoveDialogComponent } from './add-or-remove-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';

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
