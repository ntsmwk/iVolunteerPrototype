import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import { MatIconModule } from '@angular/material';
import {MarketplacesComponent} from './marketplaces.component';

const routes = [
  {path: '**', component: MarketplacesComponent}
];

@NgModule({
  declarations: [
    MarketplacesComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    FuseSharedModule,
    MatIconModule
  ]
})

export class FuseMarketplacesModule {
}
