import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { CommonModule } from "@angular/common";
import { FuseSharedModule } from "@fuse/shared.module";
import { ClassInstanceDetailsComponent } from "./class-instance-details.component";
import {
  MatIconModule,
  MatTableModule,
  MatSortModule,
  MatDialogModule,
  MAT_DIALOG_DATA,
  MatDialogRef,
} from "@angular/material";
import { HeaderModule } from "app/main/content/_components/_shared/header/header.module";

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
    MatDialogModule
  ],
  providers: [
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MatDialogRef, useValue: {} }
  ],
  declarations: [ClassInstanceDetailsComponent],
})
export class ClassInstanceDetailsModule { }
