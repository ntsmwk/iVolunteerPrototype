import { NgModule } from "@angular/core";
import { FuseTenantFormComponent } from "./tenant-form.component";
import { Route, RouterModule } from "@angular/router";
import {
  MatButtonModule,
  MatIconModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatFormFieldModule,
  MatInputModule,
  MatTableModule
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";

const routes: Route[] = [
  { path: "", component: FuseTenantFormComponent },
  { path: ":tenantId", component: FuseTenantFormComponent }
];

@NgModule({
  exports: [],
  declarations: [FuseTenantFormComponent],
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
  ],

  providers: []
})
export class FuseTenantFormModule {}
