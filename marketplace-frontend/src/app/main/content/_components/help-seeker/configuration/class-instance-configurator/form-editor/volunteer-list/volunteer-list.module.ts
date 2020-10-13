import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatProgressSpinnerModule, MatExpansionModule, MatButtonModule, MatIconModule, MatTableModule, MatCheckboxModule, MatPaginatorModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { InstanceCreationVolunteerListComponent } from './volunteer-list.component';

@NgModule({
  imports: [
    CommonModule,


    MatCommonModule,
    MatProgressSpinnerModule,
    MatExpansionModule,
    MatTableModule,
    MatCheckboxModule,
    MatPaginatorModule,

    MatButtonModule,
    MatIconModule,

    FuseSharedModule,

  ],
  declarations: [InstanceCreationVolunteerListComponent],
  exports: [InstanceCreationVolunteerListComponent]


})



export class InstanceCreationVolunteerListModule { }
