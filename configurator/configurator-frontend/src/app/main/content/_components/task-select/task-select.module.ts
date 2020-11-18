import { NgModule } from "@angular/core";
import { Route, RouterModule } from "@angular/router";
import { MatButtonModule } from "@angular/material/button";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { DateAdapter, MatNativeDateModule } from "@angular/material/core";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatDividerModule } from "@angular/material/divider";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { MatSelectModule } from "@angular/material/select";
import { FuseSharedModule } from "@fuse/shared.module";
import { GermanDateAdapter } from "../../_adapter/german-date-adapter";
import { FuseTaskSelectComponent } from "./task-select.component";
import { MatTableModule } from "@angular/material";
import { HeaderModule } from "app/main/content/_components/_shared/header/header.module";

const routes: Route[] = [{ path: "", component: FuseTaskSelectComponent }];

@NgModule({
  declarations: [FuseTaskSelectComponent],
  imports: [
    RouterModule.forChild(routes),
    MatButtonModule,
    MatTableModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDividerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    HeaderModule,
    FuseSharedModule,
  ],
  providers: [{ provide: DateAdapter, useClass: GermanDateAdapter }],
})
export class FuseTaskSelectModule { }
