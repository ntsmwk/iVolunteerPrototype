import { NgModule } from "@angular/core";

import { ImportComponent } from "./import.component";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";
import { MaterialFileInputModule } from "ngx-material-file-input";
import {
  MatSelectModule,
  MatButtonModule,
  MatFormFieldModule,
  MatInputModule,
  MatCardModule,
  MatIconModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { HeaderModule } from "app/main/content/_components/_shared/header/header.module";

const routes = [{ path: "", component: ImportComponent }];

@NgModule({
  imports: [
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    HeaderModule,
    MatCardModule,
    MatSelectModule,
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MaterialFileInputModule,
    FuseSharedModule,
  ],
  exports: [],
  declarations: [ImportComponent],
  providers: [],
})
export class ImportModule { }
