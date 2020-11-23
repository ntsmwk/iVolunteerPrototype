import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { FuseSharedModule } from '@fuse/shared.module';
import { LocationItemComponent } from './location-item.component';
import { MatCommonModule } from '@angular/material/core';
import { MatFormFieldModule, MatInputModule, MatIconModule, MatCardModule, MatSlideToggleModule } from '@angular/material';


@NgModule({
  imports: [
    CommonModule, ReactiveFormsModule, FuseSharedModule, MatCommonModule, MatFormFieldModule,
    MatInputModule, MatIconModule, MatCardModule, MatSlideToggleModule,

  ],
  declarations: [LocationItemComponent],
  exports: [LocationItemComponent]

})
export class LocationItemModule { }
