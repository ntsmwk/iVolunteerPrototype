import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { ReactiveFormsModule } from "@angular/forms";

import { FuseTaskDetailComponent } from "./task-detail.component";
import { FuseTaskTimelineComponent } from "./sidenavs/timeline/task-timeline.component";
import { FuseTaskAssignComponent } from "./assign/task-assign.component";
import { MatButtonModule } from "@angular/material/button";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatChipsModule } from "@angular/material/chips";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatTableModule } from "@angular/material/table";
import { FuseSharedModule } from "@fuse/shared.module";

const routes = [
  { path: ":marketplaceId/:taskId", component: FuseTaskDetailComponent }
];

@NgModule({
  declarations: [
    FuseTaskDetailComponent,
    FuseTaskTimelineComponent,
    FuseTaskAssignComponent
  ],
  imports: [
    ReactiveFormsModule,
    RouterModule.forChild(routes),

    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatDividerModule,
    MatTableModule,
    MatCheckboxModule,
    FuseSharedModule
  ]
})
export class FuseTaskDetailModule {}
