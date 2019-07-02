import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SortDialogComponent } from './sort-dialog.component';
import { MatTableModule, MatCommonModule, MatDialogModule, MatButtonModule, MatCheckboxModule } from '@angular/material';
//import {DragDropModule} from '@angular/cdk/drag-drop'; 

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatTableModule,
    MatDialogModule,
    MatButtonModule,
    MatCheckboxModule,
    //DragDropModule,
    
  ],
  declarations: [SortDialogComponent],
  exports: [SortDialogComponent]
})
export class SortDialogModule { }
