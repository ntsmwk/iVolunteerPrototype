import { NgModule } from "@angular/core";

import { ProfileComponent } from "./profile.component";
import { RouterModule } from "@angular/router";
import {
  MatButtonModule,
  MatTableModule,
  MatIconModule,
  MatTabsModule,
  MatCommonModule,
  MatDividerModule,
  MatMenuModule,
  MatTooltipModule,
  MatSortModule,
  MatCardModule,
  MatPaginatorModule
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { FuseWidgetModule } from "@fuse/components";

const routes = [{ path: "", component: ProfileComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatTabsModule,
    MatCommonModule,
    MatDividerModule,
    MatMenuModule,
    MatTooltipModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    FuseSharedModule,
    FuseWidgetModule
  ],
  exports: [],
  declarations: [ProfileComponent],
  providers: []
})
export class ProfileModule {}
