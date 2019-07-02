import { NgModule } from '@angular/core';
import { RouterModule, Route } from '@angular/router';

import { CommonModule } from '@angular/common';
import { UserDefinedTaskTemplateListComponent } from './user-defined-task-template-list.component';
import { MatTableModule, MatIconModule, MatButtonModule, MatProgressSpinnerModule, MatTooltipModule, MatMenuModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FuseTruncatePipeModule } from '../_pipe/truncate-pipe.module';

import { DynamicFormModule } from '../_components/dynamic-forms/dynamic-form/dynamic-form.module';
import { DynamicFormQuestionModule } from '../_components/dynamic-forms/dynamic-form-question/dynamic-form-question.module';
import { DialogFactoryModule } from '../_components/dialogs/_dialog-factory/dialog-factory.module';


const routes: Route[] = [
  {path: '', component: UserDefinedTaskTemplateListComponent}
];

@NgModule({
  declarations: [UserDefinedTaskTemplateListComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    MatMenuModule,

    FuseSharedModule,
    FuseTruncatePipeModule,

    DialogFactoryModule,
    
    DynamicFormModule,
    DynamicFormQuestionModule
  ],
})
export class UserDefinedTaskTemplateListModule { }
