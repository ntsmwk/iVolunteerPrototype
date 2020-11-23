import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowseClassSubDialogComponent } from './browse-sub-dialog.component';
import { MatCommonModule } from '@angular/material/core';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatButtonModule, MatTooltipModule, MatIconModule, MatFormFieldModule, MatInputModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,

    MatCommonModule,
    MatButtonModule,
    MatTooltipModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,

    FuseSharedModule,
  ],

  declarations: [BrowseClassSubDialogComponent],
  exports: [BrowseClassSubDialogComponent],


})



export class BrowseClassSubDialogModule { }
