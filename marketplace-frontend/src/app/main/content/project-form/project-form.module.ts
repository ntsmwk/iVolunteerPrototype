import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {DateAdapter, MatButtonModule, MatDatepickerModule, MatFormFieldModule, MatInputModule, MatNativeDateModule} from '@angular/material';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseProjectFormComponent} from './project-form.component';
import {GermanDateAdapter} from '../_adapter/german-date-adapter';

const routes: Route[] = [
  {path: '', component: FuseProjectFormComponent},
  {path: ':projectId', component: FuseProjectFormComponent}
];

@NgModule({
  declarations: [
    FuseProjectFormComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,

    FuseSharedModule
  ],
  providers: [
    {provide: DateAdapter, useClass: GermanDateAdapter},
  ]
})
export class FuseProjectFormModule {
}
