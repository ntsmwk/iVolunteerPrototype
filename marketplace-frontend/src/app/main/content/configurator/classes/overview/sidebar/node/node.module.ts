import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarNodeComponent } from './node.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatButtonModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { DialogFactoryModule } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.module';
import { RelationshipDialogModule } from 'app/main/content/_components/dialogs/relationship-dialog/relationship-dialog.module';
import { RelationshipDialogComponent } from 'app/main/content/_components/dialogs/relationship-dialog/relationship-dialog.component';
import { AddOrRemoveDialogComponent } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.component';
import { AddOrRemoveDialogModule } from 'app/main/content/_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.module';

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
    RelationshipDialogModule,

  ],
  declarations: [SidebarNodeComponent],
  exports: [SidebarNodeComponent],
  entryComponents:[AddOrRemoveDialogComponent, RelationshipDialogComponent]

})



export class SidebarNodeModule { }
