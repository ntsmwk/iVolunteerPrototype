import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCommonModule, MatTabsModule, MatRadioModule, MatDividerModule } from '@angular/material';
import { FuseSharedModule } from '@fuse/shared.module';
import { BuilderContainerComponent } from './builder-container.component';
import { TreePropertyBuilderModule } from '../tree-property/tree-property-builder.module';
import { FlatPropertyBuilderModule } from '../flat-property/flat-property-builder.module';

@NgModule({
    imports: [
        CommonModule,
        MatCommonModule,
        FuseSharedModule,
        MatTabsModule,

        MatRadioModule,
        MatDividerModule,

        FlatPropertyBuilderModule,
        TreePropertyBuilderModule,



    ],
    declarations: [BuilderContainerComponent],
    exports: [BuilderContainerComponent]
})
export class BuilderContainerModule { }
