import { NgModule } from "@angular/core";

import { RouterModule } from "@angular/router";
import {
  MatButtonModule, MatTableModule, MatIconModule, MatCommonModule, MatTooltipModule,
  MatFormFieldModule, MatDatepickerModule, MatNativeDateModule, MatInputModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { ProfileFormComponent } from './profile-form.component';

const routes = [{ path: "", component: ProfileFormComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
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
  ],
  exports: [ProfileFormComponent],
  declarations: [ProfileFormComponent],
  providers: [],
})
export class ProfileFormModule { }
