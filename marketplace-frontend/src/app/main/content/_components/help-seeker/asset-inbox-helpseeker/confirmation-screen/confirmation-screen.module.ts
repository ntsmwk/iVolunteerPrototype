import { NgModule } from "@angular/core";
import { FuseSharedModule } from "@fuse/shared.module";
import { HelpseekerConfirmationScreenComponent } from "./confirmation-screen.component";
import { CommonModule } from "@angular/common";
import { MatButtonModule } from "@angular/material";
import { RouterModule } from "@angular/router";

const routes = [{ path: "", component: HelpseekerConfirmationScreenComponent }];

@NgModule({
  declarations: [HelpseekerConfirmationScreenComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FuseSharedModule,
    MatButtonModule,
    FuseSharedModule
  ],
  exports: [HelpseekerConfirmationScreenComponent]
})
export class HelpseekerConfirmationScreenModule {}
