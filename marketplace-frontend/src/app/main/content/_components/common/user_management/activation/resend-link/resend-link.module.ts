import { NgModule } from '@angular/core';
import { ResendLinkComponent } from './resend-link.component';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatInputModule, MatCommonModule } from '@angular/material';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    FormsModule,
    MatCommonModule,
    MatInputModule,
    MatButtonModule,
    FuseSharedModule,
  ],
  exports: [ResendLinkComponent],
  declarations: [ResendLinkComponent],
  providers: [],
})
export class ResendLinkModule { }
