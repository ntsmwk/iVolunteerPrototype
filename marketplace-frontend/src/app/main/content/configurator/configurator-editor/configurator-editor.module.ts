import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfiguratorEditorComponent } from './configurator-editor.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatButtonModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatTooltipModule, MatDividerModule, MatSnackBarModule } from '@angular/material';
import { FuseTruncatePipeModule } from 'app/main/content/_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { AddOrRemoveDialogComponent } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.component';
import { AddOrRemoveDialogModule } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.module';
import { DialogFactoryModule } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.module';
import { EditorTopMenuBarModule } from './top-menu-bar/top-menu-bar.module';



@NgModule({
  imports: [
    CommonModule,
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,

    MatDividerModule,

    MatSelectModule,
    MatOptionModule,
    MatTooltipModule,
    MatSnackBarModule,


    FuseSharedModule,
    FuseTruncatePipeModule,

    DialogFactoryModule,
    AddOrRemoveDialogModule,

    EditorTopMenuBarModule,

  ],
  declarations: [ConfiguratorEditorComponent],
  exports: [ConfiguratorEditorComponent],
  entryComponents:[AddOrRemoveDialogComponent]


})



export class ConfiguratorEditorModule { }
