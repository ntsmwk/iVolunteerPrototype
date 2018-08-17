import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule, MatInputModule, MatOptionModule, MatSelectModule, MatSidenavModule} from '@angular/material';
import {FuseTaskTemplateFormComponent} from './task-template-form.component';

const routes = [
  {path: '', component: FuseTaskTemplateFormComponent},
  {path: ':taskTemplateId', component: FuseTaskTemplateFormComponent}
];

@NgModule({
  declarations: [
    FuseTaskTemplateFormComponent
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
  ]
})
export class FuseTaskTemplateFormModule {
}
