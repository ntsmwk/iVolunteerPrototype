import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { ConfirmClassConfigurationSaveDialogComponent } from 'app/main/content/configurator/class-configurator/confirm-save-dialog/confirm-save-dialog.component';

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
  declarations: [ConfirmClassConfigurationSaveDialogComponent],
  exports: [ConfirmClassConfigurationSaveDialogComponent]
})
export class ConfirmClassConfigurationSaveDialogModule { }
