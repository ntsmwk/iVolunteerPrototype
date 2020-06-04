import { NgModule } from '@angular/core';
import { PropertyOrEnumCreationDialogComponent } from './property-enum-creation-dialog.component';
import {
  MatCommonModule, MatDialogModule, MatIconModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { BuilderContainerModule } from '../../property-build-form/builder-container/builder-container.module';


@NgModule({
  imports: [
    FuseSharedModule,
    BuilderContainerModule,
    MatCommonModule,
    MatDialogModule,
    MatIconModule,

  ],
  declarations: [PropertyOrEnumCreationDialogComponent],
  exports: [PropertyOrEnumCreationDialogComponent]


}) export class PropertyOrEnumCreationDialogModule { }
