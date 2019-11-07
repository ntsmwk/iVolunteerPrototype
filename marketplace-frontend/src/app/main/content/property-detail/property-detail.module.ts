import { NgModule } from '@angular/core';
import { RouterModule, Route } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatListModule } from '@angular/material/list'

import { FuseSharedModule } from '@fuse/shared.module';


import {
  MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule,
  MatInputModule, MatSidenavModule, MatDividerModule, MatTableModule, MatCheckboxModule
}
  from '@angular/material';

import { PropertyDetailComponent } from './property-detail.component';
// import { MultiPropertyDetailComponent } from './multi-property-detail/multi-property-detail.component';

const routes: Route[] = [
  { path: ':marketplaceId/:propertyId', component: PropertyDetailComponent },
  { path: ':marketplaceId/:templateId/:propertyId', component: PropertyDetailComponent },
  { path: ':marketplaceId/:templateId/:subtemplateId/:propertyId', component: PropertyDetailComponent },
];

@NgModule({
  declarations: [PropertyDetailComponent, /*MultiPropertyDetailComponent*/],

  imports: [
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatDividerModule,
    MatTableModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatListModule,

    FuseSharedModule

  ]

})
export class PropertyDetailModule { }
