import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatIconModule} from '@angular/material';
import {FuseProjectDetailComponent} from './project-detail.component';

const routes = [
  {path: '**', component: FuseProjectDetailComponent}
];

@NgModule({
  declarations: [
    FuseProjectDetailComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,

    FuseSharedModule
  ]
})
export class FuseProjectDetailModule {
}
