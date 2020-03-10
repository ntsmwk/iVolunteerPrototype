import { NgModule } from "@angular/core";
import { Route, RouterModule } from "@angular/router";
import { MatButtonModule } from "@angular/material/button";
import { MatNativeDateModule } from "@angular/material/core";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { FuseSharedModule } from "@fuse/shared.module";
import { FuseMarketplaceFormComponent } from "./marketplace-form.component";
import { FuseTenantListComponent } from "./tenant-list/tenant-list.component";

const routes: Route[] = [
  { path: "", component: FuseMarketplaceFormComponent },
  { path: ":marketplaceId", component: FuseMarketplaceFormComponent }
];

@NgModule({
  declarations: [FuseMarketplaceFormComponent, FuseTenantListComponent],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,

    FuseSharedModule
  ]
})
export class FuseMarketplaceFormModule {}
