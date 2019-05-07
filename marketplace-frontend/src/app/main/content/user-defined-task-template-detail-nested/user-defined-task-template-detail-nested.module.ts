import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NestedUserDefinedTaskTemplateDetailComponent } from './user-defined-task-template-detail-nested.component';
import { FuseSharedModule } from '@fuse/shared.module';
import { RouterModule } from '@angular/router';
import { MatIconModule, MatCommonModule, MatExpansionModule, MatDividerModule, MatButtonModule, MatTableModule, MatTooltipModule, MatMenuModule } from '@angular/material';
import { DialogFactoryModule } from '../_components/dialogs/_dialog-factory/dialog-factory.module';

const routes = [
  {path: ':marketplaceId/:templateId', component: NestedUserDefinedTaskTemplateDetailComponent}
];

@NgModule({
  declarations: [NestedUserDefinedTaskTemplateDetailComponent],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),

    MatCommonModule,
    MatDividerModule,
    MatExpansionModule,
    MatButtonModule,
    MatTableModule,
    MatIconModule,
    MatTooltipModule,

    MatMenuModule,


    DialogFactoryModule,

    
    FuseSharedModule,


   
  ],
  
})
export class NestedUserDefinedTaskTemplateDetailModule { }
