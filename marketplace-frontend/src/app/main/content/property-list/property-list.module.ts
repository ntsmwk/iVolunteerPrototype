import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatTableModule, MatIconModule, MatButtonModule, MatTooltipModule, MatSlideToggleModule, MatCommonModule, MatCardModule, MatProgressSpinnerModule } from '@angular/material';

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
  
    MatCommonModule,
    MatCardModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatSlideToggleModule,

    MatProgressSpinnerModule,

    FuseSharedModule,
    FuseTruncatePipeModule
  ]
  
})

export class PropertyListModule { }
