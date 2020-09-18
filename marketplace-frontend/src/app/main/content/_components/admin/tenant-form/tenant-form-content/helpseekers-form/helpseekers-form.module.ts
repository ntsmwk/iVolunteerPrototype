import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { TenantHelpseekersFormComponent } from './helpseekers-form.component';
import { MatCommonModule, MatTableModule, MatButtonModule, MatIconModule } from '@angular/material';


@NgModule({
  exports: [TenantHelpseekersFormComponent],
  declarations: [TenantHelpseekersFormComponent],
  imports: [
    FuseSharedModule,
    MatCommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
  ],
  providers: [TenantHelpseekersFormComponent],
})
export class TenantHelpseekersFormModule { }
