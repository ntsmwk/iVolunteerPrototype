import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

import { FuseSharedModule } from "@fuse/shared.module";
import { CreateTenantComponent } from './create-tenant.component';

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
  ],
  exports: [CreateTenantComponent],
})
export class CreateTenantModule { }
