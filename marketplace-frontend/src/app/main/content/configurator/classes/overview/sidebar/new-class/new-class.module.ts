import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarNewClassComponent } from './new-class.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatButtonModule, MatInputModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DialogFactoryModule } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.module';
import { AddOrRemoveDialogComponent } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.component';
import { AddOrRemoveDialogModule } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.module';
import { RelationshipDialogComponent } from 'app/main/content/_components/dialogs/relationship-dialog/relationship-dialog.component';
import { RelationshipDialogModule } from 'app/main/content/_components/dialogs/relationship-dialog/relationship-dialog.module';

@NgModule({
  imports: [
    CommonModule,

    ReactiveFormsModule,
    
    MatCommonModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

    DialogFactoryModule,
    AddOrRemoveDialogModule,
    RelationshipDialogModule,
    

  ],
  declarations: [SidebarNewClassComponent],
  exports: [SidebarNewClassComponent],
  entryComponents:[AddOrRemoveDialogComponent, RelationshipDialogComponent]

})



export class SidebarNewClassModule { }
