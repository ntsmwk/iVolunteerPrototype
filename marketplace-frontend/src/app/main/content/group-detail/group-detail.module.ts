import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {
  MatButtonModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDividerModule,
  MatFormFieldModule,
  MatInputModule,
  MatNativeDateModule,
  MatSelectModule
} from '@angular/material';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseGroupDetailComponent} from './group-detail.component';

const routes: Route[] = [
  {path: ':groupId', component: FuseGroupDetailComponent}
];

@NgModule({
  declarations: [
    FuseGroupDetailComponent
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
export class FuseGroupDetailModule {
}
