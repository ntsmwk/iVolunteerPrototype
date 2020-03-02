import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '../../../../../../@fuse/shared.module';

import {RecruitViewComponent} from './recruit-view.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBar, MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinner, MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { MatPaginatorModule, MatSortModule } from '@angular/material';



const routes = [
  {
      path     : '',
      component: RecruitViewComponent
  }
];

@NgModule({
  declarations: [
    RecruitViewComponent,
  ],
  imports: [
    RouterModule.forChild(routes),

    MatCommonModule,
    MatTabsModule,
    
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,
    MatTableModule,
    MatIconModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,

    MatExpansionModule,
    NgxChartsModule,
    MatPaginatorModule,
    MatSortModule,

    FuseSharedModule
  ],
  exports: [
    RecruitViewComponent
  ]
})

export class RecruitViewModule {
}
