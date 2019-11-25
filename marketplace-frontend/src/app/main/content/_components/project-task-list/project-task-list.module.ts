import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSidenavModule } from '@angular/material/sidenav';
import {FuseProjectTaskListComponent} from './project-task-list.component';
import {FuseTruncatePipeModule} from '../../_pipe/truncate-pipe.module';
import {FuseWidgetModule} from '../../../../../@fuse/components';


@NgModule({
  declarations: [
    FuseProjectTaskListComponent
  ],
  imports: [
    RouterModule,

    MatButtonModule,
    MatDividerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatProgressBarModule,

    FuseSharedModule,
    FuseTruncatePipeModule,
    FuseWidgetModule
  ],
  exports: [FuseProjectTaskListComponent]
})
export class FuseProjectTaskListModule {
}
