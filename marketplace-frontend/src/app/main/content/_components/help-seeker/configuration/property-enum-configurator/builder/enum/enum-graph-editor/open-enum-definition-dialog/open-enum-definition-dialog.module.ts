import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OpenEnumDefinitionDialogComponent } from './open-enum-definition-dialog.component';
import { MatCommonModule } from '@angular/material/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatTooltipModule, MatIconModule, MatDialogModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatButtonModule,
    MatTooltipModule,
    MatIconModule,
    MatDialogModule,
    FuseSharedModule,
  ],

  declarations: [OpenEnumDefinitionDialogComponent],
  exports: [OpenEnumDefinitionDialogComponent],
})



export class OpenEnumDefinitionDialogModule { }
