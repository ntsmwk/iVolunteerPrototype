import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
//import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';


import { FuseSharedModule } from "@fuse/shared.module";
import { DynamicFormQuestionComponent } from './dynamic-form-question.component';
//import { DynamicFormModule } from "../dynamic-form/dynamic-form.module";
import { GermanDateAdapter } from '../../../_adapter/german-date-adapter';
import { DynamicFormErrorModule } from "../dynamic-form-error/dynamic-form-error.module";


import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatOptionModule, MatNativeDateModule, DateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTableModule } from '@angular/material/table';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, 

    MatFormFieldModule, MatButtonModule, MatCheckboxModule, MatChipsModule, 
    MatDividerModule, MatIconModule, MatInputModule, MatSidenavModule, MatTableModule, MatSelectModule, 
    MatOptionModule, MatRadioModule, MatSlideToggleModule, MatCardModule, MatDatepickerModule, MatNativeDateModule,
    
    FuseSharedModule,
    DynamicFormErrorModule
  ],
  declarations: [DynamicFormQuestionComponent],
  exports: [DynamicFormQuestionComponent],
  providers: [
    {provide: DateAdapter, useClass: GermanDateAdapter},
  ]
})
export class DynamicFormQuestionModule { }
