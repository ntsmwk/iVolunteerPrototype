import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FlexLayoutModule } from '@angular/flex-layout';
import { ShareMenuModule } from '../share-menu/share-menu.module';
import { DonutComponent } from './donut.component';

@NgModule({
  declarations: [
    DonutComponent
  ],
  imports: [
    CommonModule,

    NgxChartsModule,
    FlexLayoutModule,
    ShareMenuModule

  ],
  exports: [
    DonutComponent,
  ]
})
export class DonutModule { }
