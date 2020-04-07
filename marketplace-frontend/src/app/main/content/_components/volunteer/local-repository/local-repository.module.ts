import { NgModule } from "@angular/core";

import { RouterModule } from "@angular/router";
import { MatTableModule, MatPaginatorModule, MatSortModule, MatDividerModule, MatButtonModule, MatCardModule } from '@angular/material';
import { LocalRepositoryComponent } from './local-repository.component';
import { CommonModule } from '@angular/common';
import { OrganisationFilterModule } from 'app/main/content/_shared_components/organisation-filter/organisation-filter.module';

const routes = [{ path: "", component: LocalRepositoryComponent }];

@NgModule({
  imports: [
    RouterModule.forChild(routes),

    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatDividerModule,
    MatButtonModule,
    OrganisationFilterModule,
    MatCardModule

  ],
  exports: [],
  declarations: [
    LocalRepositoryComponent
  ],
  providers: []
})
export class LocalRepositoryModule { }
