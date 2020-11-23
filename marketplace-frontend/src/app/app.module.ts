import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {
  RouterModule,
  Routes,
  NoPreloading,
} from "@angular/router";
import { TranslateModule } from "@ngx-translate/core";
import "hammerjs";

import { FuseSharedModule } from "@fuse/shared.module";
import { fuseConfig } from "./fuse-config";
import { AppComponent } from "./app.component";
import { FuseMainModule } from "./main/main.module";
import { FuseModule } from "@fuse/fuse.module";
import { HttpClientModule } from "@angular/common/http";
import localeDe from "@angular/common/locales/de";
import { registerLocaleData } from "@angular/common";

registerLocaleData(localeDe, "de-AT");

const appRoutes: Routes = [
  {
    path: "**",
    redirectTo: "main/dashboard",
    // redirectTo: 'login'
  },
];

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot(),
    RouterModule.forRoot(appRoutes, {
      preloadingStrategy: NoPreloading /*, enableTracing: true*/,
    }),

    // Fuse Main and Shared modules
    FuseSharedModule,
    FuseModule.forRoot(fuseConfig),
    FuseMainModule,

    HttpClientModule,
  ],
  bootstrap: [AppComponent],
  providers: [],
})
export class AppModule { }
