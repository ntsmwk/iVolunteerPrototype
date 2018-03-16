import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskListComponent} from './task/list/task-list.component';
import {TaskAvailableComponent} from './task/available/task-available.component';
import {TaskCreateComponent} from './task/create/task-create.component';
import {TaskTypeListComponent} from './task-type/list/task-type-list.component';
import {TaskTypeCreateComponent} from './task-type/create/task-type-create.component';
import {TaskDetailComponent} from './task/detail/task-detail.component';
import {LoginComponent} from './login/login.component';
import {TaskAssignComponent} from './task/assign/task-assign.component';
import {TokenGuard} from './login/token.guard';
import {EmployeeGuard} from './employee/employee.guard';
import {VolunteerGuard} from './volunteer/volunteer.guard';
import {VolunteerRepositoryComponent} from './volunteer/repository/volunteer-repository.component';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'task', component: TaskCreateComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'task/:id', component: TaskCreateComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'task/:id/detail', component: TaskDetailComponent, canActivate: [TokenGuard]},
  {path: 'task/assign/:id', component: TaskAssignComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'tasks', component: TaskListComponent, canActivate: [TokenGuard]},
  {path: 'available', component: TaskAvailableComponent, canActivate: [TokenGuard, VolunteerGuard]},
  {path: 'taskType', component: TaskTypeCreateComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'taskTypes', component: TaskTypeListComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'repository', component: VolunteerRepositoryComponent, canActivate: [TokenGuard, VolunteerGuard]},
  {path: '', redirectTo: '/tasks', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
