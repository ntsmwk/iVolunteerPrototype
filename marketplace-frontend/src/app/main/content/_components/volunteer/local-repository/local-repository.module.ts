import { NgModule } from "@angular/core";

import { RouterModule } from "@angular/router";
import { MatTableModule, MatPaginatorModule, MatSortModule, MatDividerModule } from '@angular/material';
import { LocalRepositoryComponent } from './local-repository.component';
import { CommonModule } from '@angular/common';

const routes = [{ path: "", component: LocalRepositoryComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),

    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatDividerModule

  ],
  exports: [],
  declarations: [
    LocalRepositoryComponent],
  providers: []
})
export class LocalRepositoryModule { }
