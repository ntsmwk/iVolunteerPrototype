import { NgModule } from "@angular/core";

import { HeaderComponent } from "./header.component";
import { FuseSharedModule } from "@fuse/shared.module";
import { CommonModule } from "@angular/common";
import { MatIconModule } from "@angular/material";

@NgModule({
  imports: [CommonModule, FuseSharedModule, MatIconModule],
  exports: [HeaderComponent],
  declarations: [HeaderComponent],
  providers: [],
})
export class HeaderModule {}
