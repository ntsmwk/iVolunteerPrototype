import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCommonModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import {
    MatCheckboxModule, MatInputModule, MatFormFieldModule, MatSliderModule, MatSlideToggleModule,
    MatDividerModule, MatMenuModule, MatTooltipModule
} from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { FormsModule } from '@angular/forms';
import { RelationshipOptionsOverlayContentComponent } from './relationship-options-overlay-content.component';

@NgModule({
    imports: [
        CommonModule,
        FuseSharedModule,
        MatCommonModule,
        MatButtonModule,
        MatIconModule,
        MatInputModule,
        MatFormFieldModule,
        FormsModule,
        MatCheckboxModule,
        MatMenuModule,
        MatTooltipModule,
    ],
    declarations: [RelationshipOptionsOverlayContentComponent],
    exports: [RelationshipOptionsOverlayContentComponent]
})
export class RelationshipOptionsOverlayContentModule { }