import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';
import { DynamicFormModule } from "../_components/dynamic-forms/dynamic-form/dynamic-form.module";
import { DynamicFormQuestionModule } from "../_components/dynamic-forms/dynamic-form-question/dynamic-form-question.module";

import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatListModule } from '@angular/material/list';

import {FuseSharedModule} from '@fuse/shared.module';


import { MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule, 
  MatInputModule, MatSidenavModule, MatDividerModule, MatTableModule, MatCheckboxModule, MatTooltipModule } 
  from '@angular/material';

  import { FuseUserDefinedTaskTemplateDetailComponent } from './user-defined-task-template-detail.component';
import { QuestionService } from '../_service/question.service';
import { AddOrRemoveDialogModule } from '../_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.module';
import { AddOrRemoveDialogComponent } from '../_components/dialogs/add-or-remove-dialog/add-or-remove-dialog.component';

import { TextFieldDialogComponent } from "../_components/dialogs/text-field-dialog/text-field-dialog.component";
import { TextFieldDialogModule } from "../_components/dialogs/text-field-dialog/text-field-dialog.module";
import { ConfirmDialogComponent } from '../_components/dialogs/confirm-dialog/confirm-dialog.component';
import { ConfirmDialogModule } from '../_components/dialogs/confirm-dialog/confirm-dialog.module';
import { SortDialogModule } from '../_components/dialogs/sort-dialog/sort-dialog.module';
import { SortDialogComponent } from '../_components/dialogs/sort-dialog/sort-dialog.component';

const routes = [
  {path: ':marketplaceId/:templateId', component: FuseUserDefinedTaskTemplateDetailComponent}
];

@NgModule({
  declarations: [FuseUserDefinedTaskTemplateDetailComponent],
  
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

    AddOrRemoveDialogModule,
    TextFieldDialogModule,
    ConfirmDialogModule,
    SortDialogModule,

    DynamicFormModule,
    DynamicFormQuestionModule

  ],
  entryComponents:[AddOrRemoveDialogComponent, TextFieldDialogComponent, ConfirmDialogComponent, SortDialogComponent]

  
  
})
export class FuseUserDefinedTaskTemplateDetailModule { }
