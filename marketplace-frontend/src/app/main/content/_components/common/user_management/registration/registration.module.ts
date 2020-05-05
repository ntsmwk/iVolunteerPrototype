import { NgModule } from "@angular/core";

import { FuseRegistrationComponent } from "./registration.component";
import { RouterModule } from "@angular/router";
import { FuseSharedModule } from "@fuse/shared.module";
import {
  MatButtonModule,
  MatCheckboxModule,
  MatFormFieldModule,
  MatInputModule,
  MatDatepickerModule,
  MatNativeDateModule,
} from "@angular/material";

const routes = [{ path: "", component: FuseRegistrationComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,

    FuseSharedModule,
  ],
  exports: [],
  declarations: [FuseRegistrationComponent],
  providers: [],
})
export class FuseRegistrationModule {}
