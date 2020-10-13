import { NgModule } from "@angular/core";
import { UserProfileImageUploadComponent } from "./user-profile-image-upload.component";
import {
  MatButtonModule, MatIconModule, MatFormFieldModule, MatInputModule, MatProgressSpinnerModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { MaterialFileInputModule } from 'ngx-material-file-input';


@NgModule({
  exports: [UserProfileImageUploadComponent],
  declarations: [UserProfileImageUploadComponent],
  imports: [
    FuseSharedModule,

    MatButtonModule,
    MatIconModule,

    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,

    MaterialFileInputModule,

  ],

  providers: [
  ],
})
export class UserProfileImageUploadModule { }
