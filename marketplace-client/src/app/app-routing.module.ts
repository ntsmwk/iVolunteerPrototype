import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskComponent} from './task/task.component';
import {ReserveTaskComponent} from './reserve-task/reserve-task.component';
import {TaskTypeComponent} from './task-type/task-type.component';

const routes: Routes = [
  {path: 'reserve', component: ReserveTaskComponent},
  {path: 'task', component: TaskComponent},
  {path: 'taskType', component: TaskTypeComponent},
  {path: '', redirectTo: '/task', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
