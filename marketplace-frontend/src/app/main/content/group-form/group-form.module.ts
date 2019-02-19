import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {
  DateAdapter, MatButtonModule, MatCheckboxModule, MatDatepickerModule, MatDividerModule, MatFormFieldModule, MatInputModule, MatNativeDateModule,
  MatSelectModule
} from '@angular/material';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseGroupFormComponent} from './group-form.component';

const routes: Route[] = [
  {path: '', component: FuseGroupFormComponent}
];

@NgModule({
  declarations: [
    FuseGroupFormComponent
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
  ]
})
export class FuseGroupFormModule {
}
