import { NgModule } from "@angular/core";
import { ProfileImageUploadComponent } from "./profile-image-upload.component";
import {
  MatButtonModule, MatIconModule, MatFormFieldModule, MatInputModule, MatProgressSpinnerModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { MaterialFileInputModule } from 'ngx-material-file-input';


@NgModule({
  exports: [ProfileImageUploadComponent],
  declarations: [ProfileImageUploadComponent],
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
export class ProfileImageUploadModule { }
