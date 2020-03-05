import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SingleUserDefinedTaskTemplateDetailFormComponent } from './user-defined-task-template-detail-form-single.component';
import { FuseSharedModule } from '@fuse/shared.module';
import { RouterModule } from '@angular/router';
import { QuestionService } from '../../../../_service/question.service';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';
import { DynamicFormModule } from '../../../../_shared_components/dynamic-forms/dynamic-form/dynamic-form.module';
import { DynamicFormQuestionModule } from '../../../../_shared_components/dynamic-forms/dynamic-form-question/dynamic-form-question.module';


const routes = [
  {path: ':marketplaceId/:templateId', component: SingleUserDefinedTaskTemplateDetailFormComponent},
  {path: ':marketplaceId/:templateId/:subtemplateId', component: SingleUserDefinedTaskTemplateDetailFormComponent}
];

@NgModule({
  declarations: [SingleUserDefinedTaskTemplateDetailFormComponent],
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
export class SingleUserDefinedTaskTemplateDetailFormModule { }
