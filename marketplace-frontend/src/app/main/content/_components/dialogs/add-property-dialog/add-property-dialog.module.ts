import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPropertyDialogComponent } from './add-property-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule, MatInputModule, MatFormFieldModule, MatProgressSpinnerModule } from '@angular/material';
import { PropertyCreationDialogModule } from 'app/main/content/_components/help-seeker/configuration/class-configurator/property-creation-dialog/property-creation-dialog.module';
import {
  PropertyCreationDialogComponent
} from 'app/main/content/_components/help-seeker/configuration/class-configurator/property-creation-dialog/property-creation-dialog.component';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatTableModule,
    MatDialogModule,
    MatButtonModule,
    MatCheckboxModule,
    MatIconModule,

    MatFormFieldModule,
    MatInputModule,

    MatProgressSpinnerModule,

    PropertyCreationDialogModule,

  ],
  declarations: [AddPropertyDialogComponent],
  entryComponents: [PropertyCreationDialogComponent],
  exports: [AddPropertyDialogComponent]
})
export class AddPropertyDialogModule { }
