import {Component, EventEmitter, Input, Output, ViewEncapsulation} from '@angular/core';
import {Dashlet} from '../_model/dashlet';
import {DashletEntry, DashletsConf} from './dashlets.config';
import {isNullOrUndefined} from 'util';

@Component({
  selector: 'fuse-dashlets-selector',
  templateUrl: './dashlets-selector.component.html',
  styleUrls: ['./dashlets-selector.component.scss']
})
export class FuseDashletsSelectorComponent {

  allDashletEntries = DashletsConf.getDashletEntries();
  selectedDashletEntries: DashletEntry[];

  private selectedDashlets: Dashlet[];

  @Output('dashlets')
  private selectedDashletsEmitter = new EventEmitter<Dashlet[]>();

  constructor() {
  }

  @Input() set dashlets(dashlets: Dashlet[]) {
    this.selectedDashlets = isNullOrUndefined(dashlets) ? [] : dashlets;
    this.selectedDashletEntries = this.generateDashletEntries(this.selectedDashlets);
  }

  private generateDashletEntries(dashlets: Dashlet[]) {
    const dashletEntries = [];
    dashlets.forEach((dashlet: Dashlet) => {
      dashletEntries.push(DashletsConf.getDashletEntryById(dashlet.id));
    });
    return dashletEntries;
  }

  updateSelectedDashlets(dashletEntries: DashletEntry[]) {
    this.selectedDashletEntries = dashletEntries;
    this.selectedDashletsEmitter.emit(this.generateDashlets(this.allDashletEntries));
  }

  private generateDashlets(dashletEntries: DashletEntry[]) {
    const dashlets = [];
    dashletEntries.forEach((dashletEntry: DashletEntry) => {
      const dashlet = new Dashlet();
      dashlet.id = dashletEntry.id;
      dashlet.name = dashletEntry.name;
      dashlet.cols = dashletEntry.initItemCols;
      dashlet.minItemCols = dashletEntry.initItemCols;
      dashlet.rows = dashletEntry.initItemRows;
      dashlet.minItemRows = dashletEntry.initItemRows;
      dashlets.push(dashlet);
    });
    return dashlets;
  }
}
