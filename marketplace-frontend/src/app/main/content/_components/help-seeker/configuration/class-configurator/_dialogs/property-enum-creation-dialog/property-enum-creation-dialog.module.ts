import { NgModule } from '@angular/core';
import { PropertyOrEnumCreationDialogComponent } from './property-enum-creation-dialog.component';
import {
  MatCommonModule, MatDialogModule, MatIconModule, MatButtonModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { BuilderContainerModule } from '../../../property-enum-configurator/builder/builder-container/builder-container.module';


@NgModule({
  imports: [
    FuseSharedModule,
    BuilderContainerModule,
    MatCommonModule,
    MatDialogModule,
    MatIconModule,
    MatButtonModule,

  ],
  declarations: [PropertyOrEnumCreationDialogComponent],
  exports: [PropertyOrEnumCreationDialogComponent]


}) export class PropertyOrEnumCreationDialogModule { }
