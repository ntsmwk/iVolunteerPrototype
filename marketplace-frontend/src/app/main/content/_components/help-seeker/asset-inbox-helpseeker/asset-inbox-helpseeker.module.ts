import { NgModule } from "@angular/core";
import { FuseSharedModule } from "@fuse/shared.module";
import { AssetInboxHelpseekerComponent } from "./asset-inbox-helpseeker.component";
import { CommonModule } from "@angular/common";
import {
  MatButtonModule,
  MatTableModule,
  MatCommonModule,
  MatIconModule,
  MatCheckboxModule
} from "@angular/material";
import { RouterModule } from "@angular/router";
import { HeaderModule } from "../../_shared/header/header.module";

const routes = [{ path: "", component: AssetInboxHelpseekerComponent }];

@NgModule({
  declarations: [AssetInboxHelpseekerComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FuseSharedModule,
    MatButtonModule,
    HeaderModule,
    MatIconModule,
    MatTableModule,
    MatCheckboxModule
  ],
  exports: [AssetInboxHelpseekerComponent]
})
export class AssetInboxHelpseekerModule {}
