import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule } from '@angular/material/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatTooltipModule, MatIconModule, MatDialogModule, MatCheckboxModule } from '@angular/material';
import { DeleteTreePropertyDefinitionDialogComponent } from './delete-tree-property-definition-dialog.component';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatButtonModule,
    MatTooltipModule,
    MatIconModule,
    MatDialogModule,
    MatCheckboxModule,
    FuseSharedModule,

  ],

  declarations: [DeleteTreePropertyDefinitionDialogComponent],
  exports: [DeleteTreePropertyDefinitionDialogComponent],
})



export class DeleteTreePropertyDefinitionDialogModule { }
