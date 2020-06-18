import { NgModule } from '@angular/core';
import { RouterModule, Route } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatListModule } from '@angular/material/list'

import { FuseSharedModule } from '@fuse/shared.module';


import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatChipsModule } from '@angular/material/chips';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';

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
