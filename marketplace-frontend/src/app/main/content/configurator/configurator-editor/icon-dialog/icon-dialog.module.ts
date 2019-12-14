import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IconDialogComponent } from './icon-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatButtonModule,
  ],
  declarations: [IconDialogComponent],
  exports: [IconDialogComponent]
})
export class IconDialogModule { }
