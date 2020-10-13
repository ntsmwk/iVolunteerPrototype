import { NgModule } from '@angular/core';
import { PropertyCreationDialogComponent } from './property-creation-dialog.component';
import {
  MatCommonModule, MatDialogModule, MatIconModule, MatButtonModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { BuilderContainerModule } from '../../../property-configurator/builder/builder-container/builder-container.module';


@NgModule({
  imports: [
    FuseSharedModule,
    BuilderContainerModule,
    MatCommonModule,
    MatDialogModule,
    MatIconModule,
    MatButtonModule,

  ],
  declarations: [PropertyCreationDialogComponent],
  exports: [PropertyCreationDialogComponent]


}) export class PropertyCreationDialogModule { }
