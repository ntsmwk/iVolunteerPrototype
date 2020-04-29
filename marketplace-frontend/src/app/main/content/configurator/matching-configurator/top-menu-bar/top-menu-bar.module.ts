import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchingTopMenuBarComponent } from './top-menu-bar.component';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FuseTruncatePipeModule } from 'app/main/content/_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { DialogFactoryModule } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.module';
import { ConfirmDialogComponent } from 'app/main/content/_components/dialogs/confirm-dialog/confirm-dialog.component';
import { ConfirmDialogModule } from 'app/main/content/_components/dialogs/confirm-dialog/confirm-dialog.module';

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
