import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatTabsModule, MatRadioModule, MatDividerModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { SinglePropertyBuilderModule } from '../single-property/single-property-builder.module';
import { BuilderContainerComponent } from './builder-container.component';
import { EnumBuilderModule } from '../enum/enum-builder.module';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,
        MatTabsModule,

        MatRadioModule,
        MatDividerModule,

        SinglePropertyBuilderModule,
        EnumBuilderModule,



    ],
    declarations: [BuilderContainerComponent],
    exports: [BuilderContainerComponent]
})
export class BuilderContainerModule { }
