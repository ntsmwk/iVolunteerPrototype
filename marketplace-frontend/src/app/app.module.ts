import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {TranslateModule} from '@ngx-translate/core';
import 'hammerjs';

import {FuseSharedModule} from '@fuse/shared.module';

import {fuseConfig} from './fuse-config';

import {AppComponent} from './app.component';
import {FuseMainModule} from './main/main.module';
import { FuseModule } from '@fuse/fuse.module';


const appRoutes: Routes = [
  {
    path: '**',
    redirectTo: 'main/dashboard'
  }
];

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot(),
    RouterModule.forRoot(appRoutes, {preloadingStrategy: PreloadAllModules/*, enableTracing: true*/}),

    // Fuse Main and Shared modules
    FuseSharedModule,
    FuseModule.forRoot(fuseConfig),
    FuseMainModule
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
