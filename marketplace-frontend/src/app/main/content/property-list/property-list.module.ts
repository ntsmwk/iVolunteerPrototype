import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatTableModule, MatIconModule, MatButtonModule } from '@angular/material';

import { FuseSharedModule } from '@fuse/shared.module';

import { FusePropertyListComponent } from './property-list.component';



import { FuseTruncatePipeModule } from "../_pipe/truncate-pipe.module";


const routes: Route[] = [
  {path: '', component: FusePropertyListComponent}
];

@NgModule({
  declarations: [
    FusePropertyListComponent   
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

export class FusePropertyListModule { }
