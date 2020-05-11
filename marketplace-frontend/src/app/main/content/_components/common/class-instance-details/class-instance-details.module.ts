import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { CommonModule } from '@angular/common';
import { FuseSharedModule } from "@fuse/shared.module";
import { ClassInstanceDetailsComponent } from './class-instance-details.component';
import { MatIconModule, MatTableModule, MatSortModule } from '@angular/material';


const routes = [
  { path: ":id/:tenantId", component: ClassInstanceDetailsComponent }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes),
    CommonModule,

    MatSortModule,
    MatIconModule,
    MatTableModule,
    FuseSharedModule,
  ],
  declarations: [
    ClassInstanceDetailsComponent
  ]
})
export class ClassInstanceDetailsModule { }
