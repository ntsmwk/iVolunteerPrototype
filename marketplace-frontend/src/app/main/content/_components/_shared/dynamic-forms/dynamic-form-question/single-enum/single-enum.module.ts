import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FuseSharedModule } from '@fuse/shared.module';
import { SingleEnumComponent } from './single-enum.component';
import { MatCommonModule } from '@angular/material/core';
import { MatFormFieldModule, MatListModule, MatInputModule, MatButtonModule } from '@angular/material';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, MatCommonModule, MatFormFieldModule,
    MatListModule, MatInputModule, MatButtonModule,

  ],
  declarations: [SingleEnumComponent],
  exports: [SingleEnumComponent]

})
export class SingleEnumModule { }
