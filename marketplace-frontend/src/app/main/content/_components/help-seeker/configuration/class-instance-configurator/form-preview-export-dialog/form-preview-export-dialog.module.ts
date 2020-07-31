import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormPreviewExportDialogComponent } from './form-preview-export-dialog.component';
import { FuseSharedModule } from '@fuse/shared.module';
import {
    DynamicFormBlockModule
} from 'app/main/content/_components/_shared/dynamic-forms/dynamic-form-block/dynamic-form-block.module';
import { MatExpansionModule, MatButtonModule } from '@angular/material';
import { FormPreviewEntryModule } from './preview-entry/preview-entry.module';

// const routes = [
//   { path: ':marketplaceId', component: ClassInstanceFormPreviewDialogComponent }
// ];

@NgModule({
    imports: [
        CommonModule,
        FuseSharedModule,
        DynamicFormBlockModule,
        MatExpansionModule,
        MatButtonModule,
        FormPreviewEntryModule,
    ],
    declarations: [ClassInstanceFormPreviewExportDialogComponent],
    exports: [ClassInstanceFormPreviewExportDialogComponent]


})



export class ClassInstanceFormPreviewExportDialogModule { }