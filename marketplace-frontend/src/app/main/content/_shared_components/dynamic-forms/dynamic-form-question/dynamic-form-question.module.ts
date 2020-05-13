import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FuseSharedModule } from '@fuse/shared.module';
import { DynamicFormQuestionComponent } from './dynamic-form-question.component';
import { GermanDateAdapter } from '../../../_adapter/german-date-adapter';
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
import { MultipleEnumModule } from './multiple-enum/multiple-enum.module';

@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule,

    MatFormFieldModule, MatButtonModule, MatCheckboxModule, MatChipsModule,
    MatDividerModule, MatIconModule, MatInputModule, MatSidenavModule, MatTableModule, MatSelectModule,
    MatOptionModule, MatRadioModule, MatSlideToggleModule, MatCardModule, MatDatepickerModule, MatNativeDateModule,
    MatListModule,

    MultipleEnumModule,

    FuseSharedModule,
  ],
  declarations: [DynamicFormQuestionComponent],
  exports: [DynamicFormQuestionComponent],
  providers: [
    { provide: DateAdapter, useClass: GermanDateAdapter },
  ]
})
export class DynamicFormQuestionModule { }
