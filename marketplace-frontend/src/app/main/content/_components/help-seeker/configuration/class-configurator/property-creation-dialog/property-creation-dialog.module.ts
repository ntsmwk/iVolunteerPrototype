import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyCreationDialogComponent } from './property-creation-dialog.component';
import {
  MatCommonModule, MatProgressSpinnerModule, MatButtonModule, MatDialogModule, MatIconModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { SinglePropertyModule } from '../../property-build-form/single-property/single-property.module';


@NgModule({
  imports: [
    FuseSharedModule,
    SinglePropertyModule,
    MatCommonModule,
    MatDialogModule,
    MatIconModule,

  ],
  declarations: [PropertyCreationDialogComponent],
  exports: [PropertyCreationDialogComponent]


}) export class PropertyCreationDialogModule { }
