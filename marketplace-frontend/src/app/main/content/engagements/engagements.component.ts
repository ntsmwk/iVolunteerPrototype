import {Component, ViewEncapsulation} from '@angular/core';

import {fuseAnimations} from '@fuse/animations';

@Component({
  selector: 'fuse-engagements',
  templateUrl: './engagements.component.html',
  styleUrls: ['./engagements.component.scss'],
  encapsulation: ViewEncapsulation.None,
  animations: fuseAnimations
})
export class FuseEngagementsComponent {

  constructor() {

  }
}
