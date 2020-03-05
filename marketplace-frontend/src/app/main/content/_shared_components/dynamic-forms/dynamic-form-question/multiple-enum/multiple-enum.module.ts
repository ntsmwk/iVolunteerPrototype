import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FuseSharedModule } from "@fuse/shared.module";
import { MultipleEnumComponent } from './multiple-enum.component';
import { MatCommonModule, MatOptionModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule, MatSelectModule, MatListModule } from '@angular/material';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, MatCommonModule, MatFormFieldModule, 
    MatIconModule, MatOptionModule, MatSelectModule, MatListModule,
    
  ],
  declarations: [MultipleEnumComponent],
  exports: [MultipleEnumComponent]

})
export class MultipleEnumModule { }
