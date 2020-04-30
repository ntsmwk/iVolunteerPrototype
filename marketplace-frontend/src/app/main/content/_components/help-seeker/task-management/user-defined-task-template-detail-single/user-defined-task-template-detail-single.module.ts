import { SingleUserDefinedTaskTemplateDetailComponent } from './user-defined-task-template-detail-single.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import {
  MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule, MatInputModule,
  MatSidenavModule, MatDividerModule, MatTableModule, MatCheckboxModule, MatProgressSpinnerModule,
  MatListModule, MatTooltipModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { DialogFactoryModule } from 'app/main/content/_shared_components/dialogs/_dialog-factory/dialog-factory.module';
import { TextFieldDialogModule } from 'app/main/content/_shared_components/dialogs/text-field-dialog/text-field-dialog.module';
import { ConfirmDialogModule } from 'app/main/content/_shared_components/dialogs/confirm-dialog/confirm-dialog.module';
import { SortDialogModule } from 'app/main/content/_shared_components/dialogs/sort-dialog/sort-dialog.module';
import { DynamicFormModule } from 'app/main/content/_shared_components/dynamic-forms/dynamic-form/dynamic-form.module';
import { DynamicFormQuestionModule } from 'app/main/content/_shared_components/dynamic-forms/dynamic-form-question/dynamic-form-question.module';
import { ConfirmDialogComponent } from 'app/main/content/_shared_components/dialogs/confirm-dialog/confirm-dialog.component';
import { SortDialogComponent } from 'app/main/content/_shared_components/dialogs/sort-dialog/sort-dialog.component';

const routes = [
  { path: ':marketplaceId/:templateId', component: SingleUserDefinedTaskTemplateDetailComponent }
];

@NgModule({
  declarations: [SingleUserDefinedTaskTemplateDetailComponent],

  imports: [
    RouterModule.forChild(routes),
    ReactiveFormsModule,

    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatDividerModule,
    MatTableModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatListModule,
    MatTooltipModule,

    FuseSharedModule,

    DialogFactoryModule,


    TextFieldDialogModule,
    ConfirmDialogModule,
    SortDialogModule,

    DynamicFormModule,
    DynamicFormQuestionModule

  ],
  entryComponents: [ConfirmDialogComponent, SortDialogComponent]



})
export class SingleUserDefinedTaskTemplateDetailModule { }
