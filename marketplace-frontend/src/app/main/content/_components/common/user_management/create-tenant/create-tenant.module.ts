import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuseSharedModule } from '@fuse/shared.module';
import { CreateTenantComponent } from './create-tenant.component';
import { MatCommonModule, MatCardModule, MatButtonModule } from '@angular/material';
import { TenantHelpseekersFormModule } from '../../../admin/tenant-form/tenant-form-content/helpseekers-form/helpseekers-form.module';
import { TenantFormContentModule } from '../../../admin/tenant-form/tenant-form-content/tenant-form-content.module';

const routes = [
  {
    path: '',
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
    MatButtonModule,
    TenantFormContentModule,
    TenantHelpseekersFormModule,


  ],
  exports: [CreateTenantComponent],
})
export class CreateTenantModule { }
