import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AppMaterialModule} from './app-material.module';
import {TaskService} from './task/task.service';
import {TaskInteractionService} from './task-interaction/task-interaction.service';
import {NavbarComponent} from './navbar/navbar.component';
import {TaskAvailableComponent} from './task/available/available.component';
import {TaskDetailsComponent} from './task/details/task-details.component';
import {TaskOverviewComponent} from './task/overview/overview.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {VolunteerService} from './participant/volunteer.service';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    TaskAvailableComponent,
    TaskDetailsComponent,
    TaskOverviewComponent
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
    TaskInteractionService,
    VolunteerService

  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
