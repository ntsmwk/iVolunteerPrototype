import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowseSubDialogComponent } from './browse-sub-dialog.component';
import { MatCommonModule } from '@angular/material/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatTooltipModule, MatIconModule } from '@angular/material';
import { ConfirmDialogModule } from 'app/main/content/_components/dialogs/confirm-dialog/confirm-dialog.module';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatButtonModule,
    MatTooltipModule,
    MatIconModule,


    FuseSharedModule,
    ConfirmDialogModule,
  ],

  declarations: [BrowseSubDialogComponent],
  exports: [BrowseSubDialogComponent],


})



export class BrowseSubDialogModule { }
