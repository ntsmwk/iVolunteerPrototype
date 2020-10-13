import { NgModule } from "@angular/core";

import { OrganisationFilterComponent } from "./organisation-filter.component";
import { CommonModule } from "@angular/common";
import { FuseSharedModule } from "@fuse/shared.module";
import { MatButtonModule, MatTooltipModule } from "@angular/material";

@NgModule({
  imports: [CommonModule, FuseSharedModule, MatButtonModule, MatTooltipModule],
  exports: [OrganisationFilterComponent],
  declarations: [OrganisationFilterComponent],
  providers: []
})
export class OrganisationFilterModule {}
