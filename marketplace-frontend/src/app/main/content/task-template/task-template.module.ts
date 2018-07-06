import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSidenavModule, MatOptionModule, MatSelectModule} from '@angular/material';
import { FuseTaskTemplateComponent } from './task-template.component';

const routes = [
  {path: '', component: FuseTaskTemplateComponent}
];

@NgModule({
  declarations: [
    FuseTaskTemplateComponent
  ],
  imports: [
    ReactiveFormsModule,
    RouterModule.forChild(routes),

    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatSelectModule,
    MatOptionModule,
    FuseSharedModule
  ],
  exports: [
    FuseTaskTemplateComponent
  ]
})
export class FuseTaskTemplateModule {
}
