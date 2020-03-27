import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowseSubDialogComponent } from './browse-sub-dialog.component';
import { MatCommonModule } from '@angular/material/core';
import { FuseSharedModule } from '@fuse/shared.module';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,

    FuseSharedModule,
  ],

  declarations: [BrowseSubDialogComponent],
  exports: [BrowseSubDialogComponent],


})



export class BrowseSubDialogModule { }
