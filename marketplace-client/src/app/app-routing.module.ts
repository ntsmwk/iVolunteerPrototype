import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskListComponent} from './task/list/task-list.component';
import {TaskAvailableComponent} from './task/available/task-available.component';
import {TaskCreateComponent} from './task/create/task-create.component';
import {TaskTemplateListComponent} from './task-template/list/task-template-list.component';
import {TaskTemplateCreateComponent} from './task-template/create/task-template-create.component';
import {TaskDetailComponent} from './task/detail/task-detail.component';
import {LoginComponent} from './login/login.component';
import {TaskAssignComponent} from './task/assign/task-assign.component';
import {TokenGuard} from './_guard/token.guard';
import {EmployeeGuard} from './_guard/employee.guard';
import {VolunteerGuard} from './_guard/volunteer.guard';
import {VolunteerProfileComponent} from './volunteer/profile/volunteer-profile.component';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'task', component: TaskCreateComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'task/:id', component: TaskCreateComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'task/:id/detail', component: TaskDetailComponent, canActivate: [TokenGuard]},
  {path: 'task/assign/:id', component: TaskAssignComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'tasks', component: TaskListComponent, canActivate: [TokenGuard]},
  {path: 'available', component: TaskAvailableComponent, canActivate: [TokenGuard, VolunteerGuard]},
  {path: 'taskTemplate', component: TaskTemplateCreateComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'taskTemplate/:id', component: TaskTemplateCreateComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'taskTemplates', component: TaskTemplateListComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'profile', component: VolunteerProfileComponent, canActivate: [TokenGuard, VolunteerGuard]},
  {path: '', redirectTo: '/tasks', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
