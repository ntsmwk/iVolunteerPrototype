import { NgModule } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MatCommonModule } from "@angular/material/core";
import { MatDialogModule } from "@angular/material/dialog";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from "@angular/material/icon";
import { MatInputModule } from "@angular/material/input";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { EditMetaMatchingConfigurationDialogComponent } from './edit-meta-dialog.component';
import { FuseSharedModule } from '@fuse/shared.module';

@NgModule({
  imports: [
    FuseSharedModule,
    FormsModule,
    ReactiveFormsModule,
    MatCommonModule,
    MatDialogModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatIconModule,
  ],
  declarations: [EditMetaMatchingConfigurationDialogComponent],
  exports: [EditMetaMatchingConfigurationDialogComponent],
})
export class EditMetaMatchingConfigurationDialogModule { }
