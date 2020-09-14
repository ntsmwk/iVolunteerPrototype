import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddClassDefinitionGraphDialogComponent } from './add-class-definition-graph-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule, MatProgressSpinnerModule, MatDialogModule } from '@angular/material';

import { FuseSharedModule } from '@fuse/shared.module';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatButtonModule,
    MatIconModule,
    MatDialogModule,

    MatProgressSpinnerModule,

    FuseSharedModule,

  ],
  declarations: [AddClassDefinitionGraphDialogComponent],
  entryComponents: [
    AddClassDefinitionGraphDialogComponent,
  ],
  exports: [AddClassDefinitionGraphDialogComponent]
})
export class AddClassDefinitionGraphDialogModule { }
