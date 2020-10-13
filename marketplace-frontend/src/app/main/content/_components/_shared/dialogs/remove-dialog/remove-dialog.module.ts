import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RemovePropertyDialogComponent } from './remove-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule, MatInputModule, MatFormFieldModule, MatProgressSpinnerModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';

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
  declarations: [RemovePropertyDialogComponent],
  exports: [RemovePropertyDialogComponent]
})
export class RemovePropertyDialogModule { }
