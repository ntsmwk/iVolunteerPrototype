import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';

import {FuseTaskTemplateListComponent} from './task-template-list.component';
import {FuseTruncatePipeModule} from '../../../../_pipe/truncate-pipe.module';

const routes = [
  {path: '', component: FuseTaskTemplateListComponent}
];

@NgModule({
  declarations: [
    FuseTaskTemplateListComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,
    MatTableModule,
    MatButtonModule,
    FuseSharedModule,
    FuseTruncatePipeModule
  ]
})
export class FuseTaskTemplateListModule {
}
