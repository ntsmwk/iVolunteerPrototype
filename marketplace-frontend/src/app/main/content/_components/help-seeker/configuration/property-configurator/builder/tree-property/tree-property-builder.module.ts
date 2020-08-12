import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSlideToggleModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { TreePropertyBuilderComponent } from './tree-property-builder.component';
import { TreePropertyGraphEditorModule } from './tree-property-graph-editor/tree-property-graph-editor.module';
import { OpenTreePropertyDefinitionDialogComponent } from './tree-property-graph-editor/open-tree-property-definition-dialog/open-tree-property-definition-dialog.component';
import { OpenTreePropertyDefinitionDialogModule } from './tree-property-graph-editor/open-tree-property-definition-dialog/open-tree-property-definition-dialog.module';
import { DeleteTreePropertyDefinitionDialogComponent } from './tree-property-graph-editor/delete-tree-property-definition-dialog/delete-tree-property-definition-dialog.component';
import { DeleteTreePropertyDefinitionDialogModule } from './tree-property-graph-editor/delete-tree-property-definition-dialog/delete-tree-property-definition-dialog.module';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,

        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatSlideToggleModule,

        TreePropertyGraphEditorModule,

        OpenTreePropertyDefinitionDialogModule,
        DeleteTreePropertyDefinitionDialogModule,

    ],
    entryComponents: [
        OpenTreePropertyDefinitionDialogComponent,
        DeleteTreePropertyDefinitionDialogComponent,
    ],
    declarations: [TreePropertyBuilderComponent],
    exports: [TreePropertyBuilderComponent]
})
export class TreePropertyBuilderModule { }
