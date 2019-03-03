import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatFormFieldModule, MatIconModule, MatTableModule, MatCheckboxModule} from '@angular/material';
import {FuseBCEntryListComponent} from './bcEntry-list.component';

const routes = [
  {path: '', component: FuseBCEntryListComponent}
];

@NgModule({
  declarations: [
    FuseBCEntryListComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatFormFieldModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,
    MatCheckboxModule,

    FuseSharedModule
  ]
})
export class FuseBCEntryListModule {
}
