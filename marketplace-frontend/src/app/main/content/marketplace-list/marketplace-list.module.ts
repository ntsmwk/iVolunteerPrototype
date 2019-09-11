import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatFormFieldModule, MatIconModule, MatTableModule} from '@angular/material';
import {FuseMarketplaceListComponent} from './marketplace-list.component';

const routes = [
  {path: '', component: FuseMarketplaceListComponent}
];

@NgModule({
  declarations: [
    FuseMarketplaceListComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatFormFieldModule,
    MatIconModule,
    MatTableModule,
    MatButtonModule,

    FuseSharedModule
  ]
})
export class FuseMarketplaceListModule {
}
