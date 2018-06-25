import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseContentComponent} from 'app/main/content/content.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {TokenInterceptor} from './_interceptor/token.interceptor';
import {Http401Interceptor} from './_interceptor/http-401.interceptor';

import {LoginService} from './_service/login.service';
import {TokenGuard} from './_guard/token.guard';
import {EmployeeGuard} from './_guard/employee.guard';
import {VolunteerGuard} from './_guard/volunteer.guard';

const routes: Route[] = [
  {
    path: 'login',
    loadChildren: './login/login.module#FuseLoginModule'
  },
  {
    path: 'main/dashboard',
    loadChildren: './sample/sample.module#FuseSampleModule',
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
    LoginService,
    TokenGuard,
    EmployeeGuard,
    VolunteerGuard,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: Http401Interceptor, multi: true}
  ],
  exports: [
    FuseContentComponent
  ]
})
export class FuseContentModule {
}
