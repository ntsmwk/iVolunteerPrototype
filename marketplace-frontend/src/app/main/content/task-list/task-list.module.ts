import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {MatButtonModule, MatCheckboxModule, MatFormFieldModule, MatInputModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseTaskListComponent} from './task-list.component';

const routes = [
  {path: '', component: FuseTaskListComponent}
];

@NgModule({
  declarations: [
    FuseTaskListComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,

    FuseSharedModule
  ],
  exports: [
    FuseTaskListComponent
  ]
})
export class FuseTaskListModule {
}
