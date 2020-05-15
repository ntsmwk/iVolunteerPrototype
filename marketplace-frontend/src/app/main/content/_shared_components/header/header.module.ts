import { NgModule } from "@angular/core";

import { HeaderComponent } from "./header.component";
import { FuseSharedModule } from "@fuse/shared.module";
import { CommonModule } from "@angular/common";

@NgModule({
  imports: [CommonModule, FuseSharedModule],
  exports: [HeaderComponent],
  declarations: [HeaderComponent],
  providers: [],
})
export class HeaderModule {}
