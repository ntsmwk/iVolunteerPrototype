import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogFactoryDirective } from './dialog-factory.component';
// import { AddOrRemoveDialogComponent } from '../deprecrated-add-or-remove-dialog/add-or-remove-dialog.component';
// import { AddOrRemoveDialogModule } from '../deprecrated-add-or-remove-dialog/add-or-remove-dialog.module';
import { TextFieldDialogComponent } from '../text-field-dialog/text-field-dialog.component';
import { TextFieldDialogModule } from '../text-field-dialog/text-field-dialog.module';
import { ConfirmDialogModule } from '../confirm-dialog/confirm-dialog.module';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SortDialogComponent } from '../sort-dialog/sort-dialog.component';
import { SortDialogModule } from '../sort-dialog/sort-dialog.module';
import { ChooseTemplateToCopyDialogModule } from '../choose-dialog/choose-dialog.module';
import { ChooseTemplateToCopyDialogComponent } from '../choose-dialog/choose-dialog.component';
import { OpenClassConfigurationDialogComponent } from 'app/main/content/_components/help-seeker/configuration/class-configurator/open-dialog/open-dialog.component';
import { OpenClassConfigurationDialogModule } from 'app/main/content/_components/help-seeker/configuration/class-configurator/open-dialog/open-dialog.module';
import { SaveClassConfigurationAsDialogComponent } from 'app/main/content/_components/help-seeker/configuration/class-configurator/save-as-dialog/save-as-dialog.component';
import { SaveClassConfigurationAsDialogModule } from 'app/main/content/_components/help-seeker/configuration/class-configurator/save-as-dialog/save-as-dialog.module';
import { ChangeIconDialogComponent } from 'app/main/content/_components/help-seeker/configuration/class-configurator/icon-dialog/icon-dialog.component';
import { ChangeIconDialogModule } from 'app/main/content/_components/help-seeker/configuration/class-configurator/icon-dialog/icon-dialog.module';
import { NewMatchingDialogComponent } from 'app/main/content/_components/help-seeker/configuration/matching-configurator/new-dialog/new-dialog.component';
import { NewMatchingDialogModule } from 'app/main/content/_components/help-seeker/configuration/matching-configurator/new-dialog/new-dialog.module';
import { OpenMatchingDialogComponent } from 'app/main/content/_components/help-seeker/configuration/matching-configurator/open-dialog/open-dialog.component';
import { OpenMatchingDialogModule } from 'app/main/content/_components/help-seeker/configuration/matching-configurator/open-dialog/open-dialog.module';
import { DeleteMatchingDialogComponent } from 'app/main/content/_components/help-seeker/configuration/matching-configurator/delete-dialog/delete-dialog.component';
import { DeleteMatchingDialogModule } from 'app/main/content/_components/help-seeker/configuration/matching-configurator/delete-dialog/delete-dialog.module';
import { NewClassConfigurationDialogModule } from 'app/main/content/_components/help-seeker/configuration/class-configurator/new-dialog/new-dialog.module';
import { NewClassConfigurationDialogComponent } from 'app/main/content/_components/help-seeker/configuration/class-configurator/new-dialog/new-dialog.component';
import { ConfirmClassConfigurationSaveDialogModule } from 'app/main/content/_components/help-seeker/configuration/class-configurator/confirm-save-dialog/confirm-save-dialog.module';
import { ConfirmClassConfigurationSaveDialogComponent } from 'app/main/content/_components/help-seeker/configuration/class-configurator/confirm-save-dialog/confirm-save-dialog.component';
import { DeleteClassConfigurationDialogModule } from 'app/main/content/_components/help-seeker/configuration/class-configurator/delete-dialog/delete-dialog.module';
import { DeleteClassConfigurationDialogComponent } from 'app/main/content/_components/help-seeker/configuration/class-configurator/delete-dialog/delete-dialog.component';
import { AddPropertyDialogModule } from 'app/main/content/_components/dialogs/add-property-dialog/add-property-dialog.module';
import { RemoveDialogModule } from 'app/main/content/_components/dialogs/remove-dialog/remove-dialog.module';
import { AddPropertyDialogComponent } from 'app/main/content/_components/dialogs/add-property-dialog/add-property-dialog.component';
import { RemoveDialogComponent } from 'app/main/content/_components/dialogs/remove-dialog/remove-dialog.component';
import { ClassInstanceFormPreviewDialogModule } from 'app/main/content/_components/help-seeker/configuration/class-instances/form-preview-dialog/form-preview-dialog.module';
import { ClassInstanceFormPreviewDialogComponent } from 'app/main/content/_components/help-seeker/configuration/class-instances/form-preview-dialog/form-preview-dialog.component';
import {
  ClassInstanceFormPreviewExportDialogModule
} from 'app/main/content/_components/help-seeker/configuration/class-instances/form-preview-export-dialog/form-preview-export-dialog.module';
import { ClassInstanceFormPreviewExportDialogComponent } from 'app/main/content/_components/help-seeker/configuration/class-instances/form-preview-export-dialog/form-preview-export-dialog.component';
import { PropertyCreationDialogComponent } from 'app/main/content/_components/help-seeker/configuration/class-configurator/property-creation-dialog/property-creation-dialog.component';
import { PropertyCreationDialogModule } from 'app/main/content/_components/help-seeker/configuration/class-configurator/property-creation-dialog/property-creation-dialog.module';



@NgModule({
  imports: [
    CommonModule,
    // AddOrRemoveDialogModule,
    TextFieldDialogModule,
    ConfirmDialogModule,
    SortDialogModule,
    ChooseTemplateToCopyDialogModule,


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
    RemoveDialogModule,

    PropertyCreationDialogModule,

    // Matching Configurator
    NewMatchingDialogModule,
    OpenMatchingDialogModule,
    DeleteMatchingDialogModule,
  ],
  declarations: [DialogFactoryDirective],
  entryComponents: [
    // AddOrRemoveDialogComponent,
    TextFieldDialogComponent,
    ConfirmDialogComponent,
    SortDialogComponent,
    ChooseTemplateToCopyDialogComponent,

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
    RemoveDialogComponent,

    PropertyCreationDialogComponent,

    // Matching Configurator
    NewMatchingDialogComponent,
    OpenMatchingDialogComponent,
    DeleteMatchingDialogComponent]
})
export class DialogFactoryModule { }