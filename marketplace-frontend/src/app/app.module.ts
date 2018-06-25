import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterModule, Routes} from '@angular/router';
import {TranslateModule} from '@ngx-translate/core';
import 'hammerjs';

import {FuseModule} from '@fuse/fuse.module';
import {FuseSharedModule} from '@fuse/shared.module';

import {fuseConfig} from './fuse-config';

import {AppComponent} from './app.component';
import {FuseMainModule} from './main/main.module';

const appRoutes: Routes = [
  {
    path: '**',
    redirectTo: 'main'
  }
];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot(),
    RouterModule.forRoot(appRoutes),

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
