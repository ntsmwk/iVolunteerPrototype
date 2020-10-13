import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FuseSharedModule } from '@fuse/shared.module';
import { DynamicFormBlockComponent } from './dynamic-form-block.component';
import { DynamicFormItemModule } from '../dynamic-form-item/dynamic-form-item.module';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, DynamicFormItemModule, MatCommonModule, MatButtonModule, MatIconModule
  ],
  declarations: [DynamicFormBlockComponent],
  exports: [DynamicFormBlockComponent]

})
export class DynamicFormBlockModule { }
