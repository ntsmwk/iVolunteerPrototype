import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FuseUserDefinedTaskTemplateDetailFormComponent } from './user-defined-task-template-detail-form.component';
import { FuseSharedModule } from '@fuse/shared.module';
import { RouterModule } from '@angular/router';
import { QuestionService } from '../_service/question.service';
import { MatIconModule, MatCommonModule } from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';
import { DynamicFormModule } from "../_components/dynamic-forms/dynamic-form/dynamic-form.module";
import { DynamicFormQuestionModule } from '../_components/dynamic-forms/dynamic-form-question/dynamic-form-question.module';


const routes = [
  {path: ':marketplaceId/:templateId', component: FuseUserDefinedTaskTemplateDetailFormComponent}
];

@NgModule({
  declarations: [FuseUserDefinedTaskTemplateDetailFormComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatIconModule,
    MatCommonModule,
    FuseSharedModule,
    ReactiveFormsModule,
     DynamicFormModule,
     DynamicFormQuestionModule
   
  ]
  
})
export class FuseUserDefinedTaskTemplateDetailFormModule { }
