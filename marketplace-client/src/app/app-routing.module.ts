import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskListComponent} from './task/list/task-list.component';
import {TaskAvailableComponent} from './task/available/task-available.component';
import {TaskCreateComponent} from './task/create/task-create.component';
import {TaskTypeListComponent} from './task-type/list/task-type-list.component';
import {TaskTypeCreateComponent} from './task-type/create/task-type-create.component';
import {TaskDetailsComponent} from './task/details/task-details.component';
import {LoginComponent} from './login/login.component';
import {TokenGuard} from './login/token.guard';
import {EmployeeGuard} from './participant/employee.guard';
import {VolunteerGuard} from './participant/volunteer.guard';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'task', component: TaskCreateComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'task/:id/details', component: TaskDetailsComponent, canActivate: [TokenGuard]},
  {path: 'tasks', component: TaskListComponent, canActivate: [TokenGuard]},
  {path: 'available', component: TaskAvailableComponent, canActivate: [TokenGuard, VolunteerGuard]},
  {path: 'taskType', component: TaskTypeCreateComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: 'taskTypes', component: TaskTypeListComponent, canActivate: [TokenGuard, EmployeeGuard]},
  {path: '', redirectTo: '/tasks', pathMatch: 'full'},
  {path: '**', redirectTo: '/tasks'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
