import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditorTopMenuBarComponent } from './top-menu-bar.component';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FuseTruncatePipeModule } from 'app/main/content/_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { DialogFactoryModule } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.module';
import { ConfirmDialogComponent } from 'app/main/content/_components/_shared/dialogs/confirm-dialog/confirm-dialog.component';
import { ConfirmDialogModule } from 'app/main/content/_components/_shared/dialogs/confirm-dialog/confirm-dialog.module';
import { MatButtonModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatInputModule,
    MatMenuModule,
    MatButtonModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

    DialogFactoryModule,
    ConfirmDialogModule,
  ],

  declarations: [EditorTopMenuBarComponent],
  exports: [EditorTopMenuBarComponent],
  entryComponents: [ConfirmDialogComponent]


})



export class EditorTopMenuBarModule { }
