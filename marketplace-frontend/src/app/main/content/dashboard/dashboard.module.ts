import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FuseSharedModule } from '@fuse/shared.module';

import { DashboardComponent } from './dashboard.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule, MatIcon } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FuseWidgetModule } from '@fuse/components';
import { ShareDialog } from './dashboard-volunteer/share-dialog/share-dialog.component';
import { DashboardVolunteerComponent } from './dashboard-volunteer/dashboard-volunteer.component';
import { DashboardHelpSeekerComponent } from './dashboard-helpseeker/dashboard-helpseeker.component';
import { AssetInboxModule } from './dashboard-volunteer/asset-inbox/asset-inbox.module';
import { MatBadgeModule, MatCardModule } from '@angular/material';




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
    MatBadgeModule,
    
    AssetInboxModule,

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
