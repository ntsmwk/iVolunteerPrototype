import { NgModule } from "@angular/core";
import { RoleSwitchComponent } from "./role-switch.component";
import { RouterModule } from "@angular/router";
import {
  MatButtonModule,
  MatCommonModule,
  MatCardModule,
  MatProgressSpinnerModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";

const routes = [{ path: "", component: RoleSwitchComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    MatButtonModule,
    MatCommonModule,
    MatCardModule,
    MatProgressSpinnerModule,

    FuseSharedModule,
  ],

  exports: [],
  declarations: [RoleSwitchComponent],
  providers: [],
})
export class FuseRoleSwitchModule { }
