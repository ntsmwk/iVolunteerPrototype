import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseDashboardComponent} from './dashboard.component';
import {MatButtonModule, MatDividerModule, MatExpansionModule, MatIconModule, MatMenuModule, MatOptionModule, MatSelectModule, MatSidenavModule} from '@angular/material';
import {FuseWidgetModule} from '../../../../@fuse/components';
import { FuseVolunteerDashboardComponent } from './dashboard-volunteer/dashboard-volunteer.component';
import { FuseHelpSeekerDashboardComponent } from './dashboard-helpseeker/dashboard-helpseeker.component';

const routes = [
  {path: '', component: FuseDashboardComponent}
];

@NgModule({
  declarations: [
    FuseVolunteerDashboardComponent,
    FuseHelpSeekerDashboardComponent,
    FuseDashboardComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatOptionModule,
    MatSelectModule,
    MatExpansionModule,
    MatSidenavModule,
    MatDividerModule,
    FuseWidgetModule,

    FuseSharedModule
  ]
})

export class FuseDashboardModule {
}
