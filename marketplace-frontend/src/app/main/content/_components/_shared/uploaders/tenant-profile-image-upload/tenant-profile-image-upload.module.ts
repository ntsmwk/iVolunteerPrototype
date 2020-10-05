import { NgModule } from "@angular/core";
import {
  MatButtonModule, MatIconModule, MatFormFieldModule, MatInputModule, MatProgressSpinnerModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { TenantProfileImageUploadComponent } from './tenant-profile-image-upload.component';


@NgModule({
  exports: [TenantProfileImageUploadComponent],
  declarations: [TenantProfileImageUploadComponent],
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
export class TenantProfileImageUploadModule { }
