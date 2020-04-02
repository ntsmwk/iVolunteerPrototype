import { NgModule } from "@angular/core";

import { VolunteerProfileComponent } from "./profile.component";
import { RouterModule } from "@angular/router";
import { LocalRepositoryComponent } from '../local-repository/local-repository.component';

const routes = [{ path: "", component: VolunteerProfileComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
  ],
  exports: [],
  declarations: [
    VolunteerProfileComponent,
    LocalRepositoryComponent],
  providers: []
})
export class VolunteeProfileModule { }
