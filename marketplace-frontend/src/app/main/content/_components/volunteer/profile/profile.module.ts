import { NgModule } from "@angular/core";

import { VolunteerProfileComponent } from "./profile.component";
import { RouterModule } from "@angular/router";

const routes = [{ path: "", component: VolunteerProfileComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [],
  declarations: [VolunteerProfileComponent],
  providers: []
})
export class VolunteeProfileModule {}
