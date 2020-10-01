import { NgModule } from '@angular/core';
import { TenantFormContentComponent } from './tenant-form-content.component';
import {
  MatButtonModule, MatIconModule, MatDatepickerModule, MatNativeDateModule, MatFormFieldModule, MatInputModule,
  MatProgressSpinnerModule,
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { TenantTagFormModule } from './tag-form/tag-form.module';
import { TenantProfileImageUploadModule } from '../../../_shared/uploaders/tenant-profile-image-upload/tenant-profile-image-upload.module';
import { LandingPageImageUploadModule } from '../../../_shared/uploaders/landing-page-image-upload/landing-page-image-upload.module';


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
    TenantProfileImageUploadModule,
    LandingPageImageUploadModule,
  ],

  providers: [
  ],
})
export class TenantFormContentModule { }
