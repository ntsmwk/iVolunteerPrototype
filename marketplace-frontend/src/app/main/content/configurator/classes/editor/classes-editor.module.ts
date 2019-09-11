import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassesEditorComponent } from './classes-editor.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatButtonModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatTooltipModule, MatDividerModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { AddOrRemoveDialogComponent } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.component';
import { AddOrRemoveDialogModule } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.module';
import { DialogFactoryModule } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.module';
import { OpenDialogComponent } from './open-dialog/open-dialog.component';
import { OpenDialogModule } from './open-dialog/open-dialog.module';


// const routes = [
//   {path: ':open', component: ClassesEditorComponent}
// ];



@NgModule({
  imports: [
    CommonModule,
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,
    MatFormFieldModule,

    // RouterModule.forChild(routes),

    MatDividerModule,

    MatSelectModule,
    MatOptionModule,
    MatTooltipModule,


    FuseSharedModule,
    FuseTruncatePipeModule,

    DialogFactoryModule,
    AddOrRemoveDialogModule,
    OpenDialogModule

  ],
  declarations: [ClassesEditorComponent],
  exports: [ClassesEditorComponent],
  entryComponents:[AddOrRemoveDialogComponent, OpenDialogComponent]


})



export class ClassesEditorModule { }
