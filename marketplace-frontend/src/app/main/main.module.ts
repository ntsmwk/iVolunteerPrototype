import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';

import {FuseSharedModule} from '@fuse/shared.module';
import {FuseNavigationModule, FuseSearchBarModule, FuseShortcutsModule, FuseSidebarModule} from '@fuse/components';

import {FuseContentModule} from 'app/main/content/content.module';
import {FuseFooterModule} from 'app/main/footer/footer.module';
import {FuseNavbarModule} from 'app/main/navbar/navbar.module';
import {FuseToolbarModule} from 'app/main/toolbar/toolbar.module';

import {FuseMainComponent} from './main.component';




@NgModule({
  declarations: [FuseMainComponent],
  imports: [
    RouterModule,

    MatSidenavModule,

    FuseSharedModule,

    FuseNavigationModule,
    FuseSearchBarModule,
    FuseShortcutsModule,
    FuseSidebarModule,

    FuseContentModule,
    FuseFooterModule,
    FuseNavbarModule,
    FuseToolbarModule,
  ],
  exports: [FuseMainComponent]
})
export class FuseMainModule {
}
