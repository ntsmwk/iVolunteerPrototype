import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {ProfileComponent} from './profile.component';
import { MatIconModule } from '@angular/material';
import { FuseWidgetModule } from '@fuse/components';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';



const routes = [
  {
      path     : 'dashboard',
      component: ProfileComponent
  }
];

@NgModule({
  declarations: [
    ProfileComponent,
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,

    BrowserAnimationsModule,

    FuseSharedModule,
    FuseWidgetModule
  ],
  exports: [
    ProfileComponent
  ]
})

export class FuseProfileModule {
}
