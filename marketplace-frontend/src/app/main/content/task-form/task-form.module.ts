import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {
  DateAdapter, MatButtonModule, MatCheckboxModule, MatDatepickerModule, MatDividerModule, MatFormFieldModule, MatInputModule, MatNativeDateModule,
  MatSelectModule
} from '@angular/material';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTaskFormComponent} from './task-form.component';
import {GermanDateAdapter} from '../_adapter/german-date-adapter';

const routes: Route[] = [
  {path: '', component: FuseTaskFormComponent},
  {path: ':taskId', component: FuseTaskFormComponent}
];

@NgModule({
  declarations: [
    FuseTaskFormComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    MatButtonModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDividerModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,

    FuseSharedModule
  ],
  providers: [
    {provide: DateAdapter, useClass: GermanDateAdapter},
  ]
})
export class FuseTaskFormModule {
}
