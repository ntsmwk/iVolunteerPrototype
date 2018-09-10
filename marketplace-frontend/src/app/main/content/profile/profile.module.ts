import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {MatButtonModule, MatDividerModule, MatIconModule, MatTabsModule, MatListModule, MatTableModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseProfileComponent} from './profile.component';
import {FuseProfileAboutComponent} from './tabs/about/about.component';
import {FuseProfileFriendsComponent} from './tabs/friends/friends.component';
import {FuseProfileMarketplacesComponent} from './tabs/marketplaces/marketplaces.component';
import {FuseWidgetModule} from '../../../../@fuse/components';
import { FuseProfileCompetenciesComponent } from './tabs/competencies/competencies.component';
import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';

const routes = [
  {path: '', component: FuseProfileComponent},
  {path: ':participantId', component: FuseProfileComponent}
];

@NgModule({
  declarations: [
    FuseProfileComponent,
    FuseProfileAboutComponent,
    FuseProfileFriendsComponent,
    FuseProfileMarketplacesComponent,
    FuseProfileCompetenciesComponent
  ],
  imports: [
    RouterModule.forChild(routes),
    CdkTableModule,
    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    MatTabsModule,
    MatListModule,
    MatTableModule,
    CommonModule,
    
    FuseWidgetModule,
    FuseSharedModule
  ]
})
export class FuseProfileModule {
}
