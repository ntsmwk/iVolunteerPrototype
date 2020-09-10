import { NgModule } from "@angular/core";
import { TenantFormContentComponent } from "./tenant-form-content.component";
import {
  MatButtonModule, MatIconModule, MatDatepickerModule, MatNativeDateModule, MatFormFieldModule, MatInputModule, MatTableModule,
} from "@angular/material";
import { FuseSharedModule } from "@fuse/shared.module";

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
  ],

  providers: [],
})
export class TenantFormContentModule { }
