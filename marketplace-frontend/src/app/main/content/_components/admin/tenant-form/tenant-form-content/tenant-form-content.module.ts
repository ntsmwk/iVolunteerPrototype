import { NgModule } from "@angular/core";
import { TenantFormContentComponent } from "./tenant-form-content.component";
import {
  MatButtonModule, MatIconModule, MatDatepickerModule, MatNativeDateModule, MatFormFieldModule, MatInputModule, MatTableModule, MatProgressSpinner, MatProgressSpinnerModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { TenantTagFormModule } from './tag-form/tag-form.module';


@NgModule({
  exports: [TenantFormContentComponent],
  declarations: [TenantFormContentComponent],
  imports: [
    MatButtonModule,
    MatIconModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    FuseSharedModule,
    MaterialFileInputModule,
    MatProgressSpinnerModule,
    TenantTagFormModule
  ],

  providers: [
  ],
})
export class TenantFormContentModule { }
