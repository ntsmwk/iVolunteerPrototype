import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassesEditorComponent } from './classes-editor.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatButtonModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { AddOrRemoveDialogComponent } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.component';
import { AddOrRemoveDialogModule } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.module';
import { DialogFactoryModule } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.module';



@NgModule({
  imports: [
    CommonModule,
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,


    FuseSharedModule,
    FuseTruncatePipeModule,

    DialogFactoryModule,
    AddOrRemoveDialogModule,

  ],
  declarations: [ClassesEditorComponent],
  exports: [ClassesEditorComponent],
  entryComponents:[AddOrRemoveDialogComponent]


})



export class ClassesEditorModule { }
