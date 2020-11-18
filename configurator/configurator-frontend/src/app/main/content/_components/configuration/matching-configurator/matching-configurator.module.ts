import { MatchingConfiguratorComponent } from './matching-configurator.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatTooltipModule, MatCheckboxModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatchingEditorTopMenuBarModule } from './top-menu-bar/top-menu-bar.module';
import { MatchingOptionsOverlayControlModule } from './options-overlay/options-overlay-control/options-overlay-control.module';

const routes = [
  { path: '', component: MatchingConfiguratorComponent }
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatCommonModule,
    MatTooltipModule,
    MatCheckboxModule,
    FuseSharedModule,

    MatchingEditorTopMenuBarModule,
    MatchingOptionsOverlayControlModule,
  ],
  declarations: [MatchingConfiguratorComponent],
})

export class MatchingConfiguratorModule { }
