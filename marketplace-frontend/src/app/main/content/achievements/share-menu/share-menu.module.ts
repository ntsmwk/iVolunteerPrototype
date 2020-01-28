import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ShareMenuComponent } from './share-menu.component';
import { MatMenuModule, MatIconModule, MatInputModule, MatFormFieldModule } from '@angular/material';


@NgModule({
  declarations: [
    ShareMenuComponent
  ],
  imports: [
    CommonModule,
    MatMenuModule,
    MatIconModule,
    MatInputModule,
    MatFormFieldModule,
  ],
  exports: [
    ShareMenuComponent,
  ]
})
export class ShareMenuModule { }
