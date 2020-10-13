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
  MatTableModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { TenantHelpseekersFormModule } from './tenant-form-content/helpseekers-form/helpseekers-form.module';
import { TenantFormContentModule } from './tenant-form-content/tenant-form-content.module';

const routes: Route[] = [
  { path: "", component: FuseTenantFormComponent },
  { path: ":tenantId", component: FuseTenantFormComponent },
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
    FuseSharedModule,
    TenantFormContentModule,
    TenantHelpseekersFormModule,
  ],

  providers: [],
})
export class FuseTenantFormModule { }
