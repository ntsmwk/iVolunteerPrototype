import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatTabsModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { SinglePropertyBuilderModule } from '../single-property/single-property-builder.module';
import { BuilderContainerComponent } from './builder-container.component';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,
        MatTabsModule,
        SinglePropertyBuilderModule,


    ],
    declarations: [BuilderContainerComponent],
    exports: [BuilderContainerComponent]
})
export class BuilderContainerModule { }
