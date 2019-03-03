import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {MatButtonModule, MatDatepickerModule, MatFormFieldModule, MatInputModule, MatNativeDateModule} from '@angular/material';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseBCEntryFormComponent} from './bcEntry-form.component';

const routes: Route[] = [
  {path: '', component: FuseBCEntryFormComponent},
  {path: ':name', component: FuseBCEntryFormComponent}
];

@NgModule({
  declarations: [
    FuseBCEntryFormComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,

    FuseSharedModule
  ]
})
export class FuseBCEntryFormModule {
}
