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

@NgModule({
  imports: [
    CommonModule,
    AddOrRemoveDialogModule,
    TextFieldDialogModule,
    ConfirmDialogModule,
    SortDialogModule,
    ChooseTemplateToCopyDialogModule,

  ],
  declarations: [DialogFactoryComponent],
  entryComponents: [AddOrRemoveDialogComponent, TextFieldDialogComponent, ConfirmDialogComponent, 
                    SortDialogComponent, ChooseTemplateToCopyDialogComponent]
})
export class DialogFactoryModule { }
