import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';

import { FuseSharedModule } from '@fuse/shared.module';

import { FuseFooterComponent } from 'app/main/footer/footer.component';

@NgModule({
    declarations: [
        FuseFooterComponent
    ],
    imports     : [
        RouterModule,

        MatButtonModule,
        MatIconModule,
        MatToolbarModule,

        FuseSharedModule
    ],
    exports     : [
        FuseFooterComponent
    ]
})
export class FuseFooterModule
{
}
