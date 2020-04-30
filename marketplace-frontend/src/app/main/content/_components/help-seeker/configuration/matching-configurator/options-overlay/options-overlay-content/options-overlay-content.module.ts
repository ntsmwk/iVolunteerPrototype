import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatchingOptionsOverlayContentComponent } from './options-overlay-content.component';
import {
    MatCheckboxModule, MatInputModule, MatFormFieldModule, MatSliderModule, MatSlideToggleModule,
    MatDividerModule, MatMenuModule, MatTooltipModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FormsModule } from '@angular/forms';

@NgModule({
    imports: [
        CommonModule,
        FuseSharedModule,
        MatCommonModule,
        MatButtonModule,
        MatIconModule,
        MatSlideToggleModule,
        MatInputModule,
        MatFormFieldModule,
        FormsModule,
        MatSliderModule,
        MatDividerModule,
        MatCheckboxModule,
        MatMenuModule,
        MatTooltipModule,
    ],
    declarations: [MatchingOptionsOverlayContentComponent],
    exports: [MatchingOptionsOverlayContentComponent]
})
export class MatchingOptionsOverlayContentModule { }
