import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SaveClassConfigurationAsDialogComponent } from './save-as-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    FormsModule,
    ReactiveFormsModule,

    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,

    MatDividerModule,


  ],
  declarations: [SaveClassConfigurationAsDialogComponent],
  exports: [SaveClassConfigurationAsDialogComponent]
})
export class SaveClassConfigurationAsDialogModule { }
