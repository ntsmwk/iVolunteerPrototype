import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FuseSharedModule } from '@fuse/shared.module';
import { MultipleSelectTreeItemComponent } from './multiple-select-tree-item.component';
import { MatCommonModule, MatOptionModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule, MatSelectModule, MatListModule } from '@angular/material';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, MatCommonModule, MatFormFieldModule,
    MatIconModule, MatOptionModule, MatSelectModule, MatListModule,

  ],
  declarations: [MultipleSelectTreeItemComponent],
  exports: [MultipleSelectTreeItemComponent]

})
export class MultipleSelectTreeItemModule { }
