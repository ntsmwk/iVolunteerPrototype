import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {MatSidenavModule} from '@angular/material';
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

    MatSidenavModule,

    FuseSharedModule
  ]
})
export class FuseGroupDetailModule {
}
