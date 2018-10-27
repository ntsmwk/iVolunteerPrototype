import {Component, EventEmitter, Input, Output} from '@angular/core';

import {Dashlet} from '../_model/dashlet';
import {DashletsConf} from './dashlets.config';
import {isNullOrUndefined} from 'util';

@Component({
  selector: 'fuse-dashlet',
  templateUrl: './dashlet.component.html',
  styleUrls: ['./dashlet.component.scss']
})
export class FuseDashletComponent {

  @Input('dashlet')
  dashlet: Dashlet;

  @Input('inEditMode')
  inEditMode: boolean;

  @Output('update')
  updateDashletEmitter = new EventEmitter<Dashlet>();

  @Output('remove')
  removeDashletEmitter = new EventEmitter<Dashlet>();

  findDashletComponent() {
    if (isNullOrUndefined(this.dashlet)) {
      return null;
    }
    return DashletsConf.getDashletEntryById(this.dashlet.id).type;
  }

  updateStatus(status: string) {
    this.dashlet.settings.status = status;
    this.updateDashletEmitter.emit(this.dashlet);
  }

  remove() {
    this.removeDashletEmitter.emit(this.dashlet);
  }


}









