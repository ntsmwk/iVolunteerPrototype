import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskListComponent} from './task/list/task-list.component';
import {TaskCreateComponent} from './task/create/task-create.component';
import {TaskTypeListComponent} from './task-type/list/task-type-list.component';
import {TaskTypeCreateComponent} from './task-type/create/task-type-create.component';
import {TaskDetailsComponent} from './task/details/task-details.component';
import {LoginComponent} from './login/login.component';
import {LoginGuard} from './login/login.guard';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'task', component: TaskCreateComponent, canActivate: [LoginGuard]},
  {path: 'task/:id/details', component: TaskDetailsComponent, canActivate: [LoginGuard]},
  {path: 'tasks', component: TaskListComponent, canActivate: [LoginGuard]},
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
