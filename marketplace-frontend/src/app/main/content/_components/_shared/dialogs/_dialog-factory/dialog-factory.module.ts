import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ConfirmDialogModule } from "../confirm-dialog/confirm-dialog.module";
import { SortDialogModule } from "../sort-dialog/sort-dialog.module";
import { NewClassConfigurationDialogModule } from "../../../help-seeker/configuration/class-configurator/_dialogs/new-dialog/new-dialog.module";
import { OpenClassConfigurationDialogModule } from "../../../help-seeker/configuration/class-configurator/_dialogs/open-dialog/open-dialog.module";
import { ConfirmClassConfigurationSaveDialogModule } from "../../../help-seeker/configuration/class-configurator/_dialogs/confirm-save-dialog/confirm-save-dialog.module";
import { SaveClassConfigurationAsDialogModule } from "../../../help-seeker/configuration/class-configurator/_dialogs/save-as-dialog/save-as-dialog.module";
import { DeleteClassConfigurationDialogModule } from "../../../help-seeker/configuration/class-configurator/_dialogs/delete-dialog/delete-dialog.module";
import { ClassInstanceFormPreviewDialogModule } from "../../../help-seeker/configuration/class-instance-configurator/form-preview-dialog/form-preview-dialog.module";
import { ChangeIconDialogModule } from "../../../help-seeker/configuration/class-configurator/_dialogs/icon-dialog/icon-dialog.module";
import { ClassInstanceFormPreviewExportDialogModule } from "../../../help-seeker/configuration/class-instance-configurator/form-preview-export-dialog/form-preview-export-dialog.module";
import { AddPropertyDialogModule } from "../add-property-dialog/add-property-dialog.module";
import { RemovePropertyDialogModule } from "../remove-dialog/remove-dialog.module";
import { PropertyCreationDialogModule } from "../../../help-seeker/configuration/class-configurator/_dialogs/property-creation-dialog/property-creation-dialog.module";
import { NewMatchingDialogModule } from "../../../help-seeker/configuration/matching-configurator/_dialogs/new-dialog/new-dialog.module";
import { OpenMatchingDialogModule } from "../../../help-seeker/configuration/matching-configurator/_dialogs/open-dialog/open-dialog.module";
import { DeleteMatchingDialogModule } from "../../../help-seeker/configuration/matching-configurator/_dialogs/delete-dialog/delete-dialog.module";
import { DialogFactoryDirective } from "./dialog-factory.component";
import { ConfirmDialogComponent } from "../confirm-dialog/confirm-dialog.component";
import { SortDialogComponent } from "../sort-dialog/sort-dialog.component";
import { NewClassConfigurationDialogComponent } from "../../../help-seeker/configuration/class-configurator/_dialogs/new-dialog/new-dialog.component";
import { OpenClassConfigurationDialogComponent } from "../../../help-seeker/configuration/class-configurator/_dialogs/open-dialog/open-dialog.component";
import { ConfirmClassConfigurationSaveDialogComponent } from "../../../help-seeker/configuration/class-configurator/_dialogs/confirm-save-dialog/confirm-save-dialog.component";
import { SaveClassConfigurationAsDialogComponent } from "../../../help-seeker/configuration/class-configurator/_dialogs/save-as-dialog/save-as-dialog.component";
import { ClassInstanceFormPreviewDialogComponent } from "../../../help-seeker/configuration/class-instance-configurator/form-preview-dialog/form-preview-dialog.component";
import { DeleteClassConfigurationDialogComponent } from "../../../help-seeker/configuration/class-configurator/_dialogs/delete-dialog/delete-dialog.component";
import { ChangeIconDialogComponent } from "../../../help-seeker/configuration/class-configurator/_dialogs/icon-dialog/icon-dialog.component";
import { ClassInstanceFormPreviewExportDialogComponent } from "../../../help-seeker/configuration/class-instance-configurator/form-preview-export-dialog/form-preview-export-dialog.component";
import { AddPropertyDialogComponent } from "../add-property-dialog/add-property-dialog.component";
import { RemovePropertyDialogComponent } from "../remove-dialog/remove-dialog.component";
import { PropertyCreationDialogComponent } from "../../../help-seeker/configuration/class-configurator/_dialogs/property-creation-dialog/property-creation-dialog.component";
import { NewMatchingDialogComponent } from "../../../help-seeker/configuration/matching-configurator/_dialogs/new-dialog/new-dialog.component";
import { OpenMatchingDialogComponent } from "../../../help-seeker/configuration/matching-configurator/_dialogs/open-dialog/open-dialog.component";
import { DeleteMatchingDialogComponent } from "../../../help-seeker/configuration/matching-configurator/_dialogs/delete-dialog/delete-dialog.component";
import { AddClassDefinitionDialogModule } from "../../../help-seeker/configuration/matching-configurator/_dialogs/add-class-definition-dialog/add-class-definition-dialog.module";
import { AddClassDefinitionDialogComponent } from "../../../help-seeker/configuration/matching-configurator/_dialogs/add-class-definition-dialog/add-class-definition-dialog.component";
import { AddHelpseekerDialogComponent } from "../../../admin/tenant-form/tenant-form-content/helpseekers-form/add-helpseeker-dialog/add-helpseeker-dialog.component";
import { AddHelpseekerDialogModule } from "../../../admin/tenant-form/tenant-form-content/helpseekers-form/add-helpseeker-dialog/add-helpseeker-dialog.module";

@NgModule({
  imports: [
    CommonModule,
    // AddOrRemoveDialogModule,
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
    RemovePropertyDialogModule,

    PropertyCreationDialogModule,

    // Matching Configurator
    NewMatchingDialogModule,
    OpenMatchingDialogModule,
    DeleteMatchingDialogModule,
    AddClassDefinitionDialogModule,

    // Tree Property Configurator

    // Tenant Form
    AddHelpseekerDialogModule
  ],
  declarations: [DialogFactoryDirective],
  entryComponents: [
    // AddOrRemoveDialogComponent,
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
    RemovePropertyDialogComponent,

    PropertyCreationDialogComponent,

    // Matching Configurator
    NewMatchingDialogComponent,
    OpenMatchingDialogComponent,
    DeleteMatchingDialogComponent,
    AddClassDefinitionDialogComponent,

    // Tree Property Configurator

    // Tenant Form
    AddHelpseekerDialogComponent
  ]
})
export class DialogFactoryModule {}
