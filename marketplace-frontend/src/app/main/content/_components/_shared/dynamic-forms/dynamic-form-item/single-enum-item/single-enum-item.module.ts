import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FuseSharedModule } from '@fuse/shared.module';
import { SingleEnumItemComponent } from './single-enum-item.component';
import { MatCommonModule } from '@angular/material/core';
import { MatFormFieldModule, MatListModule, MatInputModule, MatButtonModule, MatIconModule } from '@angular/material';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, MatCommonModule, MatFormFieldModule,
    MatListModule, MatInputModule, MatButtonModule, MatIconModule,

  ],
  declarations: [SingleEnumItemComponent],
  exports: [SingleEnumItemComponent]

})
export class SingleEnumItemModule { }
