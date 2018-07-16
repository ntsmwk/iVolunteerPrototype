import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatIconModule} from '@angular/material';
import {FuseProjectsComponent} from './projects.component';

const routes = [
  {path: '**', component: FuseProjectsComponent}
];

@NgModule({
  declarations: [
    FuseProjectsComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,

    FuseSharedModule
  ]
})
export class FuseProjectsModule {
}
