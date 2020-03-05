import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseGetConnectedComponent} from './get-connected.component';
import {FuseWidgetModule} from '../../../../../../../@fuse/components';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';

const routes: Routes = [
  {path: '', component: FuseGetConnectedComponent},
  {path: 'group', loadChildren: () => import(`../group-detail/group-detail.module`).then(m => m.FuseGroupDetailModule)}
 
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
