import { NgModule } from "@angular/core";

import { FuseRegistrationComponent } from "./registration.component";
import { RouterModule } from "@angular/router";
import { FuseSharedModule } from "@fuse/shared.module";

const routes = [{ path: "", component: FuseRegistrationComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes), FuseSharedModule],
  exports: [FuseRegistrationComponent],
  declarations: [FuseRegistrationComponent],
  providers: []
})
export class FuseRegistrationModule {}
