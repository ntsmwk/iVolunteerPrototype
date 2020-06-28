import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatButtonModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { EnumGraphEditorComponent } from './enum-graph-editor.component';
import { EnumOptionsOverlayControlModule } from './options-overlay/options-overlay-control/options-overlay-control.module';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,
        MatButtonModule,
        EnumOptionsOverlayControlModule,
    ],
    declarations: [EnumGraphEditorComponent],
    exports: [EnumGraphEditorComponent]
})
export class EnumGraphEditorModule { }
