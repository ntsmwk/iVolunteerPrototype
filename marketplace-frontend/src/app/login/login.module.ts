import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MatButtonModule, MatCheckboxModule, MatFormFieldModule, MatInputModule } from '@angular/material';

import { FuseSharedModule } from '@fuse/shared.module';

import { FuseLoginComponent } from './login.component';

const routes = [
    {
        path     : '',
        component: FuseLoginComponent
    }
];

@NgModule({
    declarations: [
        FuseLoginComponent
    ],
    imports     : [
        MatButtonModule,
        MatCheckboxModule,
        MatFormFieldModule,
        MatInputModule,

        RouterModule.forChild(routes),

        FuseSharedModule
    ],
    exports :[
        FuseLoginComponent
    ]
})
export class LoginModule
{
}
