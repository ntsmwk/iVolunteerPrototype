import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FuseSharedModule } from "@fuse/shared.module";
import { ClassInstanceDetailsComponent } from "./class-instance-details.component";
import {
  MatIconModule,
  MatTableModule,
  MatSortModule,
} from "@angular/material";
import { HeaderModule } from "app/main/content/_shared_components/header/header.module";

const routes = [{ path: ":id", component: ClassInstanceDetailsComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    CommonModule,

    HeaderModule,
    MatSortModule,
    MatIconModule,
    MatTableModule,
    FuseSharedModule,
  ],
  declarations: [ClassInstanceDetailsComponent],
})
export class ClassInstanceDetailsModule {}
