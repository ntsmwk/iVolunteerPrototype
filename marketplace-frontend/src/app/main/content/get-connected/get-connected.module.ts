import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseGetConnectedComponent} from './get-connected.component';

const routes: Routes = [
  {path: '', component: FuseGetConnectedComponent},
  {path: 'profile', loadChildren: '../profile/profile.module#FuseProfileModule'}
];

@NgModule({
  declarations: [
    FuseGetConnectedComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    FuseSharedModule
  ]
})

export class FuseGetConnectedModule {
}
