import { NgModule } from "@angular/core";
import { FuseSharedModule } from "@fuse/shared.module";
import { AssetInboxHelpseekerComponent } from "./asset-inbox-helpseeker.component";
import { CommonModule } from "@angular/common";
import { MatButtonModule } from "@angular/material";
import { RouterModule } from "@angular/router";
import { AssetInboxModule } from "../../shared/asset-inbox/asset-inbox.module";

const routes = [{ path: "", component: AssetInboxHelpseekerComponent }];

@NgModule({
  declarations: [AssetInboxHelpseekerComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FuseSharedModule,
    MatButtonModule,

    AssetInboxModule
  ],
  exports: [AssetInboxHelpseekerComponent]
})
export class AssetInboxHelpseekerModule { }
