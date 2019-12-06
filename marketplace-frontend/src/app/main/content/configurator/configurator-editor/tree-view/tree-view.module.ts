import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EditorTreeViewComponent } from './tree-view.component';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatCommonModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
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
