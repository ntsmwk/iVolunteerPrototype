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

@NgModule({
  declarations: [
    AppComponent,
    TaskComponent,
    CreateTaskTypeComponent
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
    TaskTypeService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
