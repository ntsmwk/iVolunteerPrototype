import { NgModule } from '@angular/core';
import { TenantTagFormComponent } from './tag-form.component';
import { FuseSharedModule } from '@fuse/shared.module';
import {
  MatButtonModule, MatIconModule, MatInputModule, MatFormFieldModule, MatCommonModule, MatOptionModule,
  MatAutocompleteModule,
  MatChipsModule
} from '@angular/material';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  exports: [TenantTagFormComponent],
  declarations: [TenantTagFormComponent],
  imports: [
    FuseSharedModule,

    ReactiveFormsModule,
    MatCommonModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    MatOptionModule,
    MatChipsModule,
  ],
  providers: [TenantTagFormComponent],
})
export class TenantTagFormModule { }
