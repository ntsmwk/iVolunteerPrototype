import { NgModule } from "@angular/core";

import { FuseTenantListComponent } from "./tenant-list.component";
import { FuseSharedModule } from "@fuse/shared.module";
import { RouterModule } from "@angular/router";
import {
  MatFormFieldModule,
  MatIconModule,
  MatTableModule,
  MatButtonModule
} from "@angular/material";

const routes = [{ path: "", component: FuseTenantListComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),

    MatFormFieldModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,
    FuseSharedModule
  ],
  exports: [],
  declarations: [FuseTenantListComponent],
  providers: []
})
export class FuseTenantListModule {}
