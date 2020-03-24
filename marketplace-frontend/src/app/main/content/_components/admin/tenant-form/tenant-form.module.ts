import { NgModule } from "@angular/core";
import { FuseTenantFormComponent } from "./tenant-form.component";
import { Route, RouterModule } from "@angular/router";

const routes: Route[] = [{ path: "", component: FuseTenantFormComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [],
  declarations: [FuseTenantFormComponent],
  providers: []
})
export class FuseTenantFormModule {}
