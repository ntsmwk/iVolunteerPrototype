import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmDialogModule } from '../confirm-dialog/confirm-dialog.module';
import { NewClassConfigurationDialogModule } from '../../../configuration/class-configurator/_dialogs/new-dialog/new-dialog.module';
import { OpenClassConfigurationDialogModule } from '../../../configuration/class-configurator/_dialogs/open-dialog/open-dialog.module';
import { ConfirmClassConfigurationSaveDialogModule } from '../../../configuration/class-configurator/_dialogs/confirm-save-dialog/confirm-save-dialog.module';
import { SaveClassConfigurationAsDialogModule } from '../../../configuration/class-configurator/_dialogs/save-as-dialog/save-as-dialog.module';
import { DeleteClassConfigurationDialogModule } from '../../../configuration/class-configurator/_dialogs/delete-dialog/delete-dialog.module';
import { ClassInstanceFormPreviewDialogModule } from '../../../configuration/class-instance-configurator/form-preview-dialog/form-preview-dialog.module';
import { ChangeIconDialogModule } from '../../../configuration/class-configurator/_dialogs/icon-dialog/icon-dialog.module';
import { ClassInstanceFormPreviewExportDialogModule } from '../../../configuration/class-instance-configurator/form-preview-export-dialog/form-preview-export-dialog.module';
import { AddPropertyDialogModule } from '../add-property-dialog/add-property-dialog.module';
import { RemovePropertyDialogModule } from '../remove-dialog/remove-dialog.module';
import { PropertyCreationDialogModule } from '../../../configuration/class-configurator/_dialogs/property-creation-dialog/property-creation-dialog.module';
import { NewMatchingDialogModule } from '../../../configuration/matching-configurator/_dialogs/new-dialog/new-dialog.module';
import { OpenMatchingDialogModule } from '../../../configuration/matching-configurator/_dialogs/open-dialog/open-dialog.module';
import { DeleteMatchingDialogModule } from '../../../configuration/matching-configurator/_dialogs/delete-dialog/delete-dialog.module';
import { DialogFactoryDirective } from './dialog-factory.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { NewClassConfigurationDialogComponent } from '../../../configuration/class-configurator/_dialogs/new-dialog/new-dialog.component';
import { OpenClassConfigurationDialogComponent } from '../../../configuration/class-configurator/_dialogs/open-dialog/open-dialog.component';
import { ConfirmClassConfigurationSaveDialogComponent } from '../../../configuration/class-configurator/_dialogs/confirm-save-dialog/confirm-save-dialog.component';
import { SaveClassConfigurationAsDialogComponent } from '../../../configuration/class-configurator/_dialogs/save-as-dialog/save-as-dialog.component';
import { ClassInstanceFormPreviewDialogComponent } from '../../../configuration/class-instance-configurator/form-preview-dialog/form-preview-dialog.component';
import { DeleteClassConfigurationDialogComponent } from '../../../configuration/class-configurator/_dialogs/delete-dialog/delete-dialog.component';
import { ChangeIconDialogComponent } from '../../../configuration/class-configurator/_dialogs/icon-dialog/icon-dialog.component';
import { ClassInstanceFormPreviewExportDialogComponent } from '../../../configuration/class-instance-configurator/form-preview-export-dialog/form-preview-export-dialog.component';
import { AddPropertyDialogComponent } from '../add-property-dialog/add-property-dialog.component';
import { RemovePropertyDialogComponent } from '../remove-dialog/remove-dialog.component';
import { PropertyCreationDialogComponent } from '../../../configuration/class-configurator/_dialogs/property-creation-dialog/property-creation-dialog.component';
import { NewMatchingDialogComponent } from '../../../configuration/matching-configurator/_dialogs/new-dialog/new-dialog.component';
import { OpenMatchingDialogComponent } from '../../../configuration/matching-configurator/_dialogs/open-dialog/open-dialog.component';
import { DeleteMatchingDialogComponent } from '../../../configuration/matching-configurator/_dialogs/delete-dialog/delete-dialog.component';
import { AddClassDefinitionDialogModule } from '../../../configuration/matching-configurator/_dialogs/add-class-definition-dialog/add-class-definition-dialog.module';
import { AddClassDefinitionDialogComponent } from '../../../configuration/matching-configurator/_dialogs/add-class-definition-dialog/add-class-definition-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    // AddOrRemoveDialogModule,
    ConfirmDialogModule,

    // Class Configurator
    NewClassConfigurationDialogModule,
    OpenClassConfigurationDialogModule,
    ConfirmClassConfigurationSaveDialogModule,
    SaveClassConfigurationAsDialogModule,
    DeleteClassConfigurationDialogModule,
    ClassInstanceFormPreviewDialogModule,
    ChangeIconDialogModule,
    ClassInstanceFormPreviewExportDialogModule,

    AddPropertyDialogModule,
    RemovePropertyDialogModule,

    PropertyCreationDialogModule,

    // Matching Configurator
    NewMatchingDialogModule,
    OpenMatchingDialogModule,
    DeleteMatchingDialogModule,
    AddClassDefinitionDialogModule,

    // Tree Property Configurator

  ],
  declarations: [DialogFactoryDirective],
  entryComponents: [
    // AddOrRemoveDialogComponent,
    ConfirmDialogComponent,

    // Class Configurator
    NewClassConfigurationDialogComponent,
    OpenClassConfigurationDialogComponent,
    ConfirmClassConfigurationSaveDialogComponent,
    SaveClassConfigurationAsDialogComponent,
    ClassInstanceFormPreviewDialogComponent,
    DeleteClassConfigurationDialogComponent,
    ChangeIconDialogComponent,
    ClassInstanceFormPreviewExportDialogComponent,

    AddPropertyDialogComponent,
    RemovePropertyDialogComponent,

    PropertyCreationDialogComponent,

    // Matching Configurator
    NewMatchingDialogComponent,
    OpenMatchingDialogComponent,
    DeleteMatchingDialogComponent,
    AddClassDefinitionDialogComponent,

    // Tree Property Configurator

  ]
})
export class DialogFactoryModule { }
