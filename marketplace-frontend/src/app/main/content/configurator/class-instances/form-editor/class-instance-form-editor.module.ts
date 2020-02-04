import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormEditorComponent } from './class-instance-form-editor.component';
import { RouterModule } from '@angular/router';
import { 
  MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatTableModule, MatExpansionModule, MatFormFieldModule, MatInputModule, 
  MatSelectModule, MatOptionModule, MatCardModule, MatSlideToggleModule, MatDatepicker, MatDatepickerModule, MatDividerModule, MatButtonModule, 
  MatListModule } from '@angular/material';
import { FuseTruncatePipeModule } from '../../../_pipe/truncate-pipe.module';
import { FuseSharedModule } from '@fuse/shared.module';
import { 
  DynamicClassInstanceCreationFormModule 
} from 'app/main/content/_components/dynamic-forms/dynamic-class-instance-creation-form/dynamic-class-instance-creation-form.module';


const routes = [
  { path: ':marketplaceId', component: ClassInstanceFormEditorComponent }
  // {path: ':marketplaceId/:classId/:showMaxGluehtemperatur', component: ClassInstanceFormEditorComponent}
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
    MatButtonModule,

    MatListModule,


    FuseSharedModule,
    FuseTruncatePipeModule,
    DynamicClassInstanceCreationFormModule,

  ],
  declarations: [ClassInstanceFormEditorComponent],


})



export class ClassInstanceFormEditorModule { }
