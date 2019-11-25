import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseGroupDetailComponent} from './group-detail.component';
import {FuseGroupMemberComponent} from './sidenavs/group-member/group-member.component';

const routes: Route[] = [
  {path: ':groupId', component: FuseGroupDetailComponent}
];

@NgModule({
  declarations: [
    FuseGroupDetailComponent,

    FuseGroupMemberComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatIconModule,
    MatSidenavModule,

    FuseSharedModule
  ]
})
export class FuseGroupDetailModule {
}
