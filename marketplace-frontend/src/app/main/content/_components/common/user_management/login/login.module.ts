import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

import { MatButtonModule } from "@angular/material/button";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";

import { FuseSharedModule } from "@fuse/shared.module";

import { FuseLoginComponent } from "./login.component";
import { ResendLinkModule } from '../activation/resend-link/resend-link.module';

const routes = [{ path: "", component: FuseLoginComponent }];

@NgModule({
  declarations: [FuseLoginComponent],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    ResendLinkModule,

    FuseSharedModule
  ]
})
export class FuseLoginModule { }
