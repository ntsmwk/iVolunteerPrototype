import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SinglePropertyComponent } from './single-property.component';
import { FuseTruncatePipeModule } from '../../_pipe/truncate-pipe.module';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule, MatIconModule, MatRadioModule, MatFormFieldModule, MatInputModule, MatOptionModule, MatSelectModule, MatSlideToggleModule, MatDividerModule, MatDatepickerModule, MatNativeDateModule, MatTooltipModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';

@NgModule({
  imports: [
    CommonModule,
    
    MatCommonModule,
    MatProgressSpinnerModule,
    MatIconModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    MatSlideToggleModule,
    MatDividerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTooltipModule,

    FuseSharedModule,
    FuseTruncatePipeModule,
  ],
  declarations: [SinglePropertyComponent],
  exports: [SinglePropertyComponent]
})
export class SinglePropertyModule { }
