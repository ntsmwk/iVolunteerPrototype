import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogFactoryComponent } from './dialog-factory.component';
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
import { RelationshipDialogModule } from "../relationship-dialog/relationship-dialog.module";
import { RelationshipDialogComponent } from '../relationship-dialog/relationship-dialog.component';
import { OpenDialogComponent } from 'app/main/content/_components/help-seeker/configuration/configurator/configurator-editor/open-dialog/open-dialog.component';
import { OpenDialogModule } from 'app/main/content/_components/help-seeker/configuration/configurator/configurator-editor/open-dialog/open-dialog.module';
import { SaveAsDialogComponent } from 'app/main/content/_components/help-seeker/configuration/configurator/configurator-editor/save-as-dialog/save-as-dialog.component';
import { SaveAsDialogModule } from 'app/main/content/_components/help-seeker/configuration/configurator/configurator-editor/save-as-dialog/save-as-dialog.module';
import { ClassInstanceFormPreviewDialogComponent } from 'app/main/content/_components/help-seeker/configuration/configurator/class-instances/form-preview-dialog/form-preview-dialog.component';
import { ClassInstanceFormPreviewDialogModule } from 'app/main/content/_components/help-seeker/configuration/configurator/class-instances/form-preview-dialog/form-preview-dialog.module';
import { ChangeIconDialogComponent } from 'app/main/content/_components/help-seeker/configuration/configurator/configurator-editor/icon-dialog/icon-dialog.component';
import { ChangeIconDialogModule } from 'app/main/content/_components/help-seeker/configuration/configurator/configurator-editor/icon-dialog/icon-dialog.module';

@NgModule({
  imports: [
    CommonModule,
    AddOrRemoveDialogModule,
    TextFieldDialogModule,
    ConfirmDialogModule,
    SortDialogModule,
    ChooseTemplateToCopyDialogModule,
    RelationshipDialogModule,
    OpenDialogModule,
    SaveAsDialogModule,
    ClassInstanceFormPreviewDialogModule,
    ChangeIconDialogModule
  ],
  declarations: [DialogFactoryComponent],
  entryComponents: [AddOrRemoveDialogComponent, TextFieldDialogComponent, ConfirmDialogComponent, 
                    SortDialogComponent, ChooseTemplateToCopyDialogComponent, RelationshipDialogComponent,
                    OpenDialogComponent, SaveAsDialogComponent, ClassInstanceFormPreviewDialogComponent,
                    ChangeIconDialogComponent]
})
export class DialogFactoryModule { }
