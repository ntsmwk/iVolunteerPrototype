import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NewClassConfigurationDialogComponent } from './new-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { FormsModule } from '@angular/forms';
import { MatTooltipModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MatCommonModule,
    MatDialogModule,
    MatButtonModule,
    MatCheckboxModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
    MatTooltipModule,

  ],
  declarations: [NewClassConfigurationDialogComponent],
  exports: [NewClassConfigurationDialogComponent]
})
export class NewClassConfigurationDialogModule { }
