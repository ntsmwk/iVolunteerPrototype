import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {MatButtonModule, MatCheckboxModule, MatFormFieldModule, MatInputModule, MatSelectModule, MatTableModule} from '@angular/material';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseTaskListComponent} from './task-list.component';
import {LoginService} from '../_service/login.service';
import {TaskService} from '../_service/task.service';

const routes: Route[] = [
  {path: '', component: FuseTaskListComponent, runGuardsAndResolvers: 'always'}
];

@NgModule({
  providers: [
    LoginService,
    TaskService
  ],

  declarations: [
    FuseTaskListComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,



    FuseSharedModule
  ],
  exports: [
    FuseTaskListComponent
  ]
})
export class FuseTaskListModule {
}
