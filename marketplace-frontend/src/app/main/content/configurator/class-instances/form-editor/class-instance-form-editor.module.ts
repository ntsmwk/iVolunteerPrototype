import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormEditorComponent } from './class-instance-form-editor.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatExpansionModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatOptionModule, MatCardModule, MatSlideToggleModule, MatDatepicker, MatDatepickerModule, MatDividerModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { DynamicClassInstanceCreationFormModule } from 'app/main/content/_components/dynamic-forms/dynamic-class-instance-creation-form/dynamic-class-instance-creation-form.module';
import { DataTransportService } from 'app/main/content/_service/data-transport/data-transport.service';
import { DynamicFormComponent } from 'app/main/content/_components/dynamic-forms/dynamic-form/dynamic-form.component';
import { DynamicFormQuestionModule } from 'app/main/content/_components/dynamic-forms/dynamic-form-question/dynamic-form-question.module';
import { ReactiveFormsModule } from '@angular/forms';
import { DynamicFormModule } from 'app/main/content/_components/dynamic-forms/dynamic-form/dynamic-form.module';

const routes = [
  {path: ':marketplaceId', component: ClassInstanceFormEditorComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),

    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatTableModule,
    MatExpansionModule,

    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatCardModule,
    MatSlideToggleModule,
    MatDatepickerModule,
    MatDividerModule,
    MatIconModule,


    FuseSharedModule,
    FuseTruncatePipeModule,
    DynamicClassInstanceCreationFormModule,

  ],
  declarations: [ClassInstanceFormEditorComponent],


})



export class ClassInstanceFormEditorModule { }
