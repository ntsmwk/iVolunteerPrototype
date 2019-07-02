import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SortDialogComponent } from './sort-dialog.component';
import { MatTableModule, MatCommonModule, MatDialogModule, MatButtonModule, MatCheckboxModule, MatCardModule } from '@angular/material';
import { DragulaModule } from 'ng2-dragula';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatTableModule,
    MatDialogModule,
    MatButtonModule,
    DragulaModule,
    MatCardModule,

    
  ],
  declarations: [SortDialogComponent],
  exports: [SortDialogComponent]
})
export class SortDialogModule { }
