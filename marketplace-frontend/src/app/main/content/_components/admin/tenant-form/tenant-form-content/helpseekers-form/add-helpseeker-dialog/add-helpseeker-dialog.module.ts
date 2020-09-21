import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule, MatInputModule, MatFormFieldModule, MatProgressSpinnerModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { AddHelpseekerDialogComponent } from './add-helpseeker-dialog.component';

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

    FuseSharedModule,

  ],
  declarations: [AddHelpseekerDialogComponent],
  entryComponents: [AddHelpseekerDialogComponent],
  exports: [AddHelpseekerDialogComponent]
})
export class AddHelpseekerDialogModule { }
