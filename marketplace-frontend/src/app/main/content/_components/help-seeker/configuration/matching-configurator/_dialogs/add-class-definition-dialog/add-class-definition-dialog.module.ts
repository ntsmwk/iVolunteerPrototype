import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddClassDefinitionDialogComponent } from './add-class-definition-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule, MatInputModule, MatFormFieldModule, MatProgressSpinnerModule, MatDialogModule } from '@angular/material';

import { FuseSharedModule } from '@fuse/shared.module';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatTableModule,
    MatButtonModule,
    MatCheckboxModule,
    MatIconModule,
    MatDialogModule,

    MatFormFieldModule,
    MatInputModule,

    MatProgressSpinnerModule,


    FuseSharedModule,

  ],
  declarations: [AddClassDefinitionDialogComponent],
  entryComponents: [AddClassDefinitionDialogComponent],
  exports: [AddClassDefinitionDialogComponent]
})
export class AddClassDefinitionDialogModule { }
