import {NgModule} from '@angular/core';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatIconModule} from '@angular/material';
import {MarketplacesComponent} from './marketplaces.component';


@NgModule({
  declarations: [MarketplacesComponent],
  imports: [
    MatIconModule,
    MatButtonModule,

    FuseSharedModule
  ],
  exports: [MarketplacesComponent]
})

export class FuseMarketplacesModule {
}
