import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { EnumBuilderComponent } from './enum-builder.component';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,

    ],
    declarations: [EnumBuilderComponent],
    exports: [EnumBuilderComponent]
})
export class EnumBuilderModule { }
