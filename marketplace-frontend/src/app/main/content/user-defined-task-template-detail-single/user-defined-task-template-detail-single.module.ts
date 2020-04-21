import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { DynamicFormModule } from '../_components/dynamic-forms/dynamic-form/dynamic-form.module';
import { DynamicFormQuestionModule } from '../_components/dynamic-forms/dynamic-form-question/dynamic-form-question.module';

import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatListModule } from '@angular/material/list';

import { FuseSharedModule } from '@fuse/shared.module';


import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';

import { SingleUserDefinedTaskTemplateDetailComponent } from './user-defined-task-template-detail-single.component';

import { TextFieldDialogModule } from '../_components/dialogs/text-field-dialog/text-field-dialog.module';
import { ConfirmDialogComponent } from '../_components/dialogs/confirm-dialog/confirm-dialog.component';
import { ConfirmDialogModule } from '../_components/dialogs/confirm-dialog/confirm-dialog.module';
import { SortDialogModule } from '../_components/dialogs/sort-dialog/sort-dialog.module';
import { SortDialogComponent } from '../_components/dialogs/sort-dialog/sort-dialog.component';
import { DialogFactoryModule } from '../_components/dialogs/_dialog-factory/dialog-factory.module';

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
