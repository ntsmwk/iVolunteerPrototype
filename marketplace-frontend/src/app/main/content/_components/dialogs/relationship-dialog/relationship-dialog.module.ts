import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RelationshipDialogComponent } from './relationship-dialog.component';
import { MatTableModule, MatCommonModule, MatDialogModule, MatButtonModule, MatCheckboxModule, MatInputModule, MatFormFieldModule, MatOptionModule, MatSelectModule } from '@angular/material';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,


    
    FormsModule,


    MatSelectModule,
    MatOptionModule,
    MatDialogModule,
    MatButtonModule,
    MatCheckboxModule,
    MatInputModule,
    MatFormFieldModule,
    
  ],
  declarations: [RelationshipDialogComponent],
  exports: [RelationshipDialogComponent]
})
export class RelationshipDialogModule { }
