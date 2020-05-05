import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { FuseSharedModule } from '@fuse/shared.module';
import { FuseTaskListComponent } from './task-list.component';
import { FuseTruncatePipeModule } from '../../../../_pipe/truncate-pipe.module';
import {
  MatPaginatorModule,
  MatSortModule,
  MatSelectModule,
  MatTabsModule,
  MatChipsModule,
  MatFormFieldModule,
  MatInputModule,
  MatSidenavModule,
  MatDividerModule,
  MatCheckboxModule
} from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';

const routes: Route[] = [{ path: '', component: FuseTaskListComponent }];

@NgModule({
  declarations: [FuseTaskListComponent],
  imports: [
    RouterModule.forChild(routes),

    ReactiveFormsModule,
    RouterModule.forChild(routes),
    MatSelectModule,
    MatTabsModule,
    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatDividerModule,
    MatTableModule,
    MatCheckboxModule,
    FuseSharedModule,
    MatPaginatorModule,
    MatSortModule,

    FuseSharedModule,
    FuseTruncatePipeModule
  ]
})
export class FuseTaskListModule { }
