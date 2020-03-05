import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DonutComponent } from './donut.component';
import { ShareMenuModule } from '../share-menu/share-menu.module';

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
