import {NgModule} from '@angular/core';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatDividerModule, MatExpansionModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSidenavModule} from '@angular/material';
import {FuseProjectsComponent} from './projects.component';
import {FuseProjectMemberComponent} from './sidenavs/project-member/project-member.component';

@NgModule({
  declarations: [
    FuseProjectsComponent,
    FuseProjectMemberComponent
  ],
  imports: [
    MatDividerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,

    FuseSharedModule
  ],
  exports: [FuseProjectsComponent]
})
export class FuseProjectsModule {
}
