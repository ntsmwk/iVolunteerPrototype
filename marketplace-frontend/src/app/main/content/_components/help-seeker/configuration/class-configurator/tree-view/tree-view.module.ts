import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditorTreeViewComponent } from './tree-view.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTreeModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatCommonModule,
    MatTreeModule,
    MatIconModule,
    MatButtonModule,
  ],
  declarations: [EditorTreeViewComponent],
  exports: [EditorTreeViewComponent]
})
export class EditorTreeViewModule { }
