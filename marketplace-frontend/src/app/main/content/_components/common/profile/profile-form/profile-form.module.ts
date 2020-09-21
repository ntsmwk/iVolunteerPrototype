import { NgModule } from "@angular/core";

import { RouterModule } from "@angular/router";
import {
  MatButtonModule, MatTableModule, MatIconModule, MatCommonModule, MatTooltipModule,
  MatFormFieldModule, MatDatepickerModule, MatNativeDateModule, MatInputModule, MatCardModule, MatSelectModule, MatOptionModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { ProfileFormComponent } from './profile-form.component';


@NgModule({
  imports: [
    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatCommonModule,
    MatTooltipModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatInputModule,
    FuseSharedModule,
    MatCardModule,
    MatSelectModule,
    MatOptionModule,
  ],
  exports: [ProfileFormComponent],
  declarations: [ProfileFormComponent],
  providers: [],
})
export class ProfileFormModule { }
