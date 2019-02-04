import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseContentComponent} from 'app/main/content/content.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {TokenInterceptor} from './_interceptor/token.interceptor';
import {Http401Interceptor} from './_interceptor/http-401.interceptor';

import {TokenGuard} from './_guard/token.guard';
import {HelpSeekerGuard} from './_guard/help-seeker.guard';
import {VolunteerGuard} from './_guard/volunteer.guard';
import { LoginGuard } from './_guard/login.guard';



const routes: Route[] = [
  {
    path: 'login',
    loadChildren: './login/login.module#FuseLoginModule'
  },
  {
    path: 'main/dashboard',
    loadChildren: './dashboard/dashboard.module#FuseDashboardModule',
    canActivate: [TokenGuard, LoginGuard]
  },
  {
    path: 'main/profile',
    loadChildren: './profile/profile.module#FuseProfileModule',
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: 'main/engagements',
    loadChildren: './engagements/engagements.module#FuseEngagementsModule',
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: 'main/achievements',
    loadChildren: './achievements/achievements.module#FuseAchievementsModule',
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: 'main/get-connected',
    loadChildren: './get-connected/get-connected.module#FuseGetConnectedModule',
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: 'main/get-engaged',
    loadChildren: './get-engaged/get-engaged.module#FuseGetEngagedModule',
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: 'main/task',
    loadChildren: './task-detail/task-detail.module#FuseTaskDetailModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: 'main/task-form',
    loadChildren: './task-form/task-form.module#FuseTaskFormModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: 'main/tasks/all',
    loadChildren: './task-list/task-list.module#FuseTaskListModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: 'main/project-form',
    loadChildren: './project-form/project-form.module#FuseProjectFormModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: 'main/projects/all',
    loadChildren: './project-list/project-list.module#FuseProjectListModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  //AK
  { path: 'main/properties/all',
    loadChildren: './property-list/property-list.module#FusePropertyListModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  { path: 'main/property',
    loadChildren: './property-detail/property-detail.module#FusePropertyDetailModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  { path: 'main/task-templates/user/all',
    loadChildren: './user-defined-task-template-list/user-defined-task-template-list.module#FuseUserDefinedTaskTemplateListModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  { path: 'main/task-templates/user/detail',
    loadChildren: './user-defined-task-template-detail/user-defined-task-template-detail.module#FuseUserDefinedTaskTemplateDetailModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  { path: 'main/task-templates/user/detail/edit',
    loadChildren: './user-defined-task-template-detail-form/user-defined-task-template-detail-form.module#FuseUserDefinedTaskTemplateDetailFormModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  //AK
  {
    path: 'main/task-template-form',
    loadChildren: './task-template-form/task-template-form.module#FuseTaskTemplateFormModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: 'main/task-templates/all',
    loadChildren: './task-template-list/task-template-list.module#FuseTaskTemplateListModule',
    canActivate: [TokenGuard, HelpSeekerGuard]
  }
];

@NgModule({
  declarations: [
    FuseContentComponent,
    
    
    
    
    

  ],
  imports: [
    HttpClientModule,

    RouterModule.forChild(routes),

    FuseSharedModule
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
