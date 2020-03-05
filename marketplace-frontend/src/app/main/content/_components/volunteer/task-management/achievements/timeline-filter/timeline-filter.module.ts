import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TimelineFilterComponent } from './timeline-filter.component';
import { MatOptionModule, MatButtonModule, MatFormFieldModule, MatSelectModule, MatButtonToggleModule, MatChipsModule, MatIconModule } from '@angular/material';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
  declarations: [
    TimelineFilterComponent
  ],
  imports: [
    CommonModule,

    MatOptionModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    NgxChartsModule,
    MatButtonToggleModule,
    FlexLayoutModule,
    MatChipsModule,
    MatIconModule

  ],
  exports: [
    TimelineFilterComponent,
  ]
})
export class TimelineFilterModule { }
