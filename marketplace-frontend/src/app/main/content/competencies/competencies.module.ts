import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {MatIconModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseCompetenceListComponent} from './competencies.component';

const routes = [
  {path: '', component: FuseCompetenceListComponent}
];

@NgModule({
  declarations: [
    FuseCompetenceListComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,

    FuseSharedModule
  ]
})

export class FuseCompetenceListModule {
}
