import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PreloadAllModules, RouterModule, Routes, NoPreloading } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import 'hammerjs';

import { FuseSharedModule } from '@fuse/shared.module';
import { fuseConfig } from './fuse-config';
import { AppComponent } from './app.component';
import { FuseMainModule } from './main/main.module';
import { FuseModule } from '@fuse/fuse.module';
import { DragulaModule } from 'ng2-dragula';
import { HttpClientModule } from '@angular/common/http';

const appRoutes: Routes = [
  {
    path: '**',
    // TODO @MWE fix....
    // redirectTo: 'main/dashboard'
    redirectTo: 'login'
  }
];

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot(),
    RouterModule.forRoot(appRoutes, {
      preloadingStrategy: NoPreloading /*, enableTracing: true*/
    }),

    DragulaModule.forRoot(),

    // Fuse Main and Shared modules
    FuseSharedModule,
    FuseModule.forRoot(fuseConfig),
    FuseMainModule,

    HttpClientModule
  ],
  bootstrap: [AppComponent],
  providers: []
})
export class AppModule { }
