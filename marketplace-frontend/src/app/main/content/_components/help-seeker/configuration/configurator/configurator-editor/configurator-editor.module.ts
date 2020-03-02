import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfiguratorEditorComponent } from './configurator-editor.component';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule, MatOptionModule } from '@angular/material/core';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FuseTruncatePipeModule } from 'app/main/content/_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { AddOrRemoveDialogComponent } from 'app/main/content/_shared_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.component';
import { AddOrRemoveDialogModule } from 'app/main/content/_shared_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.module';
import { DialogFactoryModule } from 'app/main/content/_shared_components/dialogs/_dialog-factory/dialog-factory.module';
import { EditorTopMenuBarModule } from './top-menu-bar/top-menu-bar.module';
import { DataTransportService } from '../../../../../_service/data-transport/data-transport.service';
import { EditorTreeViewModule } from './tree-view/tree-view.module';



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
    EditorTreeViewModule,

  ],
  declarations: [ConfiguratorEditorComponent],
  exports: [ConfiguratorEditorComponent],
  entryComponents:[AddOrRemoveDialogComponent],



})



export class ConfiguratorEditorModule { }
