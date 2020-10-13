import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfiguratorComponent } from './configurator.component';
import { RouterModule } from '@angular/router';
import { MatCommonModule, MatOptionModule, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FuseSharedModule } from '@fuse/shared.module';
import { ClassConfiguratorModule } from './class-configurator.module';

const routes = [
    { path: '', component: ConfiguratorComponent }
];

@NgModule({
    imports: [
        CommonModule,

        RouterModule.forChild(routes),

        MatCommonModule,
        MatProgressSpinnerModule,
        MatIconModule,
        MatRadioModule,
        MatFormFieldModule,
        MatInputModule,
        MatOptionModule,
        MatSelectModule,
        MatSlideToggleModule,
        MatDividerModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatTooltipModule,


        MatTabsModule,

        ClassConfiguratorModule,

        FuseSharedModule,

    ],
    declarations: [ConfiguratorComponent]
})



export class ConfiguratorModule { }