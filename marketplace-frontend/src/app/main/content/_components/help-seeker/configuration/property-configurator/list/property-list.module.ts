import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatCommonModule } from '@angular/material/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FuseSharedModule } from '@fuse/shared.module';
import { PropertyListComponent } from './property-list.component';

const routes: Route[] = [
  { path: '', component: PropertyListComponent }
];

@NgModule({
  declarations: [
    PropertyListComponent
  ],

  imports: [
    RouterModule.forChild(routes),
    FuseSharedModule,
    MatCommonModule,
    MatProgressSpinnerModule,
  ]

})

export class PropertyListModule { }
