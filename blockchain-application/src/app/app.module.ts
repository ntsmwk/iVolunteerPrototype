import 'zone.js/dist/zone-mix';
import 'reflect-metadata';
import 'polyfills';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';

import {HttpClient, HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
// NG Translate
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

import {ElectronService} from './providers/electron.service';

import {AppComponent} from './components/app/app.component';
import {HomeComponent} from './components/home/home.component';
import {DataService} from './providers/data.service';
import {TaskService} from './providers/task.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AppMaterialModule} from './app-material.module';
import {Configuration} from './providers/configuration';
import {CreateTaskService} from './providers/create-task.service';
import {AssignTaskService} from './providers/assign-task.service';
import {FinishTaskService} from './providers/finish-task.service';

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
  imports: [
    AppRoutingModule,
    AppMaterialModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
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
    AssignTaskService,
    FinishTaskService,
    ElectronService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
