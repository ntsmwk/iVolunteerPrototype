import { NgModule } from "@angular/core";

import { AlertComponent } from "./alert.component";
import { FuseSharedModule } from "@fuse/shared.module";

@NgModule({
  imports: [FuseSharedModule],
  exports: [AlertComponent],
  declarations: [AlertComponent],
  providers: []
})
export class AlertModule {}
