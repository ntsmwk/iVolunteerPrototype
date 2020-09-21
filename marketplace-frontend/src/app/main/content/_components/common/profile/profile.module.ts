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
  MatPaginatorModule,
  MatCard,
  MatFormFieldModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatInputModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { FuseWidgetModule } from "@fuse/components";
import { LocalRepositoryLocationSwitchModule } from "../../volunteer/local-repository-location-switch/local-repository-location-switch.module";
import { ProfileFormComponent } from './profile-form/profile-form.component';

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
    MatNativeDateModule,
    MatDatepickerModule,
    MatSortModule,
    MatFormFieldModule,
    MatCardModule,
    MatInputModule,
    FuseSharedModule,
    MatCardModule,
    FuseWidgetModule,
    ProfileFormComponent,

    LocalRepositoryLocationSwitchModule,
  ],
  exports: [],
  declarations: [ProfileComponent],
  providers: [],
})
export class ProfileModule { }
