import { NgModule } from "@angular/core";

import { ProfileComponent } from "./profile.component";
import { RouterModule } from "@angular/router";

const routes = [{ path: "", component: ProfileComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [],
  declarations: [ProfileComponent],
  providers: []
})
export class ProfileModule {}
