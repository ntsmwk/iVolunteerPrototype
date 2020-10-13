import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeleteClassConfigurationDialogComponent } from './delete-dialog.component';
import { MatCommonModule } from '@angular/material/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatTooltipModule, MatIconModule, MatCheckboxModule, MatDialogModule, MatFormFieldModule, MatInputModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatDialogModule,
    MatButtonModule,

    MatFormFieldModule,
    MatInputModule,
    MatTooltipModule,
    MatIconModule,
    MatCheckboxModule,


    FuseSharedModule,
  ],

  declarations: [DeleteClassConfigurationDialogComponent],
  exports: [DeleteClassConfigurationDialogComponent],


})



export class DeleteClassConfigurationDialogModule { }
