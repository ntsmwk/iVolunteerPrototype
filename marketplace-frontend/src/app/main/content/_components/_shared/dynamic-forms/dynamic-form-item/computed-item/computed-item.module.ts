import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FuseSharedModule } from '@fuse/shared.module';
import { MatCommonModule } from '@angular/material/core';
import { MatFormFieldModule, MatInputModule, MatIconModule, MatCardModule, MatSlideToggleModule } from '@angular/material';
import { ComputedItemComponent } from './computed-item.component';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, MatCommonModule, MatFormFieldModule,
    MatInputModule, MatIconModule, MatCardModule, MatSlideToggleModule,

  ],
  declarations: [ComputedItemComponent],
  exports: [ComputedItemComponent]

})
export class ComputedItemModule { }
