import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseGetConnectedComponent} from './get-connected.component';
import {FuseWidgetModule} from '../../../../@fuse/components';
import {MatButtonModule, MatDividerModule, MatIconModule, MatTabsModule} from '@angular/material';

const routes: Routes = [
  {path: '', component: FuseGetConnectedComponent},
  {path: 'profile', loadChildren: '../profile/profile.module#FuseProfileModule'},
  {path: 'group', loadChildren: '../group-detail/group-detail.module#FuseGroupDetailModule'},
  {path: 'group-form', loadChildren: '../group-form/group-form.module#FuseGroupFormModule'}
];

@NgModule({
  declarations: [
    FuseGetConnectedComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    MatTabsModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    FuseWidgetModule,
    FuseSharedModule
  ]
})

export class FuseGetConnectedModule {
}
