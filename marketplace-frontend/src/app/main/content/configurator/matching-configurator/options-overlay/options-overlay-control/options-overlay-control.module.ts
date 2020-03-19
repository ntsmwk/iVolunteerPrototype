import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { OptionsOverlayControlComponent } from './options-overlay-control.component';
import { MatTableModule, MatCheckboxModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { OptionsOverlayModule } from '../options-overlay.module';

@NgModule({
    imports: [
        CommonModule,
        FuseSharedModule,
        MatCommonModule,
        MatButtonModule,
        MatIconModule,
        MatTableModule,
        MatCheckboxModule,
        OptionsOverlayModule,
    ],
    declarations: [OptionsOverlayControlComponent],
    exports: [OptionsOverlayControlComponent]
})
export class OptionsOverlayControlModule { }