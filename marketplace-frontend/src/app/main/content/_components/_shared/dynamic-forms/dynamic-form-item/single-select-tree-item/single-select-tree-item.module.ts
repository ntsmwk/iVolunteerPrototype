import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FuseSharedModule } from '@fuse/shared.module';
import { SingleSelectTreeItemComponent } from './single-select-tree-item.component';
import { MatCommonModule } from '@angular/material/core';
import { MatFormFieldModule, MatListModule, MatInputModule, MatButtonModule, MatIconModule } from '@angular/material';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, MatCommonModule, MatFormFieldModule,
    MatListModule, MatInputModule, MatButtonModule, MatIconModule,

  ],
  declarations: [SingleSelectTreeItemComponent],
  exports: [SingleSelectTreeItemComponent]

})
export class SingleSelectTreeItemModule { }
