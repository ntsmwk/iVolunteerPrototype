import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {TaskAvailableComponent} from './task/available/available.component';
import {TaskOverviewComponent} from './task/overview/overview.component';
import {TaskDetailsComponent} from './task/details/task-details.component';


const routes: Routes = [
  {path: 'available', component: TaskAvailableComponent},
  {path: 'overview', component: TaskOverviewComponent},
  {path: 'task/:id/details', component: TaskDetailsComponent},

  {path: '', redirectTo: '/available', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
