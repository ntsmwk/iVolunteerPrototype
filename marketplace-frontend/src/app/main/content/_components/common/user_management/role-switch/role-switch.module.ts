import { NgModule } from "@angular/core";
import { RoleSwitchComponent } from "./role-switch.component";
import { RouterModule } from "@angular/router";
import {
  MatButtonModule,
  MatCheckboxModule,
  MatCommonModule,
  MatCardContent,
  MatCardModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";

const routes = [{ path: "", component: RoleSwitchComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    MatButtonModule,
    MatCommonModule,
    MatCardModule,

    FuseSharedModule,
  ],

  exports: [],
  declarations: [RoleSwitchComponent],
  providers: [],
})
export class FuseRoleSwitchModule {}
