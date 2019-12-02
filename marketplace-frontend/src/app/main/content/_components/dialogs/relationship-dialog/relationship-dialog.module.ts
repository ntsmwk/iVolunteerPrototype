import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RelationshipDialogComponent } from './relationship-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule, MatOptionModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
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
