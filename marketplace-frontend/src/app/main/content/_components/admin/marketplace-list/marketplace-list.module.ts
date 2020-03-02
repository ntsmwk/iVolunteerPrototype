import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

import { FuseSharedModule } from "@fuse/shared.module";

import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatTableModule } from "@angular/material/table";
import { FuseMarketplaceListComponent } from "./marketplace-list.component";

const routes = [{ path: "", component: FuseMarketplaceListComponent }];

@NgModule({
  declarations: [FuseMarketplaceListComponent],
  imports: [
    RouterModule.forChild(routes),

    MatFormFieldModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,

    FuseSharedModule
  ]
})
export class FuseMarketplaceListModule {}
