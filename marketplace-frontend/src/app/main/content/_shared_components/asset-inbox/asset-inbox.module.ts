import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MatButtonModule } from "@angular/material/button";
import { MatCommonModule } from "@angular/material/core";
import { MatIconModule } from "@angular/material/icon";
import { AssetInboxComponent } from "./asset-inbox.component";
import { MatTableModule, MatCheckboxModule } from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";

@NgModule({
  imports: [
    CommonModule,
    FuseSharedModule,
    MatCommonModule,
    MatButtonModule,
    MatIconModule,
    MatTableModule,
    MatCheckboxModule
  ],
  declarations: [AssetInboxComponent],
  exports: [AssetInboxComponent]
})
export class AssetInboxModule {}