import { NgModule } from '@angular/core';
import { RouterModule, Route } from '@angular/router';

import { CommonModule } from '@angular/common';
import { FuseUserDefinedTaskTemplateListComponent } from './user-defined-task-template-list.component';
import { MatTableModule, MatIconModule, MatButtonModule, MatProgressSpinnerModule, MatTooltipModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FuseTruncatePipeModule } from '../_pipe/truncate-pipe.module';
import { TextFieldDialogComponent } from '../_components/dialogs/text-field-dialog/text-field-dialog.component';
import { TextFieldDialogModule } from '../_components/dialogs/text-field-dialog/text-field-dialog.module';
import { DynamicFormModule } from '../_components/dynamic-forms/dynamic-form/dynamic-form.module';
import { DynamicFormQuestionModule } from '../_components/dynamic-forms/dynamic-form-question/dynamic-form-question.module';


const routes: Route[] = [
  {path: '', component: FuseUserDefinedTaskTemplateListComponent}
];

@NgModule({
  declarations: [FuseUserDefinedTaskTemplateListComponent],
  imports: [
    RouterModule.forChild(routes),
    
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatTooltipModule,

    FuseSharedModule,
    FuseTruncatePipeModule,
    
    TextFieldDialogModule,

    DynamicFormModule,
    DynamicFormQuestionModule
  ],
  entryComponents:[TextFieldDialogComponent]
})
export class FuseUserDefinedTaskTemplateListModule { }
