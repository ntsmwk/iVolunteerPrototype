import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatchingOptionsOverlayControlComponent } from './options-overlay-control.component';
import { FuseSharedModule } from '@fuse/shared.module';
import { MatchingOptionsOverlayContentModule } from '../options-overlay-content/options-overlay-content.module';

@NgModule({
    imports: [
        CommonModule,
        FuseSharedModule,
        MatCommonModule,
        MatButtonModule,
        MatIconModule,
        MatchingOptionsOverlayContentModule,
    ],
    declarations: [MatchingOptionsOverlayControlComponent],
    exports: [MatchingOptionsOverlayControlComponent]
})
export class MatchingOptionsOverlayControlModule { }
