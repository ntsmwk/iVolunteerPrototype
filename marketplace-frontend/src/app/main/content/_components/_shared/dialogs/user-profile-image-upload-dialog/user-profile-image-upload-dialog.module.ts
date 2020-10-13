import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserProfileImageUploadDialogComponent } from './user-profile-image-upload-dialog.component';
import { FuseSharedModule } from '@fuse/shared.module';
import { UserProfileImageUploadModule } from '../../uploaders/user-profile-image-upload/user-profile-image-upload.module';
import { MatIconModule, MatDialogModule, MatCommonModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    FuseSharedModule,
    MatCommonModule,
    MatIconModule,
    MatDialogModule,

    UserProfileImageUploadModule,

  ],
  declarations: [UserProfileImageUploadDialogComponent],
  exports: [UserProfileImageUploadDialogComponent]
})
export class UserProfileImageUploadDialogModule { }
