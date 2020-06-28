import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnumOptionsOverlayContentComponent } from './options-overlay-content.component';
import { MatCommonModule, MatListModule, MatButtonModule, MatMenuModule, MatSlideToggleModule, MatRadioModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';


@NgModule({
    imports: [
        CommonModule,
        FuseSharedModule,
        MatCommonModule,
        MatRadioModule,
    ],
    declarations: [EnumOptionsOverlayContentComponent],
    exports: [EnumOptionsOverlayContentComponent]
})
export class EnumOptionsOverlayContentModule { }