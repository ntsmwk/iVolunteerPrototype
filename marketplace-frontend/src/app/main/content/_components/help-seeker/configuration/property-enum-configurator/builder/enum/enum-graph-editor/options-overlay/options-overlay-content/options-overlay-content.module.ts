import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EnumOptionsOverlayContentComponent } from './options-overlay-content.component';


@NgModule({
    imports: [
        CommonModule,

    ],
    declarations: [EnumOptionsOverlayContentComponent],
    exports: [EnumOptionsOverlayContentComponent]
})
export class EnumOptionsOverlayContentModule { }