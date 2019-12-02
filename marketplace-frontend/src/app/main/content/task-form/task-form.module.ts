import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { DateAdapter, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
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
