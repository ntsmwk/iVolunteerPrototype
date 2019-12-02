import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
//import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';


import { FuseSharedModule } from "@fuse/shared.module";
import { DynamicFormQuestionComponent } from './dynamic-form-question.component';
//import { DynamicFormModule } from "../dynamic-form/dynamic-form.module";
import { GermanDateAdapter } from '../../../_adapter/german-date-adapter';
import { DynamicFormErrorModule } from "../dynamic-form-error/dynamic-form-error.module";
import {
  MatButtonModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatSidenavModule,
  MatTableModule,
  MatSelectModule,
  MatOptionModule,
  MatRadioModule,
  MatSlideToggleModule,
  MatCardModule,
  MatDatepickerModule,
  MatNativeDateModule,
  DateAdapter,
  MatListModule,
} from '@angular/material';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule,

    MatFormFieldModule, MatButtonModule, MatCheckboxModule, MatChipsModule,
    MatDividerModule, MatIconModule, MatInputModule, MatSidenavModule, MatTableModule, MatSelectModule,
    MatOptionModule, MatRadioModule, MatSlideToggleModule, MatCardModule, MatDatepickerModule, MatNativeDateModule,
    MatListModule,

    FuseSharedModule,
    DynamicFormErrorModule
  ],
  declarations: [DynamicFormQuestionComponent],
  exports: [DynamicFormQuestionComponent],
  providers: [
    { provide: DateAdapter, useClass: GermanDateAdapter },
  ]
})
export class DynamicFormQuestionModule { }
