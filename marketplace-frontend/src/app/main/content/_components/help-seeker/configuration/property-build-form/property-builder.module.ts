import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyBuildFormComponent } from './property-builder.component';
import { RouterModule } from '@angular/router';
import { SinglePropertyModule } from './single-property/single-property.module';
import { MatProgressSpinnerModule, MatCommonModule, MatIconModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';

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

  ],
  declarations: [PropertyBuildFormComponent],
  providers: []
})



export class PropertyBuildFormModule { }
