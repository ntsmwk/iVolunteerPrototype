import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserProfileImageUploadDialogComponent } from './user-profile-image-upload-dialog.component';
import { FuseSharedModule } from '@fuse/shared.module';
import { UserProfileImageUploadComponent } from '../../uploaders/user-profile-image-upload/user-profile-image-upload.component';

@NgModule({
  imports: [
    CommonModule,
    FuseSharedModule,
  ],
  declarations: [UserProfileImageUploadDialogComponent],
  entryComponents: [UserProfileImageUploadComponent],
  exports: [UserProfileImageUploadDialogComponent]
})
export class UserProfileImageUploadDialogModule { }
