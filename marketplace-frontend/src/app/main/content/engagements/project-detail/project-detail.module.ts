import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatFormFieldModule, MatIconModule, MatInputModule, MatSidenavModule} from '@angular/material';
import {FuseProjectDetailComponent} from './project-detail.component';
import {FuseProjectMemberComponent} from './sidenavs/project-member/project-member.component';

const routes = [
  {path: '**', component: FuseProjectDetailComponent}
];

@NgModule({
  declarations: [
    FuseProjectDetailComponent,
    FuseProjectMemberComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
    MatSidenavModule,

    FuseSharedModule
  ]
})
export class FuseProjectDetailModule {
}
