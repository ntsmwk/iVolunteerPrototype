import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { AssetInboxVolunteerComponent } from './asset-inbox-volunteer.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule, MatMenuModule } from '@angular/material';
import { RouterModule } from '@angular/router';
import { AssetInboxModule } from '../../../_shared/asset-inbox/asset-inbox.module';

const routes = [{ path: '', component: AssetInboxVolunteerComponent }];

@NgModule({
  declarations: [AssetInboxVolunteerComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    FuseSharedModule,
    MatButtonModule,
    MatMenuModule,

    AssetInboxModule
  ],
  exports: [AssetInboxVolunteerComponent]
})
export class AssetInboxVolunteerModule { }
