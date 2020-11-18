import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatProgressSpinnerModule, MatExpansionModule, MatButtonModule, MatIconModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { InstanceCreationResultComponent } from './result.component';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatProgressSpinnerModule,
    MatExpansionModule,

    MatButtonModule,
    MatIconModule,

    FuseSharedModule,

  ],
  declarations: [InstanceCreationResultComponent],
  exports: [InstanceCreationResultComponent]


})



export class InstanceCreationResultModule { }
