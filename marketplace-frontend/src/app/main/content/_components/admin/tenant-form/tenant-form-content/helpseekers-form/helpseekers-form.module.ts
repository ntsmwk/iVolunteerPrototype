import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { TenantHelpseekersFormComponent } from './helpseekers-form.component';
import { MatCommonModule, MatTableModule, MatButtonModule, MatIconModule } from '@angular/material';
import { DialogFactoryModule } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.module';


@NgModule({
  exports: [TenantHelpseekersFormComponent],
  declarations: [TenantHelpseekersFormComponent],
  imports: [
    FuseSharedModule,
    MatCommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    DialogFactoryModule,
  ],
  providers: [TenantHelpseekersFormComponent],
})
export class TenantHelpseekersFormModule { }
