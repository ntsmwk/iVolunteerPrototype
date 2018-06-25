import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseContentComponent} from 'app/main/content/content.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {TokenInterceptor} from './_interceptor/token.interceptor';
import {Http401Interceptor} from './_interceptor/http-401.interceptor';

import {LoginService} from './_service/login.service';
import {TokenGuard} from './_guard/token.guard';
import {EmployeeGuard} from './_guard/employee.guard';
import {VolunteerGuard} from './_guard/volunteer.guard';

const routes = [
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
    path: 'main/tasks/available',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: 'main/tasks/upcomming',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard, VolunteerGuard]
  }, {
    path: 'main/tasks/running',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: 'main/tasks/finished',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard, VolunteerGuard]
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
