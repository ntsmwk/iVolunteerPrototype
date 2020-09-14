import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

import { FuseSharedModule } from "@fuse/shared.module";
import { CreateTenantComponent } from './create-tenant.component';
import { MatCommonModule, MatCardModule } from '@angular/material';
import { FuseTenantFormModule } from '../../../admin/tenant-form/tenant-form.module';
import { TenantFormContentModule } from '../../../admin/tenant-form/tenant-form-content/tenant-form-content.module';

const routes = [
  {
    path: "",
    component: CreateTenantComponent,
  }
];

@NgModule({
  declarations: [
    CreateTenantComponent,
  ],
  imports: [
    RouterModule.forChild(routes),
    FuseSharedModule,
    MatCommonModule,
    MatCardModule,
    TenantFormContentModule,

  ],
  exports: [CreateTenantComponent],
})
export class CreateTenantModule { }
