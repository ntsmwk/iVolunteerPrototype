import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatInputModule, MatMenuModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FuseTruncatePipeModule } from 'app/main/content/_pipe/truncate-pipe.module';
import { DialogFactoryModule } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.module';
import { ConfirmDialogModule } from 'app/main/content/_components/_shared/dialogs/confirm-dialog/confirm-dialog.module';
import { ConfirmDialogComponent } from 'app/main/content/_components/_shared/dialogs/confirm-dialog/confirm-dialog.component';
import { MatchingTopMenuBarComponent } from './top-menu-bar.component';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

    DialogFactoryModule,
    ConfirmDialogModule,
  ],

  declarations: [MatchingTopMenuBarComponent],
  exports: [MatchingTopMenuBarComponent],
  entryComponents: [ConfirmDialogComponent]


})
export class MatchingEditorTopMenuBarModule { }
