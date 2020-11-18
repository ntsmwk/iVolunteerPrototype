import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PropertyBuildFormComponent } from './property-build-form.component';
import { RouterModule } from '@angular/router';
import {
  MatProgressSpinnerModule, MatCommonModule, MatIconModule, MatTabsModule,
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { HeaderModule } from 'app/main/content/_components/_shared/header/header.module';
import { BuilderContainerModule } from './builder-container/builder-container.module';

const routes = [
  // { path: ':marketplaceId/:entryId', component: PropertyBuildFormComponent },
  { path: '', component: PropertyBuildFormComponent },
  { path: ':entryId', component: PropertyBuildFormComponent },
];

@NgModule({
  imports: [
    CommonModule,
    FuseSharedModule,

    RouterModule.forChild(routes),

    MatCommonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    BuilderContainerModule,
    HeaderModule,
    MatTabsModule,
  ],
  declarations: [PropertyBuildFormComponent],
  providers: [PropertyBuildFormComponent],
})
export class PropertyBuildFormModule { }
