import { NgModule } from '@angular/core';
import { ConfiguratorComponent } from './configurator.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule } from '@angular/material/core';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FuseSharedModule } from '@fuse/shared.module';

const routes = [
    { path: '', component: ConfiguratorComponent }
];

@NgModule({
    imports: [
        FuseSharedModule,
        RouterModule.forChild(routes),
        MatCommonModule,
        MatProgressSpinnerModule,
    ],
    declarations: [ConfiguratorComponent]
})



export class ConfiguratorModule { }