import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseWelcomeComponent} from './welcome.component';

const routes = [
  {path: '**', component: FuseWelcomeComponent}
];

@NgModule({
  declarations: [
    FuseWelcomeComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    FuseSharedModule
  ]
})

export class FuseWelcomeModule {
}
