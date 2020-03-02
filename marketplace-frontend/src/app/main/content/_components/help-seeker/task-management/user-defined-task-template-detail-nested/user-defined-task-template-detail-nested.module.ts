import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NestedUserDefinedTaskTemplateDetailComponent } from './user-defined-task-template-detail-nested.component';
import { FuseSharedModule } from '@fuse/shared.module';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { DialogFactoryModule } from '../../../../_shared_components/dialogs/_dialog-factory/dialog-factory.module';

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
