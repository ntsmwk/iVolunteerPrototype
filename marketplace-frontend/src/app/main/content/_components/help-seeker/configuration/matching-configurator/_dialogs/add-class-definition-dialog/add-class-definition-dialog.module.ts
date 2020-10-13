import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddClassDefinitionDialogComponent } from './add-class-definition-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule, MatInputModule, MatFormFieldModule, MatProgressSpinnerModule, MatDialogModule } from '@angular/material';

import { FuseSharedModule } from '@fuse/shared.module';
import { AddClassDefinitionGraphDialogModule } from '../add-class-definition-graph-dialog/add-class-definition-graph-dialog.module';

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
    AddClassDefinitionGraphDialogModule,

    FuseSharedModule,

  ],
  declarations: [AddClassDefinitionDialogComponent],
  entryComponents: [AddClassDefinitionDialogComponent],
  exports: [AddClassDefinitionDialogComponent]
})
export class AddClassDefinitionDialogModule { }
