import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {MatButtonModule, MatIconModule, MatSidenavModule} from '@angular/material';
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
