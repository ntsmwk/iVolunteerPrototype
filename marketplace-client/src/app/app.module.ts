import {NgModule} from '@angular/core';
import {AppMaterialModule} from './app-material.module';
import {AppRoutingModule} from './app-routing.module';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app/app.component';

import {TaskService} from './task/task.service';
import {TaskTypeService} from './task-type/task-type.service';
import {TaskCreateComponent} from './task/create/task-create.component';
import {TaskListComponent} from './task/list/task-list.component';
import {TaskTypeListComponent} from './task-type/list/task-type-list.component';
import {TaskTypeCreateComponent} from './task-type/create/task-type-create.component';

import {NavbarComponent} from './navbar/navbar.component';
import {TaskDetailComponent} from './task/detail/task-detail.component';
import {TaskInteractionService} from './task-interaction/task-interaction.service';
import {TokenInterceptor} from './_interceptor/token.interceptor';
import {Http401Interceptor} from './_interceptor/http-401.interceptor';
import {LoginService} from './login/login.service';
import {LoginComponent} from './login/login.component';
import {TokenGuard} from './login/token.guard';
import {MessageService} from './_service/message.service';
import {TaskAvailableComponent} from './task/available/task-available.component';
import {VolunteerRepositoryService} from './volunteer/volunteer-repository.service';
import {TaskAssignComponent} from './task/assign/task-assign.component';
import {EmployeeGuard} from './employee/employee.guard';
import {VolunteerGuard} from './volunteer/volunteer.guard';
import {TaskInteractionHistoryComponent} from './task-interaction/history/task-interaction-history.component';
import {GermanDateAdapter} from './_adapter/german-date-adapter';
import {DateAdapter} from '@angular/material';
import {CompetenceService} from './competence/competence.service';
import {VolunteerService} from './volunteer/volunteer.service';
import {VolunteerProfileComponent} from './volunteer/profile/volunteer-profile.component';
import {EmployeeService} from './employee/employee.service';
import {VolunteerProfileService} from './volunteer/volunteer-profile.service';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LoginComponent,
    TaskListComponent,
    TaskAvailableComponent,
    TaskCreateComponent,
    TaskDetailComponent,
    TaskTypeListComponent,
    TaskTypeCreateComponent,
    TaskAssignComponent,
    TaskInteractionHistoryComponent,
    VolunteerProfileComponent
  ],
  imports: [
    AppMaterialModule,
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    TokenGuard,
    LoginService,
    MessageService,
    CompetenceService,
    TaskService,
    TaskTypeService,
    TaskInteractionService,
    EmployeeGuard,
    EmployeeService,
    VolunteerGuard,
    VolunteerService,
    VolunteerProfileService,
    VolunteerRepositoryService,
    {provide: DateAdapter, useClass: GermanDateAdapter},
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: Http401Interceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}

