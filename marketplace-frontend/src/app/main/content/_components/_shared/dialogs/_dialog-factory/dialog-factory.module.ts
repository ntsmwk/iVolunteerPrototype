import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TextFieldDialogModule } from "../text-field-dialog/text-field-dialog.module";
import { ConfirmDialogModule } from "../confirm-dialog/confirm-dialog.module";
import { SortDialogModule } from "../sort-dialog/sort-dialog.module";
import { NewClassConfigurationDialogModule } from '../../../help-seeker/configuration/class-configurator/_dialogs/new-dialog/new-dialog.module';
import { OpenClassConfigurationDialogModule } from '../../../help-seeker/configuration/class-configurator/_dialogs/open-dialog/open-dialog.module';
import { ConfirmClassConfigurationSaveDialogModule } from '../../../help-seeker/configuration/class-configurator/_dialogs/confirm-save-dialog/confirm-save-dialog.module';
import { SaveClassConfigurationAsDialogModule } from '../../../help-seeker/configuration/class-configurator/_dialogs/save-as-dialog/save-as-dialog.module';
import { DeleteClassConfigurationDialogModule } from '../../../help-seeker/configuration/class-configurator/_dialogs/delete-dialog/delete-dialog.module';
import { ClassInstanceFormPreviewDialogModule } from '../../../help-seeker/configuration/class-instances/form-preview-dialog/form-preview-dialog.module';
import { ChangeIconDialogModule } from '../../../help-seeker/configuration/class-configurator/_dialogs/icon-dialog/icon-dialog.module';
import { ClassInstanceFormPreviewExportDialogModule } from '../../../help-seeker/configuration/class-instances/form-preview-export-dialog/form-preview-export-dialog.module';
import { AddPropertyDialogModule } from '../add-property-dialog/add-property-dialog.module';
import { RemoveDialogModule } from '../remove-dialog/remove-dialog.module';
import { PropertyOrEnumCreationDialogModule } from '../../../help-seeker/configuration/class-configurator/_dialogs/property-enum-creation-dialog/property-enum-creation-dialog.module';
import { NewMatchingDialogModule } from '../../../help-seeker/configuration/matching-configurator/new-dialog/new-dialog.module';
import { OpenMatchingDialogModule } from '../../../help-seeker/configuration/matching-configurator/open-dialog/open-dialog.module';
import { DeleteMatchingDialogModule } from '../../../help-seeker/configuration/matching-configurator/delete-dialog/delete-dialog.module';
import { DialogFactoryDirective } from './dialog-factory.component';
import { TextFieldDialogComponent } from '../text-field-dialog/text-field-dialog.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SortDialogComponent } from '../sort-dialog/sort-dialog.component';
import { NewClassConfigurationDialogComponent } from '../../../help-seeker/configuration/class-configurator/_dialogs/new-dialog/new-dialog.component';
import { OpenClassConfigurationDialogComponent } from '../../../help-seeker/configuration/class-configurator/_dialogs/open-dialog/open-dialog.component';
import { ConfirmClassConfigurationSaveDialogComponent } from '../../../help-seeker/configuration/class-configurator/_dialogs/confirm-save-dialog/confirm-save-dialog.component';
import { SaveClassConfigurationAsDialogComponent } from '../../../help-seeker/configuration/class-configurator/_dialogs/save-as-dialog/save-as-dialog.component';
import { ClassInstanceFormPreviewDialogComponent } from '../../../help-seeker/configuration/class-instances/form-preview-dialog/form-preview-dialog.component';
import { DeleteClassConfigurationDialogComponent } from '../../../help-seeker/configuration/class-configurator/_dialogs/delete-dialog/delete-dialog.component';
import { ChangeIconDialogComponent } from '../../../help-seeker/configuration/class-configurator/_dialogs/icon-dialog/icon-dialog.component';
import { ClassInstanceFormPreviewExportDialogComponent } from '../../../help-seeker/configuration/class-instances/form-preview-export-dialog/form-preview-export-dialog.component';
import { AddPropertyDialogComponent } from '../add-property-dialog/add-property-dialog.component';
import { RemoveDialogComponent } from '../remove-dialog/remove-dialog.component';
import { PropertyOrEnumCreationDialogComponent } from '../../../help-seeker/configuration/class-configurator/_dialogs/property-enum-creation-dialog/property-enum-creation-dialog.component';
import { NewMatchingDialogComponent } from '../../../help-seeker/configuration/matching-configurator/new-dialog/new-dialog.component';
import { OpenMatchingDialogComponent } from '../../../help-seeker/configuration/matching-configurator/open-dialog/open-dialog.component';
import { DeleteMatchingDialogComponent } from '../../../help-seeker/configuration/matching-configurator/delete-dialog/delete-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    // AddOrRemoveDialogModule,
    TextFieldDialogModule,
    ConfirmDialogModule,
    SortDialogModule,

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

    PropertyOrEnumCreationDialogModule,

    // Matching Configurator
    NewMatchingDialogModule,
    OpenMatchingDialogModule,
    DeleteMatchingDialogModule,

    // Enum Configurator
  ],
  declarations: [DialogFactoryDirective],
  entryComponents: [
    // AddOrRemoveDialogComponent,
    TextFieldDialogComponent,
    ConfirmDialogComponent,
    SortDialogComponent,

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

    PropertyOrEnumCreationDialogComponent,

    // Matching Configurator
    NewMatchingDialogComponent,
    OpenMatchingDialogComponent,
    DeleteMatchingDialogComponent,

    // Enum Configurator
  ],
})
export class DialogFactoryModule { }
