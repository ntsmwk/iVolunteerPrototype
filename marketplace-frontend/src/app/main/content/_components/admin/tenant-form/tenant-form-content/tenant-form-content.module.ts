import { NgModule } from "@angular/core";
import { TenantFormContentComponent } from "./tenant-form-content.component";
import {
  MatButtonModule, MatIconModule, MatDatepickerModule, MatNativeDateModule, MatFormFieldModule, MatInputModule,
  MatTableModule, MatProgressSpinnerModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { TenantTagFormModule } from './tag-form/tag-form.module';
import { TenantHelpseekersFormModule } from './helpseekers-form/helpseekers-form.module';
import { ProfileImageUploadModule } from './profile-image-upload/profile-image-upload.module';
import { LandingPageImageUploadModule } from './landing-page-image-upload/landing-page-image-upload.module';


@NgModule({
  exports: [TenantFormContentComponent],
  declarations: [TenantFormContentComponent],
  imports: [
    FuseSharedModule,

    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,

    MaterialFileInputModule,

    TenantTagFormModule,
    ProfileImageUploadModule,
    LandingPageImageUploadModule,
  ],

  providers: [
  ],
})
export class TenantFormContentModule { }
