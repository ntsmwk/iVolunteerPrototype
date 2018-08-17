import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {MatButtonModule, MatDividerModule, MatIconModule, MatTabsModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseProfileComponent} from './profile.component';
import {FuseProfileTimelineComponent} from './tabs/timeline/timeline.component';
import {FuseProfileAboutComponent} from './tabs/about/about.component';

const routes = [
  {path: '**', component: FuseProfileComponent}
];

@NgModule({
  declarations: [
    FuseProfileComponent,
    FuseProfileTimelineComponent,
    FuseProfileAboutComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    MatTabsModule,

    FuseSharedModule
  ]
})
export class FuseProfileModule {
}
