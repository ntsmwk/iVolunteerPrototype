import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DynamicFormErrorComponent } from './dynamic-form-error.component'
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatCommonModule, MatOptionModule, MatNativeDateModule } from '@angular/material/core';
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
import { FuseSharedModule } from '@fuse/shared.module';

@NgModule({
    imports: [
      //CommonModule, ReactiveFormsModule, MatCommonModule, MatFormFieldModule, MatInputModule, FuseSharedModule
      CommonModule, ReactiveFormsModule, 
    MatFormFieldModule, MatButtonModule, MatCheckboxModule, MatChipsModule, 
    MatDividerModule, MatIconModule, MatInputModule, MatSidenavModule, MatTableModule, MatSelectModule, 
    MatOptionModule, MatRadioModule, MatSlideToggleModule, MatCardModule, MatDatepickerModule, MatNativeDateModule ,FuseSharedModule,
    ],
    declarations: [DynamicFormErrorComponent],
    exports: [DynamicFormErrorComponent],
    
  })
  export class DynamicFormErrorModule { }