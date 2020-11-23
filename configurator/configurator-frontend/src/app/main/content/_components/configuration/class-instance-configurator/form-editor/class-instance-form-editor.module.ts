import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormEditorComponent } from './class-instance-form-editor.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatExpansionModule, MatDividerModule, MatButtonModule, } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FormContainerModule } from './form-container/form-container.module';
import { InstanceCreationResultModule } from './result/result.module';

const routes = [
    { path: '', component: ClassInstanceFormEditorComponent }
    // { path: ':marketplaceId', component: ClassInstanceFormEditorComponent }
    // {path: ':marketplaceId/:classId/:showMaxGluehtemperatur', component: ClassInstanceFormEditorComponent}
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        MatCommonModule,
        MatExpansionModule,
        MatButtonModule,
        MatDividerModule,
        FuseSharedModule,
        FormContainerModule,
        InstanceCreationResultModule,
    ],
    declarations: [ClassInstanceFormEditorComponent],
})



export class ClassInstanceFormEditorModule { }