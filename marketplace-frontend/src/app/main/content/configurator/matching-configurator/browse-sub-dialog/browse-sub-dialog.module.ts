import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowseSubDialogComponent } from './browse-sub-dialog.component';
import { MatCommonModule } from '@angular/material/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatTooltipModule, MatIconModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatButtonModule,
    MatTooltipModule,
    MatIconModule,

    FuseSharedModule,
  ],

  declarations: [BrowseSubDialogComponent],
  exports: [BrowseSubDialogComponent],


})



export class BrowseSubDialogModule { }
