import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { DateAdapter, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseProjectFormComponent} from './project-form.component';
import {GermanDateAdapter} from '../../../_adapter/german-date-adapter';

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
