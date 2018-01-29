import {OrganisationComponent} from './components/organisation/organisation.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {VolunteerComponent} from './components/volunteer/volunteer.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'organisation/tasks', component: OrganisationComponent},
  {path: 'volunteer/tasks', component: VolunteerComponent},
  {path: '', redirectTo: '/login', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
