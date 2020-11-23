import { MatchingConfiguratorComponent } from './matching-configurator.component';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatProgressSpinnerModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';

const routes = [
  { path: '', component: MatchingConfiguratorComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    MatCommonModule,
    MatProgressSpinnerModule,
    FuseSharedModule,
  ],
  declarations: [MatchingConfiguratorComponent],
})

export class MatchingConfiguratorModule { }
