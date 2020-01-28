import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShareMenuComponent } from './share-menu.component';
import { MatMenuModule, MatIconModule, MatInputModule, MatFormFieldModule, MatButtonModule, MatListModule, MatOptionModule, MatProgressBarModule, MatExpansionModule, MatSelectModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';


@NgModule({
  declarations: [
    ShareMenuComponent
  ],
  imports: [
    CommonModule,
    
    MatProgressBarModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    MatListModule,
    MatOptionModule,
    MatSelectModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatInputModule,

    FuseSharedModule


  ],
  exports: [
    ShareMenuComponent,
  ]
})
export class ShareMenuModule { }
