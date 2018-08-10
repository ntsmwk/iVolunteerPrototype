import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {MatButtonModule, MatIconModule, MatTableModule} from '@angular/material';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTaskListComponent} from './task-list.component';
import {FuseTruncatePipeModule} from '../_pipe/truncate-pipe.module';

const routes: Route[] = [
  {path: '', component: FuseTaskListComponent}
];

@NgModule({
  declarations: [
    FuseTaskListComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatTableModule,
    MatIconModule,

    FuseSharedModule,
    FuseTruncatePipeModule
  ]
})
export class FuseTaskListModule {
}
