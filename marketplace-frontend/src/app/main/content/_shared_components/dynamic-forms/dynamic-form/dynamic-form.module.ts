import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
//import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';


import { FuseSharedModule } from "@fuse/shared.module";
import { DynamicFormComponent } from './dynamic-form.component';
import { DynamicFormQuestionModule } from "../dynamic-form-question/dynamic-form-question.module";
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, DynamicFormQuestionModule, MatCommonModule, MatButtonModule, MatIconModule
  ],
  declarations: [DynamicFormComponent],
  exports: [DynamicFormComponent]
  
})
export class DynamicFormModule { }
