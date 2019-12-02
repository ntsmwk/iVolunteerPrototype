import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SortDialogComponent } from './sort-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
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
