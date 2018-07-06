import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseContentComponent} from 'app/main/content/content.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {TokenInterceptor} from './_interceptor/token.interceptor';
import {Http401Interceptor} from './_interceptor/http-401.interceptor';
import {TokenGuard} from './_guard/token.guard';
import {VolunteerGuard} from './_guard/volunteer.guard';

const routes: Route[] = [
  {
    path: 'login',
    loadChildren: './login/login.module#FuseLoginModule'
  },
  {
    path: 'main/dashboard',
    loadChildren: './dashboard/dashboard.module#FuseDashboardModule',
    canActivate: [TokenGuard]
  },
  {
    path: 'main/marketplaces',
    loadChildren: './marketplaces/marketplaces.module#FuseMarketplacesModule',
    canActivate: [TokenGuard]
  },
  {
    path: 'main/tasktemplate/create',
    loadChildren: './task-template/task-template.module#FuseTaskTemplateModule',
    canActivate: [TokenGuard]
  },
  {
    path: 'main/task/:taskId',
    loadChildren: './task-detail/task-detail.module#FuseTaskDetailModule',
    canActivate: [TokenGuard]
  },
  {
    path: 'main/tasks/:pageType',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard],
    runGuardsAndResolvers: 'always'
  },
  {
    path: 'main/competencies/:pageType',
    loadChildren: './competencies/competencies.module#FuseCompetenceListModule',
    canActivate: [TokenGuard, VolunteerGuard],
    runGuardsAndResolvers: 'always'
  }
];

@NgModule({
  declarations: [
    FuseContentComponent
  ],
  imports: [
    HttpClientModule,
    RouterModule.forChild(routes),
    FuseSharedModule,
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: Http401Interceptor, multi: true}
  ],
  exports: [
    FuseContentComponent
  ]
})
export class FuseContentModule {
}
