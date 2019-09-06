import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormEditorComponent } from './class-instance-form-editor.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatExpansionModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatOptionModule, MatCardModule, MatSlideToggleModule, MatDatepicker, MatDatepickerModule, MatDividerModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { DynamicClassInstanceCreationFormModule } from 'app/main/content/_components/dynamic-forms/dynamic-class-instance-creation-form/dynamic-class-instance-creation-form.module';

const routes = [
  {path: ':marketplaceId/:classId', component: ClassInstanceFormEditorComponent}
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
  // exports: [ClassInstanceFormEditorComponent]

})



export class ClassInstanceFormEditorModule { }
