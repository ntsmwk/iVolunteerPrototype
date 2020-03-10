import { NgModule } from "@angular/core";

import { FuseTenantListComponent } from "./tenant-list.component";
import { FuseSharedModule } from "@fuse/shared.module";

@NgModule({
  imports: [FuseSharedModule],
  exports: [],
  declarations: [FuseTenantListComponent],
  providers: []
})
export class FuseTenantListModule {}
