import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OpenMatchingDialogComponent } from './open-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { BrowseMatchingSubDialogModule } from 'app/main/content/_components/configuration/matching-configurator/_dialogs/browse-sub-dialog/browse-sub-dialog.module';

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

    BrowseMatchingSubDialogModule,


  ],
  declarations: [OpenMatchingDialogComponent],
  exports: [OpenMatchingDialogComponent]
})
export class OpenMatchingDialogModule { }
