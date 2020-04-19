import { NgModule } from "@angular/core";

import { ImportComponent } from "./import.component";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";
import {
  MatSelectModule,
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";

const routes = [{ path: "", component: ImportComponent }];

@NgModule({
  imports: [
    ReactiveFormsModule,
    RouterModule.forChild(routes),

    MatSelectModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    FuseSharedModule,
  ],
  exports: [],
  declarations: [ImportComponent],
  providers: [],
})
export class ImportModule {}
