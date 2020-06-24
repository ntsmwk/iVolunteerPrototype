import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSlideToggle, MatSlideToggleModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { EnumBuilderComponent } from './enum-builder.component';
import { EnumGraphEditorModule } from './enum-graph-editor/enum-graph-editor.module';
import { OpenEnumDefinitionDialogComponent } from './enum-graph-editor/open-enum-definition-dialog/open-enum-definition-dialog.component';
import { OpenEnumDefinitionDialogModule } from './enum-graph-editor/open-enum-definition-dialog/open-enum-definition-dialog.module';
import { DeleteEnumDefinitionDialogComponent } from './enum-graph-editor/delete-enum-definition-dialog/delete-enum-definition-dialog.component';
import { DeleteEnumDefinitionDialogModule } from './enum-graph-editor/delete-enum-definition-dialog/delete-enum-definition-dialog.module';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,

        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatSlideToggleModule,

        EnumGraphEditorModule,

        OpenEnumDefinitionDialogModule,
        DeleteEnumDefinitionDialogModule,

    ],
    entryComponents: [
        OpenEnumDefinitionDialogComponent,
        DeleteEnumDefinitionDialogComponent,
    ],
    declarations: [EnumBuilderComponent],
    exports: [EnumBuilderComponent]
})
export class EnumBuilderModule { }
