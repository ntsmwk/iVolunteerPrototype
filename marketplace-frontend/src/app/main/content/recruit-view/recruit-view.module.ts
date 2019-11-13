import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '../../../../@fuse/shared.module';

import {RecruitViewComponent} from './recruit-view.component';
import { MatCommonModule, MatTabsModule, MatSelectModule, MatInputModule, MatButtonModule, MatFormFieldModule, MatTableModule, MatExpansionModule, MatIconModule, MatProgressBar, MatProgressBarModule, MatProgressSpinner, MatProgressSpinnerModule } from '@angular/material';



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
  

    FuseSharedModule
  ],
  exports: [
    RecruitViewComponent
  ]
})

export class RecruitViewModule {
}
