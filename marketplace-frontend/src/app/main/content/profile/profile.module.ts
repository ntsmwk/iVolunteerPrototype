import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {MatButtonModule, MatDividerModule, MatIconModule, MatTabsModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseProfileComponent} from './profile.component';
import {FuseProfileAboutComponent} from './tabs/about/about.component';
import {FuseProfileFriendsComponent} from './tabs/friends/friends.component';
import {FuseProfileRepositoryComponent} from './tabs/repository/repository.component';
import {FuseProfileMarketplacesComponent} from './tabs/marketplaces/marketplaces.component';
import {FuseWidgetModule} from '../../../../@fuse/components';

const routes = [
  {path: '**', component: FuseProfileComponent}
];

@NgModule({
  declarations: [
    FuseProfileComponent,
    FuseProfileAboutComponent,
    FuseProfileFriendsComponent,
    FuseProfileMarketplacesComponent,
    FuseProfileRepositoryComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    MatTabsModule,

    FuseWidgetModule,
    FuseSharedModule
  ]
})
export class FuseProfileModule {
}
