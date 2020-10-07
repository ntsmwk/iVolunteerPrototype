import { NgModule } from "@angular/core";

import {
  MatButtonModule,
  MatIconModule,
  MatCommonModule,
  MatTooltipModule,
  MatFormFieldModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatInputModule,
  MatCardModule,
  MatSelectModule,
  MatOptionModule,
  MatDividerModule,
  MatCheckboxModule,
  MatExpansionModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { ProfileFormComponent } from "./profile-form.component";

@NgModule({
  imports: [
    FuseSharedModule,
    MatCommonModule,
    MatButtonModule,
    MatIconModule,
    MatTooltipModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatSelectModule,
    MatOptionModule,
    MatDividerModule,
    MatCheckboxModule,
    MatExpansionModule,
  ],
  exports: [ProfileFormComponent],
  declarations: [ProfileFormComponent],
  providers: [],
})
export class ProfileFormModule {}
