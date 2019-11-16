import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuseSharedModule } from '@fuse/shared.module';

import { DashboardComponent } from './dashboard.component';
import { MatIconModule, MatButtonModule, MatTableModule, MatIcon, MatTabsModule, MatFormFieldModule, MatSelectModule, MatCommonModule, MatDividerModule, MatMenuModule, MatTooltipModule, MatDialogModule, MatRadioModule, MatProgressSpinnerModule } from '@angular/material';
import { FuseWidgetModule } from '@fuse/components';
import { ShareDialog } from './dashboard-volunteer/share-dialog/share-dialog.component';
import { DashboardVolunteerComponent } from './dashboard-volunteer/dashboard-volunteer.component';
import { DashboardHelpSeekerComponent } from './dashboard-helpseeker/dashboard-helpseeker.component';




const routes = [
  {
    path: '',
    component: DashboardComponent
  }
];

@NgModule({
  declarations: [
    DashboardVolunteerComponent,
    DashboardHelpSeekerComponent,
    DashboardComponent,
    ShareDialog
  ],
  imports: [
    RouterModule.forChild(routes),

    MatIconModule,

    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatTabsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCommonModule,
    MatDividerModule,
    MatMenuModule,
    MatTooltipModule,
    MatDialogModule,
    MatRadioModule,
    MatProgressSpinnerModule,


    FuseSharedModule,
    FuseWidgetModule
  ],
  exports: [
    DashboardComponent
  ],
  entryComponents: [
    ShareDialog
  ]
})

export class FuseDashboardModule {
}
