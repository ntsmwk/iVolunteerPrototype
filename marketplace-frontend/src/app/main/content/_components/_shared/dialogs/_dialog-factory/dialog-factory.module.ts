import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ConfirmDialogModule } from "../confirm-dialog/confirm-dialog.module";
import { ClassInstanceFormPreviewDialogModule } from '../../../help-seeker/configuration/class-instance-configurator/form-preview-dialog/form-preview-dialog.module';
import { ClassInstanceFormPreviewExportDialogModule } from '../../../help-seeker/configuration/class-instance-configurator/form-preview-export-dialog/form-preview-export-dialog.module';
import { AddHelpseekerDialogModule } from '../../../admin/tenant-form/tenant-form-content/helpseekers-form/add-helpseeker-dialog/add-helpseeker-dialog.module';
import { UserProfileImageUploadDialogModule } from '../user-profile-image-upload-dialog/user-profile-image-upload-dialog.module';
import { DialogFactoryDirective } from './dialog-factory.component';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { ClassInstanceFormPreviewDialogComponent } from '../../../help-seeker/configuration/class-instance-configurator/form-preview-dialog/form-preview-dialog.component';
import { ClassInstanceFormPreviewExportDialogComponent } from '../../../help-seeker/configuration/class-instance-configurator/form-preview-export-dialog/form-preview-export-dialog.component';
import { AddHelpseekerDialogComponent } from '../../../admin/tenant-form/tenant-form-content/helpseekers-form/add-helpseeker-dialog/add-helpseeker-dialog.component';
import { UserProfileImageUploadDialogComponent } from '../user-profile-image-upload-dialog/user-profile-image-upload-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    // AddOrRemoveDialogModule,
    ConfirmDialogModule,

    // Class Configurator
    ClassInstanceFormPreviewDialogModule,
    ClassInstanceFormPreviewExportDialogModule,

    // Matching Configurator

    // Tree Property Configurator

    // Tenant Form
    AddHelpseekerDialogModule,

    // User Profile Form
    UserProfileImageUploadDialogModule,
  ],
  declarations: [DialogFactoryDirective],
  entryComponents: [
    // AddOrRemoveDialogComponent,
    ConfirmDialogComponent,

    // Class Configurator

    ClassInstanceFormPreviewDialogComponent,
    ClassInstanceFormPreviewExportDialogComponent,

    // Matching Configurator

    // Tree Property Configurator

    // Tenant Form
    AddHelpseekerDialogComponent,

    // User Profile Form
    UserProfileImageUploadDialogComponent,
  ]
})
export class DialogFactoryModule { }
