import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatFormFieldModule, MatInputModule, MatButtonModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { EnumBuilderComponent } from './enum-builder.component';
import { EnumGraphEditorModule } from './enum-graph-editor/enum-graph-editor.module';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,

        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,

        EnumGraphEditorModule,


    ],
    declarations: [EnumBuilderComponent],
    exports: [EnumBuilderComponent]
})
export class EnumBuilderModule { }
