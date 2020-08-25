import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowseMatchingSubDialogComponent } from './browse-sub-dialog.component';
import { MatCommonModule } from '@angular/material/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatTooltipModule, MatIconModule } from '@angular/material';
import { ConfirmDialogModule } from 'app/main/content/_components/_shared/dialogs/confirm-dialog/confirm-dialog.module';

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

  declarations: [BrowseMatchingSubDialogComponent],
  exports: [BrowseMatchingSubDialogComponent],


})



export class BrowseMatchingSubDialogModule { }
