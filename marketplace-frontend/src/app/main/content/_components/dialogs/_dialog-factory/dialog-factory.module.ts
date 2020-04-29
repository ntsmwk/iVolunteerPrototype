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
import { OpenClassConfigurationDialogComponent } from 'app/main/content/configurator/class-configurator/open-dialog/open-dialog.component';
import { OpenClassConfigurationDialogModule } from 'app/main/content/configurator/class-configurator/open-dialog/open-dialog.module';
import { SaveClassConfigurationAsDialogComponent } from 'app/main/content/configurator/class-configurator/save-as-dialog/save-as-dialog.component';
import { SaveClassConfigurationAsDialogModule } from 'app/main/content/configurator/class-configurator/save-as-dialog/save-as-dialog.module';
import { ClassInstanceFormPreviewDialogComponent } from 'app/main/content/configurator/class-instances/form-preview-dialog/form-preview-dialog.component';
import { ClassInstanceFormPreviewDialogModule } from 'app/main/content/configurator/class-instances/form-preview-dialog/form-preview-dialog.module';
import { ChangeIconDialogComponent } from 'app/main/content/configurator/class-configurator/icon-dialog/icon-dialog.component';
import { ChangeIconDialogModule } from 'app/main/content/configurator/class-configurator/icon-dialog/icon-dialog.module';
import { NewMatchingDialogComponent } from 'app/main/content/configurator/matching-configurator/new-dialog/new-dialog.component';
import { NewMatchingDialogModule } from 'app/main/content/configurator/matching-configurator/new-dialog/new-dialog.module';
import { OpenMatchingDialogComponent } from 'app/main/content/configurator/matching-configurator/open-dialog/open-dialog.component';
import { OpenMatchingDialogModule } from 'app/main/content/configurator/matching-configurator/open-dialog/open-dialog.module';
import { DeleteMatchingDialogComponent } from 'app/main/content/configurator/matching-configurator/delete-dialog/delete-dialog.component';
import { DeleteMatchingDialogModule } from 'app/main/content/configurator/matching-configurator/delete-dialog/delete-dialog.module';
import { NewClassConfigurationDialogModule } from 'app/main/content/configurator/class-configurator/new-dialog/new-dialog.module';
import { NewClassConfigurationDialogComponent } from 'app/main/content/configurator/class-configurator/new-dialog/new-dialog.component';
import { ConfirmClassConfigurationSaveDialogModule } from 'app/main/content/configurator/class-configurator/confirm-save-dialog/confirm-save-dialog.module';
import { ConfirmClassConfigurationSaveDialogComponent } from 'app/main/content/configurator/class-configurator/confirm-save-dialog/confirm-save-dialog.component';
import { DeleteClassConfigurationDialogModule } from 'app/main/content/configurator/class-configurator/delete-dialog/delete-dialog.module';
import { DeleteClassConfigurationDialogComponent } from 'app/main/content/configurator/class-configurator/delete-dialog/delete-dialog.component';
import { AddPropertyDialogComponent } from '../add-property-dialog/add-property-dialog.component';
import { AddPropertyDialogModule } from '../add-property-dialog/add-property-dialog.module';
import { RemoveDialogComponent } from '../remove-dialog/remove-dialog.component';
import { RemoveDialogModule } from '../remove-dialog/remove-dialog.module';


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

    AddPropertyDialogModule,
    RemoveDialogModule,

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

    AddPropertyDialogComponent,
    RemoveDialogComponent,

    // Matching Configurator
    NewMatchingDialogComponent,
    OpenMatchingDialogComponent,
    DeleteMatchingDialogComponent]
})
export class DialogFactoryModule { }
