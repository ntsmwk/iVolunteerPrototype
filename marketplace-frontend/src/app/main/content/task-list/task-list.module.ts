import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTaskListComponent} from './task-list.component';
import {FuseTruncatePipeModule} from '../_pipe/truncate-pipe.module';
import { MatPaginatorModule, MatSortModule } from '@angular/material';

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
    MatButtonModule,    
    MatPaginatorModule,
    MatSortModule,



    FuseSharedModule,
    FuseTruncatePipeModule
  ]
})
export class FuseTaskListModule {
}
