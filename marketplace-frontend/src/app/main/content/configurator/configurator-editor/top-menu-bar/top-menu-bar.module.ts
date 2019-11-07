import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditorTopMenuBarComponent } from './top-menu-bar.component';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatInputModule, MatMenuModule } from '@angular/material';
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

  declarations: [EditorTopMenuBarComponent],
  exports: [EditorTopMenuBarComponent],
  entryComponents:[ConfirmDialogComponent]


})



export class EditorTopMenuBarModule { }
