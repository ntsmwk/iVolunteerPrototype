import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {MatIconModule, MatTableModule, MatButtonModule} from '@angular/material';

import {FuseTaskTemplateListComponent} from './task-template-list.component';
import {FuseTruncatePipeModule} from '../_pipe/truncate-pipe.module';

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
