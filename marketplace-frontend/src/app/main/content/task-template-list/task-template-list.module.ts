import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';

import {FuseSharedModule} from '@fuse/shared.module';

import {MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule, MatInputModule, MatSidenavModule, MatTableModule} from '@angular/material';
import {FuseTaskTemplateListComponent} from './task-template-list.component';

const routes = [
  {path: '', component: FuseTaskTemplateListComponent}
];

@NgModule({
  declarations: [
    FuseTaskTemplateListComponent
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
    MatTableModule,
    MatIconModule,
    
    FuseSharedModule
  ]
})
export class FuseTaskTemplateListModule {
}
