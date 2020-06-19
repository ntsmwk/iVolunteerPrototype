import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TextFieldDialogModule } from "../text-field-dialog/text-field-dialog.module";
import { ConfirmDialogModule } from "../confirm-dialog/confirm-dialog.module";
import { SortDialogModule } from "../sort-dialog/sort-dialog.module";
import { NewClassConfigurationDialogModule } from "app/main/content/_components/help-seeker/configuration/class-configurator/new-dialog/new-dialog.module";
import { OpenClassConfigurationDialogModule } from "app/main/content/_components/help-seeker/configuration/class-configurator/open-dialog/open-dialog.module";
import { ConfirmClassConfigurationSaveDialogModule } from "app/main/content/_components/help-seeker/configuration/class-configurator/confirm-save-dialog/confirm-save-dialog.module";
import { SaveClassConfigurationAsDialogModule } from "app/main/content/_components/help-seeker/configuration/class-configurator/save-as-dialog/save-as-dialog.module";
import { DeleteClassConfigurationDialogModule } from "app/main/content/_components/help-seeker/configuration/class-configurator/delete-dialog/delete-dialog.module";
import { ClassInstanceFormPreviewDialogModule } from "app/main/content/_components/help-seeker/configuration/class-instances/form-preview-dialog/form-preview-dialog.module";
import { ChangeIconDialogModule } from "app/main/content/_components/help-seeker/configuration/class-configurator/icon-dialog/icon-dialog.module";
import { ClassInstanceFormPreviewExportDialogModule } from "app/main/content/_components/help-seeker/configuration/class-instances/form-preview-export-dialog/form-preview-export-dialog.module";
import { AddPropertyDialogModule } from "app/main/content/_components/dialogs/add-property-dialog/add-property-dialog.module";
import { RemoveDialogModule } from "app/main/content/_components/dialogs/remove-dialog/remove-dialog.module";
import { PropertyOrEnumCreationDialogModule } from "app/main/content/_components/help-seeker/configuration/class-configurator/property-enum-creation-dialog/property-enum-creation-dialog.module";
import { NewMatchingDialogModule } from "app/main/content/_components/help-seeker/configuration/matching-configurator/new-dialog/new-dialog.module";
import { OpenMatchingDialogModule } from "app/main/content/_components/help-seeker/configuration/matching-configurator/open-dialog/open-dialog.module";
import { DeleteMatchingDialogModule } from "app/main/content/_components/help-seeker/configuration/matching-configurator/delete-dialog/delete-dialog.module";
import { OpenEnumDefinitionDialogModule } from "app/main/content/_components/help-seeker/configuration/property-enum-configurator/builder/enum/enum-graph-editor/open-enum-definition-dialog/open-enum-definition-dialog.module";
import { DialogFactoryDirective } from "./dialog-factory.component";
import { TextFieldDialogComponent } from "../text-field-dialog/text-field-dialog.component";
import { ConfirmDialogComponent } from "../confirm-dialog/confirm-dialog.component";
import { SortDialogComponent } from "../sort-dialog/sort-dialog.component";
import { NewClassConfigurationDialogComponent } from "app/main/content/_components/help-seeker/configuration/class-configurator/new-dialog/new-dialog.component";
import { OpenClassConfigurationDialogComponent } from "app/main/content/_components/help-seeker/configuration/class-configurator/open-dialog/open-dialog.component";
import { ConfirmClassConfigurationSaveDialogComponent } from "app/main/content/_components/help-seeker/configuration/class-configurator/confirm-save-dialog/confirm-save-dialog.component";
import { SaveClassConfigurationAsDialogComponent } from "app/main/content/_components/help-seeker/configuration/class-configurator/save-as-dialog/save-as-dialog.component";
import { ClassInstanceFormPreviewDialogComponent } from "app/main/content/_components/help-seeker/configuration/class-instances/form-preview-dialog/form-preview-dialog.component";
import { DeleteClassConfigurationDialogComponent } from "app/main/content/_components/help-seeker/configuration/class-configurator/delete-dialog/delete-dialog.component";
import { ChangeIconDialogComponent } from "app/main/content/_components/help-seeker/configuration/class-configurator/icon-dialog/icon-dialog.component";
import { ClassInstanceFormPreviewExportDialogComponent } from "app/main/content/_components/help-seeker/configuration/class-instances/form-preview-export-dialog/form-preview-export-dialog.component";
import { AddPropertyDialogComponent } from "app/main/content/_components/dialogs/add-property-dialog/add-property-dialog.component";
import { RemoveDialogComponent } from "app/main/content/_components/dialogs/remove-dialog/remove-dialog.component";
import { PropertyOrEnumCreationDialogComponent } from "app/main/content/_components/help-seeker/configuration/class-configurator/property-enum-creation-dialog/property-enum-creation-dialog.component";
import { NewMatchingDialogComponent } from "app/main/content/_components/help-seeker/configuration/matching-configurator/new-dialog/new-dialog.component";
import { OpenMatchingDialogComponent } from "app/main/content/_components/help-seeker/configuration/matching-configurator/open-dialog/open-dialog.component";
import { DeleteMatchingDialogComponent } from "app/main/content/_components/help-seeker/configuration/matching-configurator/delete-dialog/delete-dialog.component";
import { OpenEnumDefinitionDialogComponent } from "app/main/content/_components/help-seeker/configuration/property-enum-configurator/builder/enum/enum-graph-editor/open-enum-definition-dialog/open-enum-definition-dialog.component";

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
export class DialogFactoryModule {}
