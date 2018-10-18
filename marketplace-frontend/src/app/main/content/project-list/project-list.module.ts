import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatIconModule, MatTableModule, MatFormFieldModule, MatButtonModule} from '@angular/material';
import {FuseProjectListComponent} from './project-list.component';
import {FuseTruncatePipeModule} from '../_pipe/truncate-pipe.module';

const routes = [
  {path: '', component: FuseProjectListComponent}
];

@NgModule({
  declarations: [
    FuseProjectListComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    MatFormFieldModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,

    FuseSharedModule,
    FuseTruncatePipeModule
  ]
})
export class FuseProjectListModule {
}
