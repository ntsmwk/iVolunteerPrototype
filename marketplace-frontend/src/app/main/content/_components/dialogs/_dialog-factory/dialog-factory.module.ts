import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogFactoryDirective } from './dialog-factory.component';
import { AddOrRemoveDialogComponent } from '../add-or-remove-dialog/add-or-remove-dialog.component';
import { AddOrRemoveDialogModule } from '../add-or-remove-dialog/add-or-remove-dialog.module';
import { TextFieldDialogComponent } from '../text-field-dialog/text-field-dialog.component';
import { TextFieldDialogModule } from '../text-field-dialog/text-field-dialog.module';
import { ConfirmDialogModule } from '../confirm-dialog/confirm-dialog.module';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { SortDialogComponent } from '../sort-dialog/sort-dialog.component';
import { SortDialogModule } from '../sort-dialog/sort-dialog.module';
import { ChooseTemplateToCopyDialogModule } from '../choose-dialog/choose-dialog.module';
import { ChooseTemplateToCopyDialogComponent } from '../choose-dialog/choose-dialog.component';
import { RelationshipDialogModule } from '../relationship-dialog/relationship-dialog.module';
import { RelationshipDialogComponent } from '../relationship-dialog/relationship-dialog.component';
import { OpenDialogComponent } from 'app/main/content/configurator/class-configurator/open-dialog/open-dialog.component';
import { OpenDialogModule } from 'app/main/content/configurator/class-configurator/open-dialog/open-dialog.module';
import { SaveAsDialogComponent } from 'app/main/content/configurator/class-configurator/save-as-dialog/save-as-dialog.component';
import { SaveAsDialogModule } from 'app/main/content/configurator/class-configurator/save-as-dialog/save-as-dialog.module';
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


@NgModule({
  imports: [
    CommonModule,
    AddOrRemoveDialogModule,
    TextFieldDialogModule,
    ConfirmDialogModule,
    SortDialogModule,
    ChooseTemplateToCopyDialogModule,
    RelationshipDialogModule,


    // Class Configurator
    NewClassConfigurationDialogModule,
    OpenDialogModule,
    SaveAsDialogModule,
    ClassInstanceFormPreviewDialogModule,
    ChangeIconDialogModule,

    // Matching Configurator
    NewMatchingDialogModule,
    OpenMatchingDialogModule,
    DeleteMatchingDialogModule,
  ],
  declarations: [DialogFactoryDirective],
  entryComponents: [
    AddOrRemoveDialogComponent,
    TextFieldDialogComponent,
    ConfirmDialogComponent,
    SortDialogComponent,
    ChooseTemplateToCopyDialogComponent,

    RelationshipDialogComponent,

    // Class Configurator
    NewClassConfigurationDialogComponent,
    OpenDialogComponent,
    SaveAsDialogComponent,
    ClassInstanceFormPreviewDialogComponent,
    ChangeIconDialogComponent,

    // Matching Configurator
    NewMatchingDialogComponent,
    OpenMatchingDialogComponent,
    DeleteMatchingDialogComponent]
})
export class DialogFactoryModule { }
