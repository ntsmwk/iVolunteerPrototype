import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MapPropertyTestComponent } from './map-property-test.component';
import { FuseSharedModule } from "@fuse/shared.module";
import { RouterModule, Route } from '@angular/router';
import { AgmCoreModule } from '@agm/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { DragulaModule } from 'ng2-dragula';


const routes: Route[] = [
  {path: '', component: MapPropertyTestComponent}
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    FuseSharedModule,
    
    MatCommonModule,
    MatInputModule,
    MatButtonModule,
    MatFormFieldModule,

    DragulaModule,
    
    AgmCoreModule.forRoot({
      // apiKey: 'AIzaSyDiwSZ8L-1r9TXW_q5UTK_--GE2MKHK-jE' //enter Google Maps API Key
      
    })
  ],
  declarations: [MapPropertyTestComponent]
})
export class MapPropertyTestModule { }
