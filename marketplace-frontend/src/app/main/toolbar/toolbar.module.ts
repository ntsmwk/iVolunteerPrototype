import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule, MatMenuModule, MatProgressBarModule, MatSelectModule, MatToolbarModule} from '@angular/material';

import {FuseSharedModule} from '@fuse/shared.module';

import {FuseToolbarComponent} from 'app/main/toolbar/toolbar.component';
import {FuseSearchBarModule, FuseShortcutsModule} from '@fuse/components';
import {FuseUserMenuComponent} from './user-menu/user-menu.component';
import {FuseMarketplaceSelectionComponent} from './marketplace-selection/marketplace-selection.component';

@NgModule({
  declarations: [
    FuseToolbarComponent,
    FuseUserMenuComponent,
    FuseMarketplaceSelectionComponent
  ],
  imports: [
    RouterModule,

    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatMenuModule,
    MatProgressBarModule,
    MatToolbarModule,

    FuseSharedModule,
    FuseSearchBarModule,
    FuseShortcutsModule
  ],
  exports: [
    FuseToolbarComponent
  ]
})
export class FuseToolbarModule {
}
