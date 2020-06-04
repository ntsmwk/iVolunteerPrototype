import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyBuildFormComponent } from './property-build-form.component';
import { RouterModule } from '@angular/router';
import {
  MatProgressSpinnerModule,
  MatCommonModule,
  MatIconModule,
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { HeaderModule } from 'app/main/content/_shared_components/header/header.module';
import { SinglePropertyModule } from './single-property/single-property-builder.module';

const routes = [
  { path: ':propertyId', component: PropertyBuildFormComponent },
  { path: '', component: PropertyBuildFormComponent },
];

@NgModule({
  imports: [
    CommonModule,
    FuseSharedModule,

    RouterModule.forChild(routes),

    MatCommonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    SinglePropertyModule,
    HeaderModule,
  ],
  declarations: [PropertyBuildFormComponent],
  providers: [],
})
export class PropertyBuildFormModule { }
