import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChangeIconDialogComponent } from './icon-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule, MatIconModule, MatGridListModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,

    MatButtonModule,
    MatDialogModule,
    MatIconModule,
    MatGridListModule,
    

  ],
  declarations: [ChangeIconDialogComponent],
  exports: [ChangeIconDialogComponent]
})
export class ChangeIconDialogModule { }
