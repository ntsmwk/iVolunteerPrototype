import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {MatButtonModule, MatCheckboxModule, MatFormFieldModule, MatInputModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseLoginComponent} from './login.component';

const routes = [
  {path: '', component: FuseLoginComponent}
];

@NgModule({
  declarations: [
    FuseLoginComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,

    FuseSharedModule
  ]
})
export class FuseLoginModule {
}
