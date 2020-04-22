import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPropertyDialogComponent } from './add-property-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule, MatInputModule, MatFormFieldModule, MatProgressSpinnerModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatTableModule,
    MatDialogModule,
    MatButtonModule,
    MatCheckboxModule,
    MatIconModule,

    MatFormFieldModule,
    MatInputModule,

    MatProgressSpinnerModule,

  ],
  declarations: [AddPropertyDialogComponent],
  exports: [AddPropertyDialogComponent]
})
export class AddPropertyDialogModule { }
