import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseGetConnectedComponent} from './get-connected.component';
import {FuseWidgetModule} from '../../../../@fuse/components';
import {MatButtonModule, MatDividerModule, MatIconModule, MatTabsModule} from '@angular/material';

const routes: Routes = [
  {path: '', component: FuseGetConnectedComponent},
  {path: 'group', loadChildren: '../group-detail/group-detail.module#FuseGroupDetailModule'}
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
