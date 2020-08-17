import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { FuseSharedModule } from '@fuse/shared.module';
import { TreePropertyOptionsOverlayContentModule } from '../options-overlay-content/options-overlay-content.module';
import { TreePropertyOptionsOverlayControlComponent } from './options-overlay-control.component';

@NgModule({
    imports: [
        CommonModule,
        FuseSharedModule,
        MatCommonModule,
        MatButtonModule,
        MatIconModule,
        TreePropertyOptionsOverlayContentModule,
    ],
    declarations: [TreePropertyOptionsOverlayControlComponent],
    exports: [TreePropertyOptionsOverlayControlComponent]
})
export class TreePropertyOptionsOverlayControlModule { }
