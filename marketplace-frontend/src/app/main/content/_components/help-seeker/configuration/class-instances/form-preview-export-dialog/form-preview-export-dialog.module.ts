import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClassInstanceFormPreviewExportDialogComponent } from './form-preview-export-dialog.component';
import { FuseSharedModule } from '@fuse/shared.module';
import {
    DynamicClassInstanceCreationFormModule
} from 'app/main/content/_shared_components/dynamic-forms/dynamic-class-instance-creation-form/dynamic-class-instance-creation-form.module';
import { MatExpansionModule } from '@angular/material';
import { FormEntryViewModule } from '../form-editor/form-entry-view/form-entry-view.module';

// const routes = [
//   { path: ':marketplaceId', component: ClassInstanceFormPreviewDialogComponent }
// ];

@NgModule({
    imports: [
        CommonModule,
        FuseSharedModule,
        DynamicClassInstanceCreationFormModule,
        MatExpansionModule,
        FormEntryViewModule,
    ],
    declarations: [ClassInstanceFormPreviewExportDialogComponent],
    exports: [ClassInstanceFormPreviewExportDialogComponent]


})



export class ClassInstanceFormPreviewExportDialogModule { }