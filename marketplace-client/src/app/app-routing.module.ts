import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskComponent} from './task/task.component';
import {ReserveTaskComponent} from './reserve-task/reserve-task.component';
import {TaskTypeListComponent} from './task-type/list/task-type-list.component';
import {TaskTypeCreateComponent} from './task-type/create/task-type-create.component';

const routes: Routes = [
  {path: 'reserve', component: ReserveTaskComponent},
  {path: 'task', component: TaskComponent},
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
