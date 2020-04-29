import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchingConfiguratorComponent } from './matching-configurator.component';
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
<<<<<<< HEAD:marketplace-frontend/src/app/main/content/_components/help-seeker/configuration/configurator/configurator-editor/configurator-editor.module.ts
import { AddOrRemoveDialogComponent } from 'app/main/content/_shared_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.component';
import { AddOrRemoveDialogModule } from 'app/main/content/_shared_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.module';
import { DialogFactoryModule } from 'app/main/content/_shared_components/dialogs/_dialog-factory/dialog-factory.module';
import { EditorTopMenuBarModule } from './top-menu-bar/top-menu-bar.module';
import { DataTransportService } from '../../../../../_service/data-transport/data-transport.service';
import { EditorTreeViewModule } from './tree-view/tree-view.module';

=======
import { AddOrRemoveDialogModule } from 'app/main/content/_components/dialogs/deprecrated-add-or-remove-dialog/add-or-remove-dialog.module';
import { DialogFactoryModule } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.module';
import { MatchingEditorTopMenuBarModule } from './top-menu-bar/top-menu-bar.module';
import { MatchingOptionsOverlayControlModule } from './options-overlay/options-overlay-control/options-overlay-control.module';
import { MatCheckboxModule } from '@angular/material';
>>>>>>> flexprod_master:marketplace-frontend/src/app/main/content/configurator/matching-configurator/matching-configurator.module.ts

const routes = [
  { path: '', component: MatchingConfiguratorComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),

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
    MatCheckboxModule,


    FuseSharedModule,
    FuseTruncatePipeModule,

    DialogFactoryModule,
    AddOrRemoveDialogModule,

    MatchingEditorTopMenuBarModule,
    MatchingOptionsOverlayControlModule,

  ],
  declarations: [MatchingConfiguratorComponent],



})



export class MatchingConfiguratorModule { }
