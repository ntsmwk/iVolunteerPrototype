import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatButtonModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { TreePropertyGraphEditorComponent } from './tree-property-graph-editor.component';
import { TreePropertyOptionsOverlayControlModule } from './options-overlay/options-overlay-control/options-overlay-control.module';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,
        MatButtonModule,
        TreePropertyOptionsOverlayControlModule,
    ],
    declarations: [TreePropertyGraphEditorComponent],
    exports: [TreePropertyGraphEditorComponent]
})
export class TreePropertyGraphEditorModule { }
