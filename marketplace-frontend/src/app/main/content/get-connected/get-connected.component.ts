import {Component, ViewEncapsulation} from '@angular/core';

import {fuseAnimations} from '@fuse/animations';

@Component({
  templateUrl: './get-connected.component.html',
  styleUrls: ['./get-connected.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: fuseAnimations
})
export class FuseGetConnectedComponent {

  constructor() {

  }
}
