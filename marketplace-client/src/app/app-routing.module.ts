import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskListComponent} from './task/list/task-list.component';
import {TaskAvailableComponent} from './task/available/task-available.component';
import {TaskCreateComponent} from './task/create/task-create.component';
import {TaskTypeListComponent} from './task-type/list/task-type-list.component';
import {TaskTypeCreateComponent} from './task-type/create/task-type-create.component';
import {TaskDetailsComponent} from './task/details/task-details.component';
import {LoginComponent} from './login/login.component';
import {LoginGuard} from './login/login.guard';
import {TaskAssignComponent} from './task/assign/task-assign.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'task', component: TaskCreateComponent, canActivate: [LoginGuard]},
  {path: 'task/:id/details', component: TaskDetailsComponent, canActivate: [LoginGuard]},
  {path: 'tasks', component: TaskListComponent, canActivate: [LoginGuard]},
  {path: 'task/assign/:id', component: TaskAssignComponent, canActivate: [LoginGuard]},
  {path: 'available', component: TaskAvailableComponent, canActivate: [LoginGuard]},
  {path: 'taskType', component: TaskTypeCreateComponent, canActivate: [LoginGuard]},
  {path: 'taskTypes', component: TaskTypeListComponent, canActivate: [LoginGuard]},
  {path: '', redirectTo: '/tasks', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
