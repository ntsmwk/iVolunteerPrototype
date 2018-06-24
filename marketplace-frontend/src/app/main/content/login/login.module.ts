import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {MatButtonModule, MatCheckboxModule, MatFormFieldModule, MatInputModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseLoginComponent} from './login.component';
import {LoginService} from '../_service/login.service';

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
  ],
  exports: [
    FuseLoginComponent
  ]
})
export class FuseLoginModule {
}
