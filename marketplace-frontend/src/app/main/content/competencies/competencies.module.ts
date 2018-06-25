import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import { FuseCompetenceListComponent } from './competencies.component';

const routes = [
  {path: '', component: FuseCompetenceListComponent}
];

@NgModule({
  declarations: [
    FuseCompetenceListComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    FuseSharedModule
  ],
  exports: [
    FuseCompetenceListComponent
  ]
})

export class FuseCompetenceListModule {
}
