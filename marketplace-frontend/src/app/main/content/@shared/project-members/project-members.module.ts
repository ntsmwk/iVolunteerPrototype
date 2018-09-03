import {NgModule} from '@angular/core';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseProjectMembersComponent} from './project-members.component';

@NgModule({
  declarations: [FuseProjectMembersComponent  ],
  imports: [
    FuseSharedModule
  ],
  exports: [FuseProjectMembersComponent]
})
export class FuseProjectMembersModule {
}
