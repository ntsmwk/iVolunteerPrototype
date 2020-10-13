import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TreePropertyOptionsOverlayContentComponent } from './options-overlay-content.component';
import { MatCommonModule, MatRadioModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';


@NgModule({
    imports: [
        CommonModule,
        FuseSharedModule,
        MatCommonModule,
        MatRadioModule,
    ],
    declarations: [TreePropertyOptionsOverlayContentComponent],
    exports: [TreePropertyOptionsOverlayContentComponent]
})
export class TreePropertyOptionsOverlayContentModule { }