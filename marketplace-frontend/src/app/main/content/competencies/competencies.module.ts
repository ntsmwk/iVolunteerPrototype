import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseCompetenceListComponent} from './competencies.component';
import {MatIconModule} from '@angular/material';

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
