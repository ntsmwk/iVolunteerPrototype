import { NgModule } from '@angular/core';
import { ActivationComponent } from './activation.component';
import { RouterModule } from '@angular/router';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatProgressSpinnerModule, MatInputModule, MatCommonModule } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { ResendLinkModule } from './resend-link/resend-link.module';

const routes = [{ path: ':activationId', component: ActivationComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),

    FormsModule,
    MatCommonModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    ResendLinkModule,

    FuseSharedModule,
  ],
  exports: [],
  declarations: [ActivationComponent],
  providers: [],
})
export class ActivationModule { }
