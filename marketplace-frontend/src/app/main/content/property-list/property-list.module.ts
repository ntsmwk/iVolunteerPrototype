import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatTableModule, MatIconModule, MatButtonModule } from '@angular/material';

import { FuseSharedModule } from '@fuse/shared.module';

import { PropertyListComponent } from './property-list.component';



import { FuseTruncatePipeModule } from "../_pipe/truncate-pipe.module";


const routes: Route[] = [
  {path: '', component: PropertyListComponent}
];

@NgModule({
  declarations: [
    PropertyListComponent   
  ],

  imports: [
    RouterModule.forChild(routes),
  
    MatTableModule,
    MatIconModule,
    MatButtonModule,

    FuseSharedModule,
    FuseTruncatePipeModule

  ]
  
})

export class PropertyListModule { }
