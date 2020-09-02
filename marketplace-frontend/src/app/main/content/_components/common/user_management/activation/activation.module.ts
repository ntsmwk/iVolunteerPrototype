import { NgModule } from '@angular/core';
import { ActivationComponent } from './activation.component';
import { RouterModule } from '@angular/router';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule } from '@angular/material';

const routes = [{ path: ':activationId', component: ActivationComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,


    FuseSharedModule,
  ],
  exports: [],
  declarations: [ActivationComponent],
  providers: [],
})
export class ActivationModule { }
