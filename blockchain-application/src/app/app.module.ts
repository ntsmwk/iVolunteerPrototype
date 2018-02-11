import 'zone.js/dist/zone-mix';
import 'reflect-metadata';
import 'polyfills';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';

import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
// NG Translate
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

import {AppComponent} from './components/app/app.component';
import {OrganisationComponent} from './components/organisation/organisation.component';
import {DataService} from './providers/data.service';
import {TaskService} from './providers/task.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppMaterialModule} from './app-material.module';
import {Configuration} from './providers/configuration';
import {CreateTaskService} from './providers/create-task.service';
import {AssignTaskService} from './providers/assign-task.service';
import {FinishTaskService} from './providers/finish-task.service';
import {LoginComponent} from './components/login/login.component';
import {VolunteerService} from './providers/volunteer.service';
import {OrganisationService} from './providers/organisation.service';
import {VolunteerComponent} from './components/volunteer/volunteer.component';
import {TaskListComponent} from './components/task-list/task-list.component';
import {TaskCreateComponent} from './components/task-create/task-create.component';
import {ReserveTaskService} from './providers/reserve-task.service';
import {CookieService} from 'ngx-cookie-service';
import {HomeComponent} from './components/home/home.component';
import {AuthenticationGuard} from './guard/authenticationGuard';
import {SystemPingService} from './providers/system-ping.service';
import {TokenInterceptor} from './interceptor/token.interceptor';
import {Http401Interceptor} from './interceptor/http-401.interceptor';

// AoT requires an exported function for factories
export function HttpLoaderFactory(httpClient: HttpClient) {
  return new TranslateHttpLoader(httpClient, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    TaskListComponent,
    TaskCreateComponent,
    OrganisationComponent,
    VolunteerComponent,
    HomeComponent
  ],
  imports: [
    AppRoutingModule,
    AppMaterialModule,
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (HttpLoaderFactory),
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    Configuration,
    DataService,
    TaskService,
    CreateTaskService,
    ReserveTaskService,
    AssignTaskService,
    FinishTaskService,
    VolunteerService,
    OrganisationService,

    CookieService,
    SystemPingService,
    AuthenticationGuard,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: Http401Interceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
