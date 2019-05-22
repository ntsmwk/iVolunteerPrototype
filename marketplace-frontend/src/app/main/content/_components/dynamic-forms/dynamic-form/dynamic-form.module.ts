import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
//import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';


import { FuseSharedModule } from "@fuse/shared.module";
import { DynamicFormComponent } from './dynamic-form.component';
import { DynamicFormQuestionModule } from "../dynamic-form-question/dynamic-form-question.module";
import { MatButtonModule, MatCommonModule, MatIconModule } from '@angular/material';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, DynamicFormQuestionModule, MatCommonModule, MatButtonModule, MatIconModule
  ],
  declarations: [DynamicFormComponent],
  exports: [DynamicFormComponent]
  
})
export class DynamicFormModule { }
