import {Component} from '@angular/core';

import {fuseAnimations} from '@fuse/animations';

@Component({
    templateUrl  : './profile.component.html',
    styleUrls    : ['./profile.component.scss'],
    animations   : fuseAnimations
})
export class FuseProfileComponent
{

    constructor()
    {
    }
}
