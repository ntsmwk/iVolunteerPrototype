import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";

import { FuseSharedModule } from "@fuse/shared.module";

import { DashboardComponent } from "./dashboard.component";
import { MatButtonModule } from "@angular/material/button";
import { MatCommonModule } from "@angular/material/core";
import { MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule, MatIcon } from "@angular/material/icon";
import { MatMenuModule } from "@angular/material/menu";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatRadioModule } from "@angular/material/radio";
import { MatSelectModule } from "@angular/material/select";
import { MatTableModule } from "@angular/material/table";
import { MatTabsModule } from "@angular/material/tabs";
import { MatTooltipModule } from "@angular/material/tooltip";
import { FuseWidgetModule } from "@fuse/components";
import { MatBadgeModule, MatPaginatorModule, MatSortModule, MatCardModule } from "@angular/material";
import { FuseTruncatePipeModule } from "app/main/content/_pipe/truncate-pipe.module";
import { HeaderModule } from "app/main/content/_components/_shared/header/header.module";
import { DialogFactoryModule } from "app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.module";

const routes = [
  {
    path: "",
    component: DashboardComponent
  }
];

@NgModule({
  declarations: [
    DashboardComponent,
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,
    HeaderModule,
    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatTabsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCommonModule,
    MatDividerModule,
    MatMenuModule,
    MatTooltipModule,
    MatDialogModule,
    MatRadioModule,
    MatProgressSpinnerModule,
    MatBadgeModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    FuseTruncatePipeModule,

    DialogFactoryModule,

    FuseSharedModule,
    FuseWidgetModule
  ],
  exports: [DashboardComponent],
})
export class FuseDashboardModule { }
