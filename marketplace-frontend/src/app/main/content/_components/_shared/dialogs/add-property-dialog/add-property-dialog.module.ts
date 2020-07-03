import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPropertyDialogComponent } from './add-property-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule, MatInputModule, MatFormFieldModule, MatProgressSpinnerModule, MatTabsModule } from '@angular/material';
import { PropertyOrEnumCreationDialogModule } from '../../../help-seeker/configuration/class-configurator/_dialogs/property-enum-creation-dialog/property-enum-creation-dialog.module';
import { PropertyOrEnumCreationDialogComponent } from '../../../help-seeker/configuration/class-configurator/_dialogs/property-enum-creation-dialog/property-enum-creation-dialog.component';
import { FuseSharedModule } from '@fuse/shared.module';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatTableModule,
    MatDialogModule,
    MatButtonModule,
    MatCheckboxModule,
    MatIconModule,
    MatTabsModule,

    MatFormFieldModule,
    MatInputModule,

    MatProgressSpinnerModule,

    PropertyOrEnumCreationDialogModule,

    FuseSharedModule,

  ],
  declarations: [AddPropertyDialogComponent],
  entryComponents: [PropertyOrEnumCreationDialogComponent],
  exports: [AddPropertyDialogComponent]
})
export class AddPropertyDialogModule { }
