import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseContentComponent} from 'app/main/content/content.component';
import {TokenGuard} from './_guard/token.guard';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {TokenInterceptor} from './_interceptor/token.interceptor';
import {Http401Interceptor} from './_interceptor/http-401.interceptor';

const routes = [
  {
    path: 'login',
    loadChildren: './login/login.module#FuseLoginModule'
  },
  {
    path: 'main',
    loadChildren: './sample/sample.module#FuseSampleModule',
    canActivate: [TokenGuard]
  },
  {
    path: 'main/tasks/available',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard]
  },
  {
    path: 'main/tasks/upcomming',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard]
  }, {
    path: 'main/tasks/running',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard]
  },
  {
    path: 'main/tasks/finished',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard]
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
    TokenGuard,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: Http401Interceptor, multi: true}
  ],
  exports: [
    FuseContentComponent
  ]
})
export class FuseContentModule {
}
