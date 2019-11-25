import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
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
