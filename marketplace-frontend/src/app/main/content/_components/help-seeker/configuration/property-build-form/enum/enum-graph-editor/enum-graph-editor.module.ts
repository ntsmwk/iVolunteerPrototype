import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { EnumGraphEditorComponent } from './enum-graph-editor.component';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,
    ],
    declarations: [EnumGraphEditorComponent],
    exports: [EnumGraphEditorComponent]
})
export class EnumGraphEditorModule { }
