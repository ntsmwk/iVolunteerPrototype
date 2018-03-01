import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskListComponent} from './task/list/task-list.component';
import {TaskCreateComponent} from './task/create/task-create.component';
import {TaskTypeListComponent} from './task-type/list/task-type-list.component';
import {TaskTypeCreateComponent} from './task-type/create/task-type-create.component';
import {TaskDetailsComponent} from './task/details/task-details.component';

const routes: Routes = [
  {path: 'task', component: TaskCreateComponent},
  {path: 'task/:id/details', component: TaskDetailsComponent},
  {path: 'tasks', component: TaskListComponent},
  {path: 'taskType', component: TaskTypeCreateComponent},
  {path: 'taskTypes', component: TaskTypeListComponent},
  {path: '', redirectTo: '/task', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
