import { NgModule } from "@angular/core";
import {
  MatButtonModule, MatIconModule, MatFormFieldModule, MatInputModule, MatProgressSpinnerModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { LandingPageImageUploadComponent } from './landing-page-image-upload.component';


@NgModule({
  exports: [LandingPageImageUploadComponent],
  declarations: [LandingPageImageUploadComponent],
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
export class LandingPageImageUploadModule { }
