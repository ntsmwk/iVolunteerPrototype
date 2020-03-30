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
import { MatIconModule, MatTableModule } from "@angular/material";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { FuseTenantFormComponent } from "../tenant-form/tenant-form.component";
import { FuseTenantFormModule } from "../tenant-form/tenant-form.module";

const routes: Route[] = [
  { path: "", component: FuseMarketplaceFormComponent },
  { path: ":marketplaceId", component: FuseMarketplaceFormComponent }
];

@NgModule({
  declarations: [FuseMarketplaceFormComponent, FuseTenantListComponent],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    FuseSharedModule
  ]
})
export class FuseMarketplaceFormModule {}
