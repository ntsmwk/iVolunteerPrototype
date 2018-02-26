import {NgModule} from '@angular/core';
import {AppMaterialModule} from './app-material.module';
import {AppRoutingModule} from './app-routing.module';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClientModule} from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {TaskComponent} from './task/task.component';
import {CreateTaskTypeComponent} from './create-task-type/create-task-type.component';

import {TaskService} from './task/task.service';
import {TaskTypeService} from './task-type/task-type.service';
import {CreateTaskComponent} from './create-task/create-task.component';
import { ReserveTaskComponent } from './reserve-task/reserve-task.component';
import {TaskTransactionService} from './task-transaction/task-transaction.service';
import {VolunteerService} from './participant/volunteer.service';

@NgModule({
  declarations: [
    AppComponent,
    TaskComponent,
    CreateTaskTypeComponent,
    CreateTaskComponent,
    ReserveTaskComponent
  ],
  imports: [
    AppMaterialModule,
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [
    TaskService,
    TaskTypeService,
    TaskTransactionService,
    VolunteerService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
