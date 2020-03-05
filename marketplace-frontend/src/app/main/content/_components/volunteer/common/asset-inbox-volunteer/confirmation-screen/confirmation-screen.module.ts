import { NgModule } from '@angular/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { VolunteerConfirmationScreenComponent } from './confirmation-screen.component';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material';
import { RouterModule } from '@angular/router';

const routes = [
  { path: '', component: VolunteerConfirmationScreenComponent }
];

@NgModule({
  declarations: [
    VolunteerConfirmationScreenComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),       
    FuseSharedModule,
    MatButtonModule,
  
  ],
  exports: [
    VolunteerConfirmationScreenComponent
  ]
})

export class VolunteerConfirmationScreenModule {
}
