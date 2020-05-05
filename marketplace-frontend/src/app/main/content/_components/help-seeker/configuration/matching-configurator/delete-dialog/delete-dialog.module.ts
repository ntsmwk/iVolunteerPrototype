import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeleteMatchingDialogComponent } from './delete-dialog.component';
import { MatCommonModule } from '@angular/material/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatTooltipModule, MatIconModule, MatCheckboxModule, MatDialogModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,

    MatDialogModule,

    MatButtonModule,

    MatTooltipModule,
    MatIconModule,
    MatCheckboxModule,


    FuseSharedModule,
  ],

  declarations: [DeleteMatchingDialogComponent],
  exports: [DeleteMatchingDialogComponent],


})



export class DeleteMatchingDialogModule { }
