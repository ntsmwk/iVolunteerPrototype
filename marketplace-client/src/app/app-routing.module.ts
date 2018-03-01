import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ReserveTaskComponent} from './reserve-task/reserve-task.component';
import {TaskListComponent} from './task/list/task-list.component';
import {TaskCreateComponent} from './task/create/task-create.component';
import {TaskTypeListComponent} from './task-type/list/task-type-list.component';
import {TaskTypeCreateComponent} from './task-type/create/task-type-create.component';

const routes: Routes = [
  {path: 'reserve', component: ReserveTaskComponent},
  {path: 'task', component: TaskCreateComponent},
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
