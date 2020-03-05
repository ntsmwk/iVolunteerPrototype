import { NgModule } from "@angular/core";

import { FuseProjectMembersComponent } from "./project-members.component";
import { FuseSharedModule } from "@fuse/shared.module";

@NgModule({
  declarations: [FuseProjectMembersComponent],
  imports: [FuseSharedModule],
  exports: [FuseProjectMembersComponent]
})
export class FuseProjectMembersModule {}
