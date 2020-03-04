import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SunburstTableComponent } from './sunburst-table.component';
import { MatExpansionModule, MatTableModule, MatPaginatorModule } from '@angular/material';
import { ShareMenuModule } from '../share-menu/share-menu.module';
import { HighchartsChartModule } from 'highcharts-angular';
import { FlexLayoutModule } from '@angular/flex-layout';



@NgModule({
  declarations: [
    SunburstTableComponent
  ],
  imports: [
    CommonModule,

    MatExpansionModule,
    MatTableModule,
    MatPaginatorModule,
    ShareMenuModule,
    HighchartsChartModule,
    FlexLayoutModule



  ],
  exports: [
    SunburstTableComponent,
  ]
})
export class SunburstTableModule { }
