import { NgModule } from "@angular/core";

import { OrganisationFilterComponent } from "./organisation-filter.component";
import { ImageService } from "../../_service/image.service";
import { CommonModule } from "@angular/common";
import { FuseSharedModule } from "@fuse/shared.module";
import { MatButtonModule } from "@angular/material";

@NgModule({
  imports: [CommonModule, FuseSharedModule, MatButtonModule],
  exports: [OrganisationFilterComponent],
  declarations: [OrganisationFilterComponent],
  providers: []
})
export class OrganisationFilterModule {}
