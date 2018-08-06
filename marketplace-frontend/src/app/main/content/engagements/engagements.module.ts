import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseEngagementsComponent} from './engagements.component';
import {MatButtonModule, MatDividerModule, MatIconModule, MatTabsModule} from '@angular/material';

const routes: Routes = [
  {
    path: '**', component: FuseEngagementsComponent
  }
];

@NgModule({
  declarations: [
    FuseEngagementsComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    MatTabsModule,

    FuseSharedModule
  ]
})

export class FuseEngagementsModule {
}
