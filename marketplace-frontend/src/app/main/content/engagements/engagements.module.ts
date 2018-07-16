import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

const routes: Routes = [
  {
    path: '',
    children: [
      {path: 'projects', loadChildren: './projects/projects.module#FuseProjectsModule'},
      {path: 'project/:marketplaceId/:projectId', loadChildren: './project-detail/project-detail.module#FuseProjectDetailModule'},
      {path: '', redirectTo: 'projects', pathMatch: 'full'}
    ]
  }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forChild(routes),

    FuseSharedModule
  ]
})

export class FuseEngagementsModule {
}
