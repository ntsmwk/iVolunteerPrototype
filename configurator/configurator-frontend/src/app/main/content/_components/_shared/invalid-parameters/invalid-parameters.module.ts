import { NgModule } from "@angular/core";

import { FuseSharedModule } from "@fuse/shared.module";
import { InvalidParametersComponent } from './invalid-parameters.component';
import { Route, RouterModule } from '@angular/router';

const routes: Route[] = [{ path: '', component: InvalidParametersComponent }];

@NgModule({
  imports: [
    FuseSharedModule,
    RouterModule.forChild(routes),
  ],
  exports: [InvalidParametersComponent],
  declarations: [InvalidParametersComponent],
  providers: []
})
export class InvalidParametersModule { }
