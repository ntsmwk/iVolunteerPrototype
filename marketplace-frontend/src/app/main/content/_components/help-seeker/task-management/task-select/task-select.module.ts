import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { FuseSharedModule } from '@fuse/shared.module';
import { FuseTaskSelectComponent } from './task-select.component';
import { MatProgressSpinnerModule } from '@angular/material';

const routes: Route[] = [{ path: '', component: FuseTaskSelectComponent }];

@NgModule({
  declarations: [FuseTaskSelectComponent],
  imports: [
    RouterModule.forChild(routes),
    FuseSharedModule,
    MatProgressSpinnerModule,
  ],
  providers: [],
})
export class FuseTaskSelectModule { }
