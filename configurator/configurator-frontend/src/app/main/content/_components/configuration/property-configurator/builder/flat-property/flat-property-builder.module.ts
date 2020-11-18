import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlatPropertyBuilderComponent } from './flat-property-builder.component';
import { FuseTruncatePipeModule } from '../../../../../_pipe/truncate-pipe.module';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule, MatOptionModule, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FuseSharedModule } from '@fuse/shared.module';
import { ReactiveFormsModule } from '@angular/forms';
import { MatExpansionModule } from '@angular/material';

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
    MatButtonModule,
    MatExpansionModule,

    ReactiveFormsModule,

    FuseSharedModule,
    FuseTruncatePipeModule,
  ],
  declarations: [FlatPropertyBuilderComponent],
  exports: [FlatPropertyBuilderComponent]
})
export class FlatPropertyBuilderModule { }
