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
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { FuseWidgetModule } from "@fuse/components";
import { VolunteerProfileComponent } from "./volunteer-profile/volunteer-profile.component";
import { HelpSeekerProfileComponent } from "./helpseeker-profile/helpseeker-profile.component";
import { ProfileAboutComponent } from "./volunteer-profile/about/about.component";
import { ProfilePhotosVideosComponent } from "./volunteer-profile/photos-videos/photos-videos.component";
import { ProfileTimelineComponent } from "./volunteer-profile/timeline/timeline.component";

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
    FuseSharedModule,
    MatCardModule,
    FuseWidgetModule,
  ],
  exports: [],
  declarations: [
    ProfileComponent,
    VolunteerProfileComponent,
    HelpSeekerProfileComponent,
    ProfileAboutComponent,
    ProfilePhotosVideosComponent,
    ProfileTimelineComponent,
  ],
  providers: [],
})
export class ProfileModule {}
